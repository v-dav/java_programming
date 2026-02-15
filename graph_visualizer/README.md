# Graph Algorithms Visualizer

An interactive desktop application for building, editing, and running graph traversal and shortest-path algorithms with real-time visual feedback. Built with Java Swing.

---

## Table of Contents

- [Features](#features)
- [Algorithms](#algorithms)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Building & Running](#building--running)
- [Usage Guide](#usage-guide)
- [Architecture](#architecture)

---

## Features

| Category | Capability |
|---|---|
| **Graph editing** | Add / remove vertices and edges with click-driven dialogs |
| **Edge weights** | Every edge carries an integer weight (supports negative weights) |
| **Algorithm visualisation** | Step-by-step vertex highlighting during traversal (DFS / BFS) |
| **Result display** | Algorithm output shown in a persistent status label |
| **Soft reset** | *File → New* clears the canvas and restores the default mode |

---

## Algorithms

### Depth-First Search (DFS)
Explores as far as possible along each branch before backtracking. When multiple edges are available, the **lowest-weight edge** is followed first.

**Output format:** `DFS : A -> C -> B -> D`

---

### Breadth-First Search (BFS)
Explores all neighbours at the current depth before moving deeper. Neighbours are visited in ascending edge-weight order.

**Output format:** `BFS : A -> B -> C -> D`

---

### Dijkstra's Algorithm
Computes the shortest path (minimum total weight) from the selected source vertex to every other reachable vertex.

**Output format:** `B=2, C=5, D=7`  
Each pair is `<Vertex>=<Cost from source>`, sorted alphabetically. The source vertex itself is omitted.

---

### Prim's Algorithm
Builds a Minimum Spanning Tree (MST) — the subset of edges that connects all vertices with the smallest possible total weight and no cycles.

**Output format:** `B=A, C=A, D=B`  
Each pair is `<Child>=<Parent>` in the MST, sorted alphabetically by child. The root (selected source) is omitted.

---

## Project Structure

```
src/
└── visualizer/
    ├── ApplicationRunner.java   # Entry point — launches MainFrame
    ├── MainFrame.java           # Main JFrame: UI layout, graph state, event wiring
    ├── GraphAlgorithm.java      # Abstract base class for all algorithms
    ├── DFSAlgorithm.java        # Depth-First Search implementation
    ├── BFSAlgorithm.java        # Breadth-First Search implementation
    ├── DijkstraAlgorithm.java   # Dijkstra's shortest path implementation
    └── PrimAlgorithm.java       # Prim's MST implementation
```

---

## Requirements

| Requirement | Version |
|---|---|
| Java Development Kit (JDK) | 17 or higher |
| Operating System | Windows / macOS / Linux |

No external libraries or build tools are required — the project uses only the Java standard library and Swing (bundled with the JDK).

---

## Building & Running

### 1. Clone or download the source

```bash
git clone https://github.com/your-username/graph-algorithms-visualizer.git
cd graph-algorithms-visualizer
```

### 2. Compile

From the project root, compile all source files into an `out/` directory:

```bash
javac -d out src/visualizer/*.java
```

### 3. Run

```bash
java -cp out visualizer.ApplicationRunner
```

### One-liner (compile + run)

```bash
javac -d out src/visualizer/*.java && java -cp out visualizer.ApplicationRunner
```

---

### Running with an IDE

**IntelliJ IDEA**
1. Open the project root as a new project.
2. Mark `src/` as the *Sources Root* (right-click → *Mark Directory as → Sources Root*).
3. Run `ApplicationRunner` directly (`Shift+F10` or the green ▶ button).

**Eclipse**
1. *File → New → Java Project*, uncheck *Use default location*, point to the project root.
2. Right-click `ApplicationRunner.java` → *Run As → Java Application*.

---

## Usage Guide

### Menu overview

| Menu | Item | Action |
|---|---|---|
| **File** | New | Clears the entire graph and resets mode to *Add a Vertex* |
| **File** | Exit | Closes the application |
| **Mode** | Add a Vertex | Click anywhere on the canvas to place a new vertex |
| **Mode** | Add an Edge | Click two vertices in sequence to connect them with a weighted edge |
| **Mode** | Remove a Vertex | Click a vertex to delete it along with all its edges |
| **Mode** | Remove an Edge | Click an edge line or its weight label to delete that edge |
| **Mode** | None | Disables all editing interactions |
| **Algorithms** | Depth-First Search | Prompts for a start vertex, then runs DFS |
| **Algorithms** | Breadth-First Search | Prompts for a start vertex, then runs BFS |
| **Algorithms** | Dijkstra's Algorithm | Prompts for a start vertex, then computes shortest paths |
| **Algorithms** | Prim's Algorithm | Prompts for a start vertex, then computes the MST |

### Step-by-step workflow

1. **Build the graph** — switch to *Add a Vertex* mode (default on launch / after New). Click the canvas; enter a single-character ID in the dialog that appears.
2. **Connect vertices** — switch to *Add an Edge* mode. Click vertex A, then vertex B. Enter an integer weight. Repeat for all desired edges.
3. **Run an algorithm** — select it from the *Algorithms* menu. The status bar will read **"Please choose a starting vertex"**. Click any vertex to start.
4. **Read the result** — visited vertices are highlighted in green during traversal; the final result appears in the status bar once execution completes.
5. **Reset** — use *File → New* to start over with a blank canvas.

---

## Architecture

The application follows the **Open-Closed Principle**: `MainFrame` is closed for modification when adding new algorithms. Every algorithm lives in exactly one class that extends `GraphAlgorithm`.

```
GraphAlgorithm  (abstract)
│
├── getDisplayName()          → menu item label
├── getVisitOrder(...)        → ordered vertex IDs for step animation
│                               (return empty list for no animation)
└── formatResult(...)         → final string shown in the display label
```

Adding a new algorithm requires **only one new file** — a subclass of `GraphAlgorithm` — plus a single entry in the `List.of(...)` in `MainFrame.setupMenuBar()`.

The adjacency map passed to all algorithms is:

```
Map<String, List<String[]>>
  key   → vertex ID
  value → list of {neighbourId, weightString} pairs (unsorted)
```

Sorting by weight is the responsibility of each algorithm, enabling each to define its own neighbour-selection strategy independently.

## Credentials

Built following the Jetbrains Academy Spring Desktop Applicaiton Development course.
