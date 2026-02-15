import java.util.*;

public class DijkstraAlgorithm extends GraphAlgorithm {

    @Override
    public String getDisplayName() { return "Dijkstra's Algorithm"; }

    // No step-by-step animation — result shown all at once
    @Override
    public List<String> getVisitOrder(String startId, Map<String, List<String[]>> adj) {
        return Collections.emptyList();
    }

    @Override
    public String formatResult(String startId, Map<String, List<String[]>> adj) {
        Map<String, Integer> dist = dijkstra(startId, adj);

        // Build result: all reachable non-source vertices sorted alphabetically
        List<String> vertices = new ArrayList<>(dist.keySet());
        Collections.sort(vertices);
        List<String> pairs = new ArrayList<>();
        for (String v : vertices) {
            if (!v.equals(startId)) {
                pairs.add(v + "=" + dist.get(v));
            }
        }
        return String.join(", ", pairs);
    }

    private Map<String, Integer> dijkstra(String startId, Map<String, List<String[]>> adj) {
        Map<String, Integer> dist = new HashMap<>();
        for (String v : adj.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        dist.put(startId, 0);

        // Priority queue entries: {vertexId, currentCost}
        PriorityQueue<String[]> queue = new PriorityQueue<>(
                Comparator.comparingInt(a -> Integer.parseInt(a[1])));
        queue.offer(new String[]{startId, "0"});

        Set<String> settled = new HashSet<>();

        while (!queue.isEmpty()) {
            String[] curr = queue.poll();
            String u = curr[0];
            int d = Integer.parseInt(curr[1]);

            if (settled.contains(u)) continue;
            settled.add(u);

            for (String[] edge : adj.getOrDefault(u, Collections.emptyList())) {
                String v = edge[0];
                int w = Integer.parseInt(edge[1]);
                if (!settled.contains(v)) {
                    int newDist = d + w;
                    if (newDist < dist.getOrDefault(v, Integer.MAX_VALUE)) {
                        dist.put(v, newDist);
                        queue.offer(new String[]{v, String.valueOf(newDist)});
                    }
                }
            }
        }
        return dist;
    }
}
