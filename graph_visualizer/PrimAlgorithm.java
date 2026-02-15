package visualizer;

import java.util.*;

public class PrimAlgorithm extends GraphAlgorithm {

    @Override
    public String getDisplayName() { return "Prim's Algorithm"; }

    // No step-by-step vertex animation
    @Override
    public List<String> getVisitOrder(String startId, Map<String, List<String[]>> adj) {
        return Collections.emptyList();
    }

    @Override
    public String formatResult(String startId, Map<String, List<String[]>> adj) {
        // parent[v] = the vertex that connected v into the MST
        Map<String, String> parent = new LinkedHashMap<>();
        Set<String> inMST = new HashSet<>();

        // Min-heap entries: {weight, vertexId, parentId}
        PriorityQueue<String[]> pq = new PriorityQueue<>(
                Comparator.comparingInt(a -> Integer.parseInt(a[0])));

        inMST.add(startId);
        for (String[] edge : adj.getOrDefault(startId, Collections.emptyList())) {
            pq.offer(new String[]{edge[1], edge[0], startId});
        }

        while (!pq.isEmpty()) {
            String[] entry = pq.poll();
            String v = entry[1];
            String p = entry[2];

            if (inMST.contains(v)) continue;
            inMST.add(v);
            parent.put(v, p);

            for (String[] edge : adj.getOrDefault(v, Collections.emptyList())) {
                if (!inMST.contains(edge[0])) {
                    pq.offer(new String[]{edge[1], edge[0], v});
                }
            }
        }

        // Output Child=Parent pairs sorted alphabetically by child
        List<String> children = new ArrayList<>(parent.keySet());
        Collections.sort(children);
        List<String> pairs = new ArrayList<>();
        for (String child : children) {
            pairs.add(child + "=" + parent.get(child));
        }
        return String.join(", ", pairs);
    }
}