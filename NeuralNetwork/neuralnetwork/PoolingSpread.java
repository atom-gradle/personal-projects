package neuralnetwork;

public interface PoolingSpread {
    double[][][] forward(double[][][] input);
    double[][][] backward(double[][][] input);
}
