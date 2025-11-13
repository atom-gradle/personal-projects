package neuralnetwork;

import java.io.FileNotFoundException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkHelper {

    public NetworkHelper() {}
    public int trainNetwork() {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.init();

        DataParser parser = null;
        try {
            parser = new DataParser(Main.mnist_train_set_filePath,Main.mnist_train_label_filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Data File does not exists.");
            System.exit(1);
        }

        BlockingQueue<LabeledData> dataQueue = new LinkedBlockingQueue<>(1000);
        ThreadPoolExecutor calcThreadPoolExecutor = new ThreadPoolExecutor(Main.availableCores,
                Main.availableCores + 1,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        ThreadPoolExecutor IOThreadPoolExecutor = new ThreadPoolExecutor(Main.availableCores,
                2 * Main.availableCores,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        DataParser finalParser = parser;
        IOThreadPoolExecutor.execute(() -> {
            for(int i = 0;i < Main.totalImageAmount;i++) {
                try {
                    dataQueue.put(finalParser.parseTrainData());
                } catch (InterruptedException e) {
                    System.out.println("Error in put");
                }
            }
        });

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger acc_counter = new AtomicInteger();
        CompletableFuture<Integer> trainFuture = CompletableFuture.supplyAsync(() -> {
            for(int i = 0;i < Main.totalImageAmount;i++) {
                try {
                    LabeledData data = dataQueue.take();
                    //Util.printArray(data.getImageData());
                    //System.out.println(data.getLabel());
                    boolean predictedSuccessful = neuralNetwork.train0(data.getImageData(),data.getLabel(),counter.getAndIncrement(),Main.totalImageAmount);
                    if(predictedSuccessful) {
                        acc_counter.getAndIncrement();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error in take");
                }
            }
            return acc_counter.get();
        }, calcThreadPoolExecutor);

        return trainFuture.join();
    }

    public void testNetwork() {

    }
}
