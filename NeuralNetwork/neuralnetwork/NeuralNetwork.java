package neuralnetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NeuralNetwork {
    public static final int Image_outputSize = 28;
    public static final int Conv_1_outputSize = 24;
    public static final int Pool_2_outputSize = 12;
    public static final int Conv_3_outputSize = 8;
    public static final int Pool_4_outputSize = 4;

    public static final int convCoreSize = 5;
    public static final int poolCoreSize = 2;

    public ConvolutionalLayer C1,C3;
    public PoolingLayer S2,S4;
    public FullConnectionLayer O5;

    public double learningRate = 0.0001;

    public NeuralNetwork() {
        C1 = new ConvolutionalLayer(1,28,28,5,6);
        S2 = new PoolingLayer(6,24,24, PoolingLayer.PoolingMode.MAX_POOLING);
        C3 = new ConvolutionalLayer(6,12,12,5,12);
        S4 = new PoolingLayer(12,8,8, PoolingLayer.PoolingMode.MAX_POOLING);
        O5 = new FullConnectionLayer(12,4,4);
    }

    public void init() {
        C1.initParams();
        C3.initParams();
        O5.initParams();
    }

    /*
    public void train(String trainDirPath) {
        String[] filePaths = new File(trainDirPath).list();
        if(filePaths == null || filePaths.length == 0) {
            System.out.println("No images to train.");
            return;
        }

        int total = filePaths.length;
        AtomicInteger count = new AtomicInteger();
        Arrays.stream(filePaths).forEach(path -> {
            train0(trainDirPath + "\\" + path,count.get(),total);
            System.out.println("Learning Rate: " + learningRate + "\n");
            count.getAndIncrement();
        });
        System.out.println("Training Finished.");
    }
     */

    public int predict(double[][] imageArray) {
        double[][][] input = new double[1][28][28];
        input[0] = imageArray;
        double[] output = forward(input);
        Util.printArray(output);
        return Util.getPredictedValueFromArray(output);
    }

    public boolean train0(double[][][] imageData,int target,int count,int total) {
        System.out.println("Training: " + count + " / " + total + ", Target is "+target);
        double[] softmaxPrediction = forward(imageData);
        int predictedValue = Util.getPredictedValueFromArray(softmaxPrediction);
        backward(softmaxPrediction,target);
        return target == predictedValue;
    }

    private int test0(String filePath) {
        double[] prediction = forward(filePath);
        Util.printArray(prediction);
        return Util.getPredictedValueFromArray(prediction);
    }

    private double[] forward(String filePath) {
        double[][][] input = null;
        try {
            input = Util.readImage(filePath);
        } catch (FileNotFoundException e1) {
            System.out.println(e1.getMessage());
            System.exit(1);
        } catch (IOException e2) {
            System.out.println("Error reading input image.");
            System.exit(1);
        }

        double[][][] input1 = C1.forward(input);
        double[][][] input2 = S2.forward(input1);
        double[][][] input3 = C3.forward(input2);
        double[][][] input4 = S4.forward(input3);
        double[] input5 = O5.forward(input4);
        return input5;
    }

    private double[] forward(double[][][] input) {
        double[][][] input1 = C1.forward(input);
        double[][][] input2 = S2.forward(input1);
        double[][][] input3 = C3.forward(input2);
        double[][][] input4 = S4.forward(input3);
        double[] input5 = O5.forward(input4);
        return input5;
    }

    private void backward(double[] input,int target) {
        double[][][] output5 = O5.backward(input, target, learningRate);
        double[][][] output4 = S4.backward(output5);
        double[][][] output3 = C3.backward(output4, learningRate);
        double[][][] output2 = S2.backward(output3);
        C1.backward(output2,learningRate);
    }

}
