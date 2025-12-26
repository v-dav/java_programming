package blockchain;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static blockchain.ShaUtil.applySha256;

public class Blockchain {

    private final List<Block> chain = Collections.synchronizedList(new ArrayList<>());
    private Long nextId = 1L;
    private int leadingZeros = 0;
    private final Random random = new Random();
    private final List<Message> messagedReceived = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong messageIdCounter = new AtomicLong(0);

    public void generateNewBlock(int minerId, int numberOfBlocks) {
        String prevHash;
        int currentLeadingZeros;
        List<Message> messagesSnapshot;
        long maxIdInChain;

        synchronized (this) {
            if (chain.size() >= numberOfBlocks) {
                return;
            }
            prevHash = chain.isEmpty() ? "0" : chain.getLast().currentHash();
            currentLeadingZeros = leadingZeros;

            // Get max message ID from current chain
            maxIdInChain = chain.stream()
                    .mapToLong(Block::getMaxMessageId)
                    .max()
                    .orElse(0);

            messagesSnapshot =
                    chain.isEmpty() ? new ArrayList<>()
                    : messagedReceived.stream()
                    .filter(m -> m.id() > maxIdInChain)
                    .toList();
        }

        Instant start = Instant.now();
        Block newBlock = createBlock(prevHash, currentLeadingZeros, messagesSnapshot);

        synchronized (this) {
            if (chain.size() < numberOfBlocks && isValidBlock(newBlock, chain, currentLeadingZeros)) {
                chain.add(newBlock);
                nextId++;
                messagedReceived.removeAll(messagesSnapshot);
                Instant end = Instant.now();
                long seconds = Duration.between(start, end).toSeconds();
                String nStatus = manageLeadingZeroes(seconds);
                System.out.println(newBlock.toFormattedString(minerId, nStatus));
            }
        }
    }

    private String manageLeadingZeroes(long seconds) {
        if (seconds < 15) {
            leadingZeros++;
            return "N was increased to " + leadingZeros;
        } else if (seconds > 60) {
            if (leadingZeros > 0) {
                leadingZeros--;
                return "N was decreased by 1";
            }
            return "N stays the same";
        } else {
            return "N stays the same";
        }
    }

    private boolean isValidBlock(Block newBlock, List<Block> chain, int requiredZeros) {
        String prevHash = newBlock.prevHash();

        if (!newBlock.currentHash().startsWith("0".repeat(requiredZeros))) {
            return false;
        }

        if (chain.isEmpty() && !prevHash.equals("0")) {
            return false;
        }

        if (!chain.isEmpty()) {
            return prevHash.equals(chain.getLast().currentHash());
        }

        for (Message message : newBlock.messages()) {
            if (!message.isValid()) {
                return false;
            }
        }

        return true;
    }

    private Block createBlock(String prevHash, int requiredZeros, List<Message> messagesSnapshot) {
        long magicNumber = random.nextLong();
        long timestamp = new Date().getTime();
        String currentHash = applySha256(nextId + prevHash + timestamp + magicNumber + messagesSnapshot.toString());

        while (!currentHash.startsWith("0".repeat(requiredZeros))) {
            magicNumber = random.nextLong();
            currentHash = applySha256(nextId + prevHash + timestamp + magicNumber + messagesSnapshot.toString());
        }

        return new Block(nextId, prevHash, timestamp, currentHash, magicNumber, messagesSnapshot);
    }

    public boolean isValidBlockchain() {
        if (chain.isEmpty()) return true;
        long previousMaxMessageId = 0;

        for (int i = 0; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            if (i == 0 && !currentBlock.prevHash().equals("0")) return false;

            String expectedHash = applySha256(
                    currentBlock.id() +
                            currentBlock.prevHash() +
                            currentBlock.timestamp() +
                            currentBlock.magicNumber() +
                            currentBlock.messages().toString()
            );

            if (!expectedHash.equals(currentBlock.currentHash())) return false;

            if (i < chain.size() - 1) {
                Block nextBlock = chain.get(i + 1);
                if (!expectedHash.equals(nextBlock.prevHash())) return false;
            }

            for (Message message : currentBlock.messages()) {
                if (!message.isValid()) return false;
                if (message.id() <= previousMaxMessageId) return false;
            }

            previousMaxMessageId = currentBlock.getMaxMessageId();
        }
        return true;
    }

    public synchronized int getChainSize() {
        return chain.size();
    }

    public void addMessage(Message message) {
        // Verify signature before accepting
        if (!message.isValid()) {
            System.out.println("Rejected message: invalid signature");
            return;
        }

        // Get current max message ID in chain
        long maxIdInChain;
        synchronized (this) {
            maxIdInChain = chain.stream()
                    .mapToLong(Block::getMaxMessageId)
                    .max()
                    .orElse(0);
        }

        // Reject if ID is not greater than current max
        if (message.id() <= maxIdInChain) {
            System.out.println("Rejected message: ID too low (replay attack prevented)");
            return;
        }

        this.messagedReceived.add(message);
    }
}
