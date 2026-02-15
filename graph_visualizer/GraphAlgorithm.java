package visualizer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class GraphAlgorithm {

    /** Full name for the menu item, e.g. "Depth-First Search" */
    public abstract String getDisplayName();

    /**
     * Ordered list of vertex IDs to highlight step-by-step during animation.
     * Return empty list if no step animation is needed.
     *
     * @param startId  starting vertex ID
     * @param adj      adjacency map: vertexId -> list of {neighborId, weight} pairs (unsorted)
     */
    public List<String> getVisitOrder(String startId, Map<String, List<String[]>> adj) {
        return Collections.emptyList();
    }

    /**
     * Compute and return the final result string shown in the display label.
     *
     * @param startId  starting vertex ID
     * @param adj      adjacency map: vertexId -> list of {neighborId, weight} pairs (unsorted)
     */
    public abstract String formatResult(String startId, Map<String, List<String[]>> adj);
}