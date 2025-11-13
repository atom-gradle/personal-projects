package neuralnetwork;

import java.util.Arrays;
import java.util.Random;

public class FullConnectionLayer {

    public int inputDepth;
    public int inputWidth;
    public int inputHeight;
    private int length;
    public static final int neuronCount = 10;
    private double[] lastInput;
    public double[][] weights;
    private double[] biases;

    public FullConnectionLayer(int inputDepth, int inputWidth, int inputHeight) {
        this.inputDepth = inputDepth;
        this.inputWidth = inputWidth;
        this.inputHeight = inputHeight;
        this.length = inputDepth * inputWidth * inputHeight;
        this.weights = new double[neuronCount][length];
        this.biases = new double[neuronCount];
    }

    public void initParams() {
        Random rand = new Random();
        int inputSize = inputDepth * inputWidth * inputHeight;

        // 使用更保守的Xavier初始化
        double scale = Math.sqrt(2.0 / (inputSize + neuronCount));

        for (int i = 0; i < neuronCount; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = rand.nextGaussian() * scale;
                // 检查初始化值
                if (Double.isNaN(weights[i][j])) {
                    System.out.println("权重初始化出现NaN!");
                    weights[i][j] = 0.01;
                }
            }
            biases[i] = 0.01; // 使用小的正偏置，避免死神经元
        }

        System.out.println("全连接层初始化完成，权重范围: " +
                getWeightRange() + ", 偏置范围: " + getBiasRange());
    }

    public double[] forward(double[][][] input) {
        // 展平输入
        double[] vector = Arrays.stream(input)
                .flatMap(Arrays::stream)
                .flatMapToDouble(Arrays::stream)
                .toArray();

        // 输入归一化 - 关键修复！
        normalizeInput(vector);

        // 检查输入范围
        double min = Arrays.stream(vector).min().orElse(0);
        double max = Arrays.stream(vector).max().orElse(0);
        if (min < 0 || max > 10) { // ReLU输出应该>=0
            System.out.printf("全连接层输入异常: [%.3f, %.3f]%n", min, max);
        }

        this.lastInput = vector;
        double[] result = new double[neuronCount];

        for(int k = 0; k < neuronCount; k++) {
            for(int i = 0; i < length; i++) {
                result[k] += vector[i] * weights[k][i];
            }
            result[k] += biases[k];

            // 检查中间结果
            if (Double.isNaN(result[k])) {
                System.out.println("前向传播出现NaN!");
                result[k] = 0;
            }
        }

        double[] softmax = FunctionUtil.Softmax(result);
        return softmax;
    }

    // 添加输入归一化方法
    private void normalizeInput(double[] input) {
        // 计算当前输入的均值和最大值
        double max = 0;
        double sum = 0;

        for (double value : input) {
            if (value > max) max = value;
            sum += value;
        }
        double mean = sum / input.length;

        // 简单的归一化：除以最大值，保持相对比例
        if (max > 8.0) { // 只在值较大时归一化
            double scale = max / 4.0; // 目标最大值为4
            for (int i = 0; i < input.length; i++) {
                input[i] = input[i] / scale;
                // 确保不会变成负值（ReLU输出应该是非负的）
                if (input[i] < 0) input[i] = 0;
            }
        }

        // 可选：记录归一化信息用于调试
        if (max > 10.0) {
            System.out.printf("输入归一化: 最大值 %.3f -> 缩放 %.3f%n", max, max/4.0);
        }
    }
    public double[][][] backward(double[] softmaxOutput, int target, double learningRate) {
        // 1. 计算输出层梯度 (softmax + cross entropy)
        double[] gradients = new double[neuronCount];

        // 简化标签：不使用标签平滑，先用标准方法
        for (int i = 0; i < gradients.length; i++) {
            double targetValue = (i == target) ? 1.0 : 0.0;
            gradients[i] = softmaxOutput[i] - targetValue;

            // 更温和的梯度裁剪
            if (gradients[i] > 1.0) gradients[i] = 1.0;
            if (gradients[i] < -1.0) gradients[i] = -1.0;

            if (Double.isNaN(gradients[i])) {
                System.out.println("输出梯度出现NaN!");
                gradients[i] = 0.0;
            }
        }

        // 2. 更新权重和偏置
        for(int i = 0; i < neuronCount; i++) {
            for(int j = 0; j < length; j++) {
                double weightUpdate = learningRate * gradients[i] * lastInput[j];

                // 权重更新裁剪
                if (weightUpdate > 0.1) weightUpdate = 0.1;
                if (weightUpdate < -0.1) weightUpdate = -0.1;

                weights[i][j] -= weightUpdate;

                // 检查权重
                if (Double.isNaN(weights[i][j])) {
                    System.out.println("权重更新后出现NaN!");
                    weights[i][j] = 0.01;
                }
            }
            double biasUpdate = learningRate * gradients[i];
            biases[i] -= biasUpdate;
        }

        // 3. 计算传递给前一层的梯度（关键修正！）
        double[] prevGradients = new double[length];
        for(int j = 0; j < length; j++) {
            double sum = 0;
            for(int i = 0; i < neuronCount; i++) {
                sum += weights[i][j] * gradients[i];
            }
            prevGradients[j] = sum;

            // 应用ReLU导数：如果上一层的输入<=0，梯度为0
            if (lastInput[j] <= 0) {
                prevGradients[j] = 0;
            }
        }

        double[][][] output = reshapeTo3D(prevGradients, inputDepth, inputHeight, inputWidth);
        return output;
    }

    private double[][][] reshapeTo3D(double[] flat, int depth, int height, int width) {
        double[][][] result = new double[depth][height][width];
        int index = 0;
        for(int d = 0; d < depth; d++) {
            for(int h = 0; h < height; h++) {
                for(int w = 0; w < width; w++) {
                    result[d][h][w] = flat[index++];
                }
            }
        }
        return result;
    }

    // 辅助方法：监控权重和偏置
    private String getWeightRange() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < neuronCount; i++) {
            for (int j = 0; j < length; j++) {
                if (weights[i][j] < min) min = weights[i][j];
                if (weights[i][j] > max) max = weights[i][j];
            }
        }
        return String.format("[%.4f, %.4f]", min, max);
    }

    private String getBiasRange() {
        double min = Arrays.stream(biases).min().orElse(0);
        double max = Arrays.stream(biases).max().orElse(0);
        return String.format("[%.4f, %.4f]", min, max);
    }
}