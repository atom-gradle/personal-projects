package neuralnetwork;

public class LabeledData {

    private double[][] imageData;
    private int label;

    public LabeledData() {}
    public LabeledData(double[][] imageData, int label) {
        this.imageData = imageData;
        this.label = label;
    }
    //像素值范围‌：每个像素点的取值范围为‌0（黑色）到255（白色）‌，数据类型为uint8
    //‌数据存储格式‌：MNIST数据集采用灰度图形式存储，背景为纯黑色（像素值0），数字为白色（像素值255）

    public double[][][] getImageData() {
        double[][][] data = new double[1][28][28];
        data[0] = imageData;
        // 归一化到 [0, 1] 范围
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                for (int k = 0; k < data[0][0].length; k++) {
                    if(data[i][j][k] != 0)
                        data[i][j][k] = (255 - Math.abs(data[i][j][k])) / 255.0;
                }
            }
        }
        return data;
    }

    public int getLabel() {
        return label;
    }
}
