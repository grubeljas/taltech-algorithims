package ee.ttu.algoritmid.trampoline.playalgorithm;

import ee.ttu.algoritmid.trampoline.Trampoline;
import ee.ttu.algoritmid.trampoline.implementation.Vec2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ModifiedDijkstra extends PlayAlgorithm {

    public ModifiedDijkstra(Trampoline[][] map, boolean exactJump) {
        super(map, exactJump);
    }

    /**
     * Solve the map.
     * Save the result to the result object.
     * This is the same as DijkstraAlgorithm, except the frontier is saved in multiple priority queues
     * which themselves are stored in a hash map.
     */
    @Override
    public void play() {
        final HashMap<Node, Node> path = new HashMap<>();
        final Map<Integer, PriorityQueue<Node>> queueMap = new HashMap<>();

        final Node startPos = new Node(0, 0, 0, cost(0, 0));
        Node finalPos;
        final Vec2 goal = new Vec2(map[0].length - 1, map.length - 1);

        queueMap.put(0, new PriorityQueue<>(new NodeComparatorReverseSimple()));
        queueMap.get(0).add(startPos);
        path.put(startPos, null);

        int currentDepth = 0;

        while (true) {
            if (!queueMap.containsKey(currentDepth)) return;
            final PriorityQueue<Node> frontier = queueMap.get(currentDepth);
            final Node current = frontier.poll();
            if (current.equalsPos(goal)) {
                finalPos = current;
                break;
            }

            final List<Vec2> neighbours = getNeighbours(current.x, current.y);
            for (Vec2 nb : neighbours) {
                if (!path.containsKey(nb)) {
                    final Node newNode = new Node(nb.x, nb.y, current.jumps + 1, current.cost + cost(nb.x, nb.y));
                    if (!queueMap.containsKey(newNode.jumps))
                        queueMap.put(newNode.jumps, new PriorityQueue<>(new NodeComparatorReverseSimple()));
                    queueMap.get(newNode.jumps).add(newNode);
                    path.put(newNode, current);
                }
            }
            if (frontier.isEmpty()) currentDepth++;
        }

        reconstructPath(path, finalPos);
    }

    protected static class NodeComparatorReverseSimple implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.cost != o2.cost) return (o1.cost > o2.cost) ? 1 : -1;
            return 0;
        }
    }
}
