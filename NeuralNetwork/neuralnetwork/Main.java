package neuralnetwork;

import javax.swing.*;

public class Main {

    public static final String mnist_dataset_dir_path = "C:\\Users\\Admin\\IdeaProjects\\NeuralNetwork\\MNIST\\raw";

    public static final String mnist_train_set_filePath = mnist_dataset_dir_path + "\\train-images-idx3-ubyte";
    public static final String mnist_train_label_filePath = mnist_dataset_dir_path + "\\train-labels-idx1-ubyte";
    public static final String mnist_test_set_filePath = mnist_dataset_dir_path + "\\t10k-images-idx3-ubyte";
    public static final String mnist_test_label_filePath = mnist_dataset_dir_path + "\\t10k-labels-idx1-ubyte";
    public static final int availableCores = Runtime.getRuntime().availableProcessors();
    public static final int totalImageAmount = 60_000;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainWindow::new);

    }
}
