package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainFrame extends JFrame {

    private static final int VERTEX_SIZE = 50;
    private static final int RADIUS = VERTEX_SIZE / 2;

    private String mode = "Add a Vertex";
    private JPanel graph;
    private JLabel modeLabel;
    private JLabel displayLabel;
    private JPanel selectedVertex = null;
    private final Set<String> addedEdges = new HashSet<>();
    private final Set<JPanel> visitedVertices = new HashSet<>();
    private GraphAlgorithm pendingAlgorithm = null; // set while waiting for start vertex

    public MainFrame() {
        setTitle("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        graph = new JPanel(null);
        graph.setName("Graph");

        modeLabel = new JLabel("Add a Vertex");
        modeLabel.setName("Mode");

        displayLabel = new JLabel("");
        displayLabel.setName("Display");

        setLayout(new BorderLayout());
        add(graph, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(modeLabel);
        bottom.add(Box.createHorizontalStrut(20));
        bottom.add(displayLabel);
        add(bottom, BorderLayout.SOUTH);

        setupMenuBar();

        graph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("Add a Vertex".equals(mode)) {
                    showVertexDialog(e.getX(), e.getY());
                } else if ("Add an Edge".equals(mode) && selectedVertex != null) {
                    selectedVertex.setOpaque(false);
                    selectedVertex.repaint();
                    selectedVertex = null;
                }
            }
        });

        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("MenuBar");

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.setName("New");
        newItem.addActionListener(e -> {
            graph.removeAll();
            addedEdges.clear();
            visitedVertices.clear();
            pendingAlgorithm = null;
            displayLabel.setText("");
            graph.revalidate();
            graph.repaint();
            setMode("Add a Vertex");
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("Exit");
        exitItem.addActionListener(e -> dispose());

        fileMenu.add(newItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Mode menu
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setName("Mode");

        JMenuItem addVertexItem = new JMenuItem("Add a Vertex");
        addVertexItem.setName("Add a Vertex");
        addVertexItem.addActionListener(e -> setMode("Add a Vertex"));

        JMenuItem addEdgeItem = new JMenuItem("Add an Edge");
        addEdgeItem.setName("Add an Edge");
        addEdgeItem.addActionListener(e -> setMode("Add an Edge"));

        JMenuItem removeVertexItem = new JMenuItem("Remove a Vertex");
        removeVertexItem.setName("Remove a Vertex");
        removeVertexItem.addActionListener(e -> setMode("Remove a Vertex"));

        JMenuItem removeEdgeItem = new JMenuItem("Remove an Edge");
        removeEdgeItem.setName("Remove an Edge");
        removeEdgeItem.addActionListener(e -> setMode("Remove an Edge"));

        JMenuItem noneItem = new JMenuItem("None");
        noneItem.setName("None");
        noneItem.addActionListener(e -> setMode("None"));

        modeMenu.add(addVertexItem);
        modeMenu.add(addEdgeItem);
        modeMenu.add(removeVertexItem);
        modeMenu.add(removeEdgeItem);
        modeMenu.add(noneItem);
        menuBar.add(modeMenu);

        // Algorithms menu — add new algorithms here, no other changes needed
        JMenu algoMenu = new JMenu("Algorithms");
        algoMenu.setName("Algorithms");

        for (GraphAlgorithm algo : List.of(
                new DFSAlgorithm(),
                new BFSAlgorithm(),
                new DijkstraAlgorithm(),
                new PrimAlgorithm())) {
            JMenuItem item = new JMenuItem(algo.getDisplayName());
            item.setName(algo.getDisplayName());
            item.addActionListener(e -> startAlgorithm(algo));
            algoMenu.add(item);
        }

        menuBar.add(algoMenu);
        setJMenuBar(menuBar);
    }

    private void setMode(String newMode) {
        mode = newMode;
        modeLabel.setText(newMode);
        pendingAlgorithm = null;
        if (!visitedVertices.isEmpty()) {
            visitedVertices.clear();
            graph.repaint();
        }
        if (selectedVertex != null) {
            selectedVertex.setOpaque(false);
            selectedVertex.repaint();
            selectedVertex = null;
        }
    }

    private void startAlgorithm(GraphAlgorithm algo) {
        visitedVertices.clear();
        graph.repaint();
        mode = "None";
        modeLabel.setText("None");
        if (selectedVertex != null) {
            selectedVertex.setOpaque(false);
            selectedVertex.repaint();
            selectedVertex = null;
        }
        pendingAlgorithm = algo;
        displayLabel.setText("Please choose a starting vertex");
    }

    private void handleAlgorithmStart(JPanel startVertex) {
        String startId = startVertex.getName().replace("Vertex ", "");
        GraphAlgorithm algo = pendingAlgorithm;
        pendingAlgorithm = null;
        displayLabel.setText("Please wait...");

        Map<String, List<String[]>> adj = buildAdjacency();

        SwingWorker<String, String> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                List<String> visitOrder = algo.getVisitOrder(startId, adj);
                for (String id : visitOrder) {
                    publish(id);
                    Thread.sleep(300);
                }
                // Guarantee "Please wait..." is visible even when there is no animation
                if (visitOrder.isEmpty()) {
                    Thread.sleep(300);
                }
                return algo.formatResult(startId, adj);
            }

            @Override
            protected void process(List<String> chunks) {
                for (String id : chunks) {
                    JPanel v = findVertex(id);
                    if (v != null) {
                        visitedVertices.add(v);
                        v.repaint();
                    }
                }
            }

            @Override
            protected void done() {
                try {
                    displayLabel.setText(get());
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /** Build unsorted adjacency map: vertexId -> list of {neighborId, weightStr} pairs. */
    private Map<String, List<String[]>> buildAdjacency() {
        Map<String, List<String[]>> adj = new HashMap<>();

        for (String edge : addedEdges) {
            int sep = edge.indexOf('-');
            String id1 = edge.substring(0, sep);
            String id2 = edge.substring(sep + 1);
            String weight = getEdgeWeight(id1, id2);

            adj.computeIfAbsent(id1, k -> new ArrayList<>()).add(new String[]{id2, weight});
            adj.computeIfAbsent(id2, k -> new ArrayList<>()).add(new String[]{id1, weight});
        }
        return adj;
    }

    private String getEdgeWeight(String id1, String id2) {
        for (Component c : graph.getComponents()) {
            String name = c.getName();
            if (("EdgeLabel <" + id1 + " -> " + id2 + ">").equals(name) ||
                    ("EdgeLabel <" + id2 + " -> " + id1 + ">").equals(name)) {
                return ((JLabel) c).getText();
            }
        }
        return "0";
    }

    private void showVertexDialog(int clickX, int clickY) {
        String id = JOptionPane.showInputDialog(null, "Enter Vertex ID:", "Vertex", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return;
        if (id.length() == 1 && !id.isBlank()) {
            addVertex(id, clickX - RADIUS, clickY - RADIUS);
        } else {
            SwingUtilities.invokeLater(() -> showVertexDialog(clickX, clickY));
        }
    }

    private void addVertex(String id, int x, int y) {
        JPanel vertex = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Color fill;
                if (isOpaque()) {
                    fill = Color.YELLOW;                    // selected for edge
                } else if (visitedVertices.contains(this)) {
                    fill = new Color(0, 200, 100);          // visited (green)
                } else {
                    fill = Color.WHITE;
                }
                g.setColor(fill);
                g.fillOval(0, 0, VERTEX_SIZE, VERTEX_SIZE);
                g.setColor(Color.BLACK);
                g.drawOval(0, 0, VERTEX_SIZE - 1, VERTEX_SIZE - 1);
            }
        };
        vertex.setName("Vertex " + id);
        vertex.setBounds(x, y, VERTEX_SIZE, VERTEX_SIZE);
        vertex.setOpaque(false);

        JLabel label = new JLabel(id, SwingConstants.CENTER);
        label.setName("VertexLabel " + id);
        label.setBounds(0, 0, VERTEX_SIZE, VERTEX_SIZE);
        vertex.add(label);

        MouseAdapter vertexClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("Add an Edge".equals(mode)) {
                    handleVertexClick(vertex);
                } else if ("Remove a Vertex".equals(mode)) {
                    removeVertex(vertex);
                } else if (pendingAlgorithm != null) {
                    handleAlgorithmStart(vertex);
                }
            }
        };
        vertex.addMouseListener(vertexClick);
        label.addMouseListener(vertexClick);

        graph.add(vertex);
        graph.revalidate();
        graph.repaint();
    }

    private void handleVertexClick(JPanel clicked) {
        if (selectedVertex == null) {
            selectedVertex = clicked;
            clicked.setOpaque(true);
            clicked.repaint();
        } else if (selectedVertex != clicked) {
            String id1 = selectedVertex.getName().replace("Vertex ", "");
            String id2 = clicked.getName().replace("Vertex ", "");
            selectedVertex.setOpaque(false);
            selectedVertex.repaint();
            selectedVertex = null;
            if (!addedEdges.contains(id1 + "-" + id2) && !addedEdges.contains(id2 + "-" + id1)) {
                showEdgeDialog(id1, id2);
            }
        }
    }

    private void removeVertex(JPanel vertex) {
        String id = vertex.getName().replace("Vertex ", "");
        Set<String> toRemove = new HashSet<>();
        for (String edge : addedEdges) {
            int sep = edge.indexOf('-');
            String a = edge.substring(0, sep);
            String b = edge.substring(sep + 1);
            if (a.equals(id) || b.equals(id)) toRemove.add(edge);
        }
        for (String edge : toRemove) {
            int sep = edge.indexOf('-');
            removeEdgeComponents(edge.substring(0, sep), edge.substring(sep + 1));
            addedEdges.remove(edge);
        }
        visitedVertices.remove(vertex);
        graph.remove(vertex);
        graph.revalidate();
        graph.repaint();
    }

    private void removeEdgeComponents(String id1, String id2) {
        for (Component c : graph.getComponents()) {
            String name = c.getName();
            if (name == null) continue;
            if (name.equals("Edge <" + id1 + " -> " + id2 + ">") ||
                    name.equals("Edge <" + id2 + " -> " + id1 + ">") ||
                    name.equals("EdgeLabel <" + id1 + " -> " + id2 + ">")) {
                graph.remove(c);
            }
        }
    }

    private void showEdgeDialog(String id1, String id2) {
        String weight = JOptionPane.showInputDialog(null, "Enter Weight:", "Edge", JOptionPane.QUESTION_MESSAGE);
        if (weight == null) return;
        if (weight.matches("-?\\d+")) {
            addEdge(id1, id2, weight);
        } else {
            SwingUtilities.invokeLater(() -> showEdgeDialog(id1, id2));
        }
    }

    private void addEdge(String id1, String id2, String weight) {
        JPanel v1 = findVertex(id1);
        JPanel v2 = findVertex(id2);
        if (v1 == null || v2 == null) return;

        addedEdges.add(id1 + "-" + id2);

        int cx1 = v1.getX() + RADIUS, cy1 = v1.getY() + RADIUS;
        int cx2 = v2.getX() + RADIUS, cy2 = v2.getY() + RADIUS;

        int bx = Math.min(cx1, cx2), by = Math.min(cy1, cy2);
        int bw = Math.max(Math.abs(cx1 - cx2), 1);
        int bh = Math.max(Math.abs(cy1 - cy2), 1);

        final int lx1 = cx1 - bx, ly1 = cy1 - by;
        final int lx2 = cx2 - bx, ly2 = cy2 - by;

        JComponent edgeAB = new JComponent() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.drawLine(lx1, ly1, lx2, ly2);
            }
        };
        edgeAB.setName("Edge <" + id1 + " -> " + id2 + ">");
        edgeAB.setBounds(bx, by, bw, bh);

        JComponent edgeBA = new JComponent() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.drawLine(lx2, ly2, lx1, ly1);
            }
        };
        edgeBA.setName("Edge <" + id2 + " -> " + id1 + ">");
        edgeBA.setBounds(bx, by, bw, bh);

        int mx = (cx1 + cx2) / 2, my = (cy1 + cy2) / 2;

        JLabel labelAB = new JLabel(weight);
        labelAB.setName("EdgeLabel <" + id1 + " -> " + id2 + ">");
        labelAB.setBounds(mx, my, 20, 20);

        MouseAdapter edgeClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("Remove an Edge".equals(mode)) {
                    removeEdgeComponents(id1, id2);
                    addedEdges.remove(id1 + "-" + id2);
                    addedEdges.remove(id2 + "-" + id1);
                    graph.revalidate();
                    graph.repaint();
                }
            }
        };
        edgeAB.addMouseListener(edgeClick);
        edgeBA.addMouseListener(edgeClick);
        labelAB.addMouseListener(edgeClick);

        graph.add(edgeAB);
        graph.add(edgeBA);
        graph.add(labelAB);
        graph.revalidate();
        graph.repaint();
    }

    private JPanel findVertex(String id) {
        for (Component c : graph.getComponents()) {
            if (c instanceof JPanel && ("Vertex " + id).equals(c.getName())) {
                return (JPanel) c;
            }
        }
        return null;
    }
}