package visualizer;

import java.util.*;

public class DFSAlgorithm extends GraphAlgorithm {

    @Override
    public String getDisplayName() { return "Depth-First Search"; }

    @Override
    public List<String> getVisitOrder(String startId, Map<String, List<String[]>> adj) {
        List<String> result = new ArrayList<>();
        dfs(startId, adj, new LinkedHashSet<>(), result);
        return result;
    }

    private void dfs(String id, Map<String, List<String[]>> adj,
                     Set<String> visited, List<String> result) {
        visited.add(id);
        result.add(id);
        // Sort neighbours by weight ascending before visiting
        List<String[]> neighbours = new ArrayList<>(adj.getOrDefault(id, Collections.emptyList()));
        neighbours.sort(Comparator.comparingInt(n -> Integer.parseInt(n[1])));
        for (String[] neighbour : neighbours) {
            if (!visited.contains(neighbour[0])) {
                dfs(neighbour[0], adj, visited, result);
            }
        }
    }

    @Override
    public String formatResult(String startId, Map<String, List<String[]>> adj) {
        return "DFS : " + String.join(" -> ", getVisitOrder(startId, adj));
    }
}