package neuralnetwork;

public class PoolingLayer implements PoolingSpread {
    public int inputDepth;
    public int inputWidth;
    public int inputHeight;
    public int outputWidth;
    public int outputHeight;
    public PoolingMode poolingMode;
    private static final int poolSize = 2;      // 池化窗口大小
    private static final int stride = 2;        // 步长应该是2
    private double[][][] lastInput;
    private int[][][] maxPositions;  // 用于保存最大值位置（最大池化时）

    public PoolingLayer(int inputDepth, int inputWidth, int inputHeight, PoolingMode poolingMode) {
        this.inputDepth = inputDepth;
        this.inputWidth = inputWidth;
        this.inputHeight = inputHeight;
        this.outputWidth = (inputWidth - poolSize) / stride + 1;  // 正确计算输出尺寸
        this.outputHeight = (inputHeight - poolSize) / stride + 1;
        this.poolingMode = poolingMode;
        this.maxPositions = new int[inputDepth][outputWidth][outputHeight]; // 保存最大值位置
    }

    @Override
    public double[][][] forward(double[][][] input) {
        this.lastInput = input;
        if (poolingMode == PoolingMode.MAX_POOLING) {
            return maxPooling(input);
        }
        return averagePooling(input);
    }

    // 修正后的最大池化
    private double[][][] maxPooling(double[][][] input) {
        double[][][] output = new double[inputDepth][outputWidth][outputHeight];

        for (int d = 0; d < inputDepth; d++) {
            for (int x = 0; x < outputWidth; x++) {
                for (int y = 0; y < outputHeight; y++) {
                    int startX = x * stride;
                    int startY = y * stride;

                    double maxVal = Double.NEGATIVE_INFINITY;
                    int maxX = -1, maxY = -1;

                    // 在2x2窗口内找最大值
                    for (int i = 0; i < poolSize; i++) {
                        for (int j = 0; j < poolSize; j++) {
                            int inputX = startX + i;
                            int inputY = startY + j;
                            if (inputX < inputWidth && inputY < inputHeight) {
                                double val = input[d][inputX][inputY];
                                if (val > maxVal) {
                                    maxVal = val;
                                    maxX = inputX;
                                    maxY = inputY;
                                }
                            }
                        }
                    }

                    output[d][x][y] = maxVal;
                    maxPositions[d][x][y] = maxX * inputHeight + maxY; // 保存位置信息
                }
            }
        }
        return output;
    }

    // 修正后的平均池化
    private double[][][] averagePooling(double[][][] input) {
        double[][][] output = new double[inputDepth][outputWidth][outputHeight];

        for (int d = 0; d < inputDepth; d++) {
            for (int x = 0; x < outputWidth; x++) {
                for (int y = 0; y < outputHeight; y++) {
                    int startX = x * stride;
                    int startY = y * stride;

                    double sum = 0.0;
                    int count = 0;

                    // 计算2x2窗口内的平均值
                    for (int i = 0; i < poolSize; i++) {
                        for (int j = 0; j < poolSize; j++) {
                            int inputX = startX + i;
                            int inputY = startY + j;
                            if (inputX < inputWidth && inputY < inputHeight) {
                                sum += input[d][inputX][inputY];
                                count++;
                            }
                        }
                    }

                    output[d][x][y] = sum / count;
                }
            }
        }
        return output;
    }

    @Override
    public double[][][] backward(double[][][] gradient) {
        double[][][] output = new double[inputDepth][inputWidth][inputHeight];

        // 初始化梯度为0
        for (int d = 0; d < inputDepth; d++) {
            for (int x = 0; x < inputWidth; x++) {
                for (int y = 0; y < inputHeight; y++) {
                    output[d][x][y] = 0.0;
                }
            }
        }

        if (poolingMode == PoolingMode.MAX_POOLING) {
            // 最大池化的反向传播
            for (int d = 0; d < inputDepth; d++) {
                for (int x = 0; x < outputWidth; x++) {
                    for (int y = 0; y < outputHeight; y++) {
                        int pos = maxPositions[d][x][y];
                        int maxX = pos / inputHeight;
                        int maxY = pos % inputHeight;
                        output[d][maxX][maxY] += gradient[d][x][y];
                    }
                }
            }
        } else {
            // 平均池化的反向传播
            for (int d = 0; d < inputDepth; d++) {
                for (int x = 0; x < outputWidth; x++) {
                    for (int y = 0; y < outputHeight; y++) {
                        int startX = x * stride;
                        int startY = y * stride;
                        double grad = gradient[d][x][y] / (poolSize * poolSize);

                        for (int i = 0; i < poolSize; i++) {
                            for (int j = 0; j < poolSize; j++) {
                                int inputX = startX + i;
                                int inputY = startY + j;
                                if (inputX < inputWidth && inputY < inputHeight) {
                                    output[d][inputX][inputY] += grad;
                                }
                            }
                        }
                    }
                }
            }
        }

        return output;
    }

    public enum PoolingMode {
        MAX_POOLING,
        AVG_POOLING
    }
}