package ee.ttu.algoritmid.trampoline.playalgorithm;

import ee.ttu.algoritmid.trampoline.Trampoline;
import ee.ttu.algoritmid.trampoline.implementation.Vec2;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlgorithm extends PlayAlgorithm {

    public DijkstraAlgorithm(Trampoline[][] map, boolean exactJump) {
        super(map, exactJump);
    }

    /**
     * Solve the map.
     * Save the result to the result object.
     */
    @Override
    public void play() {
        final HashMap<Node, Node> path = new HashMap<>();
        final PriorityQueue<Node> frontier = new PriorityQueue<>(new NodeComparatorReverse());

        final Node startPos = new Node(0, 0, 0, cost(0, 0));
        Node finalPos = null;
        final Vec2 goal = new Vec2(map[0].length - 1, map.length - 1);

        boolean path_found = false;

        frontier.add(startPos);
        path.put(startPos, null);

        while (!frontier.isEmpty()) {
            final Node current = frontier.poll();
            if (current.equalsPos(goal)) {
                path_found = true;
                finalPos = current;
                break;
            }

            final List<Vec2> neighbours = getNeighbours(current.x, current.y);
            for (Vec2 nb : neighbours) {
                if (!path.containsKey(nb)) {
                    final Node newNode = new Node(nb.x, nb.y, current.jumps + 1, current.cost + cost(nb.x, nb.y));
                    frontier.add(newNode);
                    path.put(newNode, current);
                }
            }
        }
        if (!path_found) return;

        reconstructPath(path, finalPos);
    }
}
