package Reactor;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;

public class Reactor {

    private ThreadPoolExecutor mainExecutor;
    private ThreadPoolExecutor subExecutor;

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private static volatile boolean running = true;
    private static volatile ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private static final ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

    public static final int port = 8000;//listening for msg delivery service

    public static String serverIP = "";
    public static Map<String,SocketChannel> clientsMap;

    public Reactor() throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        clientsMap = new HashMap<>();
        serverIP = serverSocketChannel.getLocalAddress().toString();
    }

    public void launch() throws Exception {
        System.out.printf("Server launches at IP: %s listening for port 8000",serverIP);
        while(running) {
            if(!running) {
                break;
            }
            int readyChannels = selector.select();
            //int readyChannels = selector.select(500);
            if(readyChannels == 0) {
                System.out.println("0");
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if(key.isAcceptable()) {
                    System.out.println("is Acceptable");
                    accept(key);
                } else if(key.isReadable()) {
                    System.out.println("is Readable");
                    read(key);
                }
                System.out.println(key);
            }
        }
    }

    public void accept(SelectionKey key) throws Exception {
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        client.register(selector,SelectionKey.OP_READ);
        System.out.println("A client connected!");
        //String clientId = "User-" + UUID.randomUUID().toString().substring(0, 8);
        //clientsMap.put(clientId,client);
    }

    public void read(SelectionKey key) throws Exception {
        SocketChannel client = (SocketChannel) key.channel();
        int length = 0;
        lengthBuffer.clear();
        if((client.read(lengthBuffer)) != -1) {
            lengthBuffer.flip();
            length = lengthBuffer.getInt();
            System.out.printf("Length is: %d",length);
            ByteBuffer buffer = ByteBuffer.allocate(length-4);
            int bytesRead = client.read(buffer);
            if(bytesRead != -1) {
                buffer.flip();
                byte[] msgBytes = new byte[buffer.limit()];
                buffer.get(msgBytes);
                Msg msg = new Msg(msgBytes);
                System.out.println(msg);
                if(msg.getType() == Msg.SYS_INFO) {
                    String decryptedContent = TextCryptoHelper.getDecryptedResult(msg.getAESKey(),msg.getVectorIV(),msg.getContent());
                    if(decryptedContent.startsWith("LOGIN")) {
                        //String[] loginInfoArray = decryptedContent.substring(5,decryptedContent.length()).split(";");
                        //checkValidity(loginInfoArray[0],loginInfoArray[1]);
                        clientsMap.put(msg.getFrom().trim(),client);
                        System.out.println(clientsMap);
                    } else if(decryptedContent.startsWith("QUERY")) {
                        String requireWhat = decryptedContent.substring(5,decryptedContent.length());
                    }
                } else if(msg.getType() == Msg.TEXT) {
                    String decryptedContent = TextCryptoHelper.getDecryptedResult(msg.getAESKey(),msg.getVectorIV(),msg.getContent());
                    System.out.println("Decrypted Text is: "+decryptedContent);
                    try {
                        repost(msg);
                    } catch(Exception e) {
                        System.out.println("repost failed");
                    }
                }
            }
        } else {
            key.cancel();
            client.close();
            if(clientsMap.containsValue(client)) {
                clientsMap.remove(client);
            }
            System.out.println("A client disconnected!  "+clientsMap);
        }
    }
    public boolean sendMsg(SocketChannel socketChannel,Msg msg) throws Exception {
        if(socketChannel != null) {
            if(socketChannel.isConnected()) {
                ByteBuffer buffer = msg.getByteBuffer();
                SelectionKey key = socketChannel.keyFor(selector);
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                while(buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                    if (!buffer.hasRemaining()) {
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                    }
                }
                return true;
            }
        }
        return false;
    }
    public boolean repost(Msg msg) throws Exception {
        String to = msg.getTo().trim();
        if(clientsMap.containsKey(to)) {
            sendMsg(clientsMap.get(to),msg);
            System.out.println("has reposted");
            return true;
        }
        System.out.println(msg.getTo().trim()+"is offline,so the msg is not reposted");
        return false;
    }

}
