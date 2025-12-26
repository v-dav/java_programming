# Java Blockchain

A lightweight, educational blockchain implementation in Java featuring proof-of-work consensus, digital signatures, and concurrent mining.

## Overview

This project demonstrates core blockchain concepts including cryptographic hashing, proof-of-work mining with dynamic difficulty adjustment, and message authentication using RSA digital signatures. It supports multiple concurrent miners competing to add blocks to the chain.

## Features

- **Proof-of-Work Mining** — Miners compete to find valid block hashes with configurable leading zeros
- **Dynamic Difficulty** — Automatically adjusts mining difficulty based on block generation time
- **Digital Signatures** — RSA-based message signing and verification prevents tampering
- **Replay Attack Protection** — Sequential message IDs ensure messages cannot be reused
- **Concurrent Mining** — Thread-safe design supports multiple simultaneous miners
- **Chain Validation** — Comprehensive integrity verification of the entire blockchain

## Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Main      │────▶│  Blockchain │────▶│    Block    │
│  (Miners)   │     │   (Chain)   │     │  (Record)   │
└─────────────┘     └──────┬──────┘     └─────────────┘
                          │
                          ▼
                   ┌─────────────┐
                   │   Message   │
                   │  (Signed)   │
                   └──────┬──────┘
                          │
              ┌───────────┴───────────┐
              ▼                       ▼
       ┌─────────────┐         ┌─────────────┐
       │   ShaUtil   │         │SignatureUtil│
       │  (SHA-256)  │         │    (RSA)    │
       └─────────────┘         └─────────────┘
```

## Components

| Component | Description |
|-----------|-------------|
| `Blockchain` | Manages the chain, coordinates mining, validates blocks and messages |
| `Block` | Immutable record containing block data, hash, and transactions |
| `Message` | Signed message with sender verification via public key cryptography |
| `ShaUtil` | SHA-256 hashing for block hash generation |
| `SignatureUtil` | RSA key generation, signing, and signature verification |

## How It Works

1. **Mining**: Miners generate random magic numbers until finding a hash with the required leading zeros
2. **Difficulty Adjustment**: If blocks are mined too quickly (<15s), difficulty increases; too slowly (>60s), it decreases
3. **Message Validation**: Each message must have a valid RSA signature and a unique sequential ID
4. **Chain Integrity**: Blocks are linked via cryptographic hashes; any tampering invalidates the chain

## Getting Started

### Prerequisites

- Java 21 or higher
- No external dependencies required

### Build & Run

```bash
# Compile
javac -d out blockchain/*.java

# Run
java -cp out blockchain.Main
```

### Configuration

Adjust parameters in `Main.java`:

```java
int numberOfMiners = 10;   // Concurrent mining threads
int numberOfBlocks = 5;    // Target chain length
```

## Example Output

```
Block:
Created by miner # 3
Id: 1
Timestamp: 1703894521000
Magic number: 7284619273648
Hash of the previous block:
0
Hash of the block:
00a7f3b2c8d4e5f6a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4
Block data: no messages
N was increased to 1
```

## Security Features

- **SHA-256 Hashing** — Cryptographically secure block hash generation
- **RSA Digital Signatures** — 2048-bit keys for message authentication
- **Replay Attack Prevention** — Monotonically increasing message IDs
- **Thread Safety** — Synchronized access to shared blockchain state

## Project Structure

```
blockchain/
├── Main.java           # Entry point, miner orchestration
├── Blockchain.java     # Core chain logic and validation
├── Block.java          # Block data structure
├── Message.java        # Signed message record
├── ShaUtil.java        # SHA-256 utilities
└── SignatureUtil.java  # RSA cryptography utilities
```

## License

This project is available for educational purposes.
