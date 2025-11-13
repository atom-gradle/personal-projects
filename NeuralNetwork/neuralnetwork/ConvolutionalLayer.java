package neuralnetwork;

import java.util.*;

public class ConvolutionalLayer implements Spread {
    private int inputWidth;    // 输入图像的宽度
    private int inputHeight;   // 输入图像的高度
    private int inputDepth;    // 输入图像的深度(通道数)
    private int kernelSize;    // 卷积核尺寸(假设为正方形)
    private int neuronCount;    // 神经元/滤波器数量
    private static final int stride = 1;        // 卷积步长
    private static final int padding = 0;       // 填充大小

    public double[][][][] kernels;  // 卷积核 [neuronCount][depth][width][height]
    private double[] biases;        // 每个神经元的偏置

    private double[][][] lastInput; // 保存上一次的输入用于反向传播

    // 添加激活值控制参数
    private static final double MAX_ACTIVATION = 6.0; // 限制单个激活值

    /**
     * 构造函数
     * @param inputWidth 输入宽度
     * @param inputHeight 输入高度
     * @param inputDepth 输入深度(通道数)
     * @param kernelSize 卷积核尺寸
     * @param neuronCount 滤波器数量
     */
    public ConvolutionalLayer(int inputDepth, int inputWidth, int inputHeight,
                            int kernelSize, int neuronCount) {
        this.inputWidth = inputWidth;
        this.inputHeight = inputHeight;
        this.inputDepth = inputDepth;
        this.kernelSize = kernelSize;
        this.neuronCount = neuronCount;

    }

    public void initParams() {
        Random rand = new Random();

        // 使用 He 初始化（适合 ReLU 激活函数）
        double stddev = Math.sqrt(2.0 / (kernelSize * kernelSize * inputDepth));

        kernels = new double[neuronCount][inputDepth][kernelSize][kernelSize];
        for (int f = 0; f < neuronCount; f++) {
            for (int d = 0; d < inputDepth; d++) {
                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        kernels[f][d][i][j] = rand.nextGaussian() * stddev;
                    }
                }
            }
        }

        biases = new double[neuronCount];
        for (int f = 0; f < neuronCount; f++) {
            biases[f] = 0.0; // 或者 0.1 * rand.nextGaussian()
        }
    }

    /**
     * 前向传播
     * @param input 输入数据 [depth][width][height]
     * @return 卷积后的特征图 [filter][width][height]
     */
    @Override
    public double[][][] forward(double[][][] input) {
        this.lastInput = input; // 保存输入用于反向传播

        int outputWidth = (inputWidth - kernelSize + 2 * padding) / stride + 1;
        int outputHeight = (inputHeight - kernelSize + 2 * padding) / stride + 1;

        double[][][] output = new double[neuronCount][outputWidth][outputHeight];

        // 对每个神经元/滤波器进行卷积操作
        for (int f = 0; f < neuronCount; f++) {
            for (int x = 0; x < outputWidth; x++) {
                for (int y = 0; y < outputHeight; y++) {

                    // 计算输入窗口的起始位置
                    int startX = x * stride - padding;
                    int startY = y * stride - padding;

                    double sum = 0.0;

                    // 对每个输入通道进行卷积
                    for (int d = 0; d < inputDepth; d++) {
                        for (int i = 0; i < kernelSize; i++) {
                            for (int j = 0; j < kernelSize; j++) {
                                int inputX = startX + i;
                                int inputY = startY + j;

                                // 处理填充边界
                                if (inputX >= 0 && inputX < inputWidth &&
                                        inputY >= 0 && inputY < inputHeight) {
                                    sum += input[d][inputX][inputY] * kernels[f][d][i][j];
                                }
                            }
                        }
                    }

                    // 应用ReLU，但限制最大值
                    double activation = sum + biases[f];
                    output[f][x][y] = Math.min(FunctionUtil.ReLU(activation), MAX_ACTIVATION);
                }
            }
        }

        return output;
    }

    /**
     * 反向传播
     * @param gradient 上一层传来的梯度 [filter][width][height]
     * @param learningRate 学习率
     * @return 传递给下一层的梯度 [depth][width][height]
     */
    @Override
    public double[][][] backward(double[][][] gradient, double learningRate) {
        int outputWidth = gradient[0].length;
        int outputHeight = gradient[0][0].length;

        double[][][] inputGradient = new double[inputDepth][inputWidth][inputHeight];
        double[][][][] kernelGradient = new double[neuronCount][inputDepth][kernelSize][kernelSize];
        double[] biasGradient = new double[neuronCount];

        // 初始化梯度
        for (int d = 0; d < inputDepth; d++) {
            for (int x = 0; x < inputWidth; x++) {
                for (int y = 0; y < inputHeight; y++) {
                    inputGradient[d][x][y] = 0.0;
                }
            }
        }

        // 计算梯度
        for (int f = 0; f < neuronCount; f++) {
            for (int x = 0; x < outputWidth; x++) {
                for (int y = 0; y < outputHeight; y++) {

                    int startX = x * stride - padding;
                    int startY = y * stride - padding;

                    // 🔥 关键修改1：检查当前梯度值，如果太大就跳过
                    if (Math.abs(gradient[f][x][y]) > 10.0) {
                        // System.out.printf("跳过过大梯度: %.3f%n", gradient[f][x][y]);
                        continue;
                    }

                    // 计算对输入的梯度
                    for (int d = 0; d < inputDepth; d++) {
                        for (int i = 0; i < kernelSize; i++) {
                            for (int j = 0; j < kernelSize; j++) {
                                int inputX = startX + i;
                                int inputY = startY + j;

                                if (inputX >= 0 && inputX < inputWidth &&
                                        inputY >= 0 && inputY < inputHeight) {
                                    inputGradient[d][inputX][inputY] +=
                                            kernels[f][d][i][j] * gradient[f][x][y];

                                    // 计算对卷积核的梯度
                                    kernelGradient[f][d][i][j] +=
                                            lastInput[d][inputX][inputY] * gradient[f][x][y];
                                }
                            }
                        }
                    }

                    // 计算对偏置的梯度
                    biasGradient[f] += gradient[f][x][y];
                }
            }
        }

        // 🔥 关键修改2：更严格的梯度裁剪和参数更新
        double gradientNorm = 0.0;
        int paramCount = 0;

        // 首先计算梯度范数
        for (int f = 0; f < neuronCount; f++) {
            for (int d = 0; d < inputDepth; d++) {
                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        double grad = kernelGradient[f][d][i][j];
                        gradientNorm += grad * grad;
                        paramCount++;
                    }
                }
            }
            gradientNorm += biasGradient[f] * biasGradient[f];
            paramCount++;
        }
        gradientNorm = Math.sqrt(gradientNorm / paramCount);

        // 梯度裁剪：如果梯度范数太大，进行缩放
        double maxGradientNorm = 1.0; // 最大允许的梯度范数
        double scale = 1.0;
        if (gradientNorm > maxGradientNorm) {
            scale = maxGradientNorm / gradientNorm;
            System.out.printf("梯度裁剪: 范数 %.3f -> 缩放 %.3f%n", gradientNorm, scale);
        }

        // Update params with gradient clipping
        for (int f = 0; f < neuronCount; f++) {
            for (int d = 0; d < inputDepth; d++) {
                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        double grad = kernelGradient[f][d][i][j] * scale; // 应用缩放

                        // 更严格的逐参数梯度裁剪
                        if (grad > 0.1) grad = 0.1;
                        if (grad < -0.1) grad = -0.1;

                        // 检查NaN
                        if (Double.isNaN(grad)) {
                            // System.out.println("卷积核梯度出现 NaN");
                            grad = 0.0;
                        }

                        kernels[f][d][i][j] -= learningRate * grad;

                        // 可选：权重裁剪，防止权重变得太大
                        if (kernels[f][d][i][j] > 2.0) kernels[f][d][i][j] = 2.0;
                        if (kernels[f][d][i][j] < -2.0) kernels[f][d][i][j] = -2.0;
                    }
                }
            }

            // 偏置更新也应用梯度裁剪
            double biasGrad = biasGradient[f] * scale;
            if (biasGrad > 0.1) biasGrad = 0.1;
            if (biasGrad < -0.1) biasGrad = -0.1;
            biases[f] -= learningRate * biasGrad;

            // 偏置裁剪
            if (biases[f] > 2.0) biases[f] = 2.0;
            if (biases[f] < -2.0) biases[f] = -2.0;
        }

        // 🔥 关键修改3：在返回输入梯度前应用ReLU导数
        for (int d = 0; d < inputDepth; d++) {
            for (int x = 0; x < inputWidth; x++) {
                for (int y = 0; y < inputHeight; y++) {
                    // 如果前向传播时该输入<=0，那么梯度为0（ReLU导数）
                    if (lastInput[d][x][y] <= 0) {
                        inputGradient[d][x][y] = 0.0;
                    }

                    // 对输入梯度也进行裁剪
                    if (inputGradient[d][x][y] > 1.0) inputGradient[d][x][y] = 1.0;
                    if (inputGradient[d][x][y] < -1.0) inputGradient[d][x][y] = -1.0;
                }
            }
        }

        return inputGradient;
    }

}
