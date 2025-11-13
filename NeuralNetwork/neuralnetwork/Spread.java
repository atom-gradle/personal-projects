package neuralnetwork;

public interface Spread {
    double[][][] forward(double[][][] input);
    double[][][] backward(double[][][] input,double learningRate);
}
