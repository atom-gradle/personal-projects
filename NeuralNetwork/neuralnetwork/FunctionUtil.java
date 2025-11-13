package neuralnetwork;

import java.util.Arrays;

public class FunctionUtil {
    public static double ReLU(double x) {
        return x >= 0 ? x : 0.0d;
    }
    public static double LeakyReLU(double x) {
        return x >= 0 ? x : 0.01d * x;
    }
    public static double Sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E,-x));
    }
    public static double[] Softmax(double[] input) {
        if (input == null || input.length == 0) return new double[0];

        double max = Arrays.stream(input).max().orElse(0.0);

        // 限制输入范围，防止极端值
        double[] stabilized = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            stabilized[i] = Math.max(Math.min(input[i] - max, 10.0), -10.0);
        }

        double sum = 0.0;
        double[] exps = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            exps[i] = Math.exp(stabilized[i]);
            sum += exps[i];
        }

        // 确保概率合理分布
        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = exps[i] / sum;
            // 强制概率在合理范围内
            result[i] = Math.max(1e-8, Math.min(result[i], 1.0 - 1e-8));
        }

        return result;
    }
    public static double max(double ...args) {
        return Arrays.stream(args).max().getAsDouble();
    }

    public static double avg(double ...args) {
        return Arrays.stream(args).average().getAsDouble();
    }
}
