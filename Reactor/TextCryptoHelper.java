package Reactor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class TextCryptoHelper {

    // AES encrypted mode : CBC Mode
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    // AES Key Length:128 bits
    private static final int AES_KEY_SIZE = 128;

    public static void main(String[] args) throws Exception {
        String[] results = getEncryptedResults("Hello");
        System.out.println(getDecryptedResult(results[0],results[1],results[2]));
    }

    public static String[] getEncryptedResults(String textToEncrypt) throws Exception {
        String[] results = new String[3];

        // generate AES Key
        SecretKey secretKey = generateAESKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        // generate init vector(IV)
        byte[] iv = generateIV();
        String encodedIV = Base64.getEncoder().encodeToString(iv);

        // encrypt
        byte[] encryptedData = encrypt(textToEncrypt.getBytes(StandardCharsets.UTF_8), secretKey, iv);
        String encryptedText = Base64.getEncoder().encodeToString(encryptedData);

        results[0] = encodedKey;
        results[1] = encodedIV;
        results[2] = encryptedText;
        return results;
    }

    public static String getDecryptedResult(String secretKeyStr,String ivStr,String textToDecrypt) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(textToDecrypt);
        SecretKey secretKey = getSecretKeyFromBase64(secretKeyStr);
        byte[] iv = Base64.getDecoder().decode(ivStr);
        // decrypt
        byte[] decryptedData = decrypt(encryptedData, secretKey, iv);
        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);;

        return decryptedText;
    }
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE); // can be 128, 192 or 256 bits
        return keyGenerator.generateKey();
    }

    public static byte[] generateIV() {
        byte[] iv = new byte[16]; // AES block is 16 B in size
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public static byte[] encrypt(byte[] data, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(encryptedData);
    }

    /**
     * rebuild key from string encoded in Base64
     */
    public static SecretKey getSecretKeyFromBase64(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}