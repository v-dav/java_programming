import java.util.*;

public class BFSAlgorithm extends GraphAlgorithm {

    @Override
    public String getDisplayName() { return "Breadth-First Search"; }

    @Override
    public List<String> getVisitOrder(String startId, Map<String, List<String[]>> adj) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startId);
        visited.add(startId);
        while (!queue.isEmpty()) {
            String id = queue.poll();
            result.add(id);
            // Sort neighbours by weight ascending before enqueuing
            List<String[]> neighbours = new ArrayList<>(adj.getOrDefault(id, Collections.emptyList()));
            neighbours.sort(Comparator.comparingInt(n -> Integer.parseInt(n[1])));
            for (String[] neighbour : neighbours) {
                if (!visited.contains(neighbour[0])) {
                    visited.add(neighbour[0]);
                    queue.add(neighbour[0]);
                }
            }
        }
        return result;
    }

    @Override
    public String formatResult(String startId, Map<String, List<String[]>> adj) {
        return "BFS : " + String.join(" -> ", getVisitOrder(startId, adj));
    }
}
