package neuralnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataParser {

    private long train_data_stream_pointer;
    private long train_label_stream_pointer;

    private FileInputStream fis_data;
    private FileInputStream fis_label;
    private boolean unread;

    public DataParser(String train_data_filePath,String train_label_filePath) throws FileNotFoundException {
        fis_data = new FileInputStream(train_data_filePath);
        fis_label = new FileInputStream(train_label_filePath);
        unread = true;
    }

    public LabeledData parseTrainData() {

        try {
            if(unread) {
                fis_data.skip(16);
                fis_label.skip(8);
                unread = false;
            }
            // fis_data reads 28x28 bytes and fis_label reads 1 byte
            byte[] data = new byte[784];
            double[][] imageData = new double[28][28];
            fis_data.read(data);
            int index = 0;
            for(int i = 0;i < 28;i++) {
                for(int j = 0;j < 28;j++) {
                    imageData[i][j] = data[index++];
                }
            }
           return new LabeledData(imageData, fis_label.read());

        } catch (IOException e) {

        }

        return new LabeledData();

    }

    public void close() {
        try {
            fis_data.close();
            fis_label.close();
        } catch (IOException e) {
            System.out.println("Exception in closing streams");
            System.exit(1);
        }
    }


}
