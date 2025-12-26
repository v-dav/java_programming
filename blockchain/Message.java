package blockchain;

import java.security.PublicKey;

public record Message(
        long id,
        String text,
        byte[] signature,
        PublicKey publicKey
) {
    public String getSignedData() {
        return id + text;
    }

    public boolean isValid() {
        return SignatureUtil.verify(getSignedData(), signature, publicKey);
    }

    @Override
    public String toString() {
        return text;
    }
}