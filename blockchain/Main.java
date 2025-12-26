package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        int numberOfMiners = 10;
        int numberOfBlocks = 5;

        // Miners simulator
        try (ExecutorService minerPool = Executors.newFixedThreadPool(numberOfMiners)) {
            for (int i = 0; i < numberOfMiners; i++) {
                final int minerId = i + 1;
                minerPool.submit(() -> {
                    while (blockchain.getChainSize() < numberOfBlocks) {
                        blockchain.generateNewBlock(minerId, numberOfBlocks);
                    }
                });
            }
        }
    }
}
