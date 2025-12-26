package blockchain;

import java.util.List;

public record Block(Long id,
                    String prevHash,
                    Long timestamp,
                    String currentHash,
                    Long magicNumber,
                    List<Message> messages
) {

    public String toFormattedString(int minerId,
                                    String nStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append("Block data:");
        if (messages.isEmpty()) {
            sb.append(" no messages");
        } else {
            for (Message m : messages) {
                sb.append("\n").append(m);
            }
        }

        return "Block:\n" +
                "Created by miner # " + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                prevHash + "\n" +
                "Hash of the block:\n" +
                currentHash + "\n" +
                sb + "\n" +
                nStatus + "\n";
    }

    public long getMaxMessageId() {
        return messages.stream()
                .mapToLong(Message::id)
                .max()
                .orElse(0);
    }
}