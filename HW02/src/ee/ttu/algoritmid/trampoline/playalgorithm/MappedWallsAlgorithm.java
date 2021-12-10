package ee.ttu.algoritmid.trampoline.playalgorithm;

import ee.ttu.algoritmid.trampoline.Trampoline;
import ee.ttu.algoritmid.trampoline.implementation.Vec2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class MappedWallsAlgorithm extends PlayAlgorithm {

    private Map<Integer, TreeSet<Integer>> wallMapE;    // map<y, tree>
    private Map<Integer, TreeSet<Integer>> wallMapS;    // map<x, tree>

    public MappedWallsAlgorithm(Trampoline[][] map, boolean exactJump) {
        super(map, exactJump);
    }

    /**
     * Solve the map.
     * Save the result to the result object.
     */
    @Override
    public void play() {
        createWallMap();
        final HashMap<PlayAlgorithm.Node, PlayAlgorithm.Node> path = new HashMap<>();
        final PriorityQueue<PlayAlgorithm.Node> frontier = new PriorityQueue<>(new PlayAlgorithm.NodeComparatorReverse());

        final PlayAlgorithm.Node startPos = new PlayAlgorithm.Node(0, 0, 0, cost(0, 0));
        PlayAlgorithm.Node finalPos = null;
        final Vec2 goal = new Vec2(map[0].length - 1, map.length - 1);

        boolean path_found = false;

        frontier.add(startPos);
        path.put(startPos, null);

        while (!frontier.isEmpty()) {
            final PlayAlgorithm.Node current = frontier.poll();
            if (current.equalsPos(goal)) {
                path_found = true;
                finalPos = current;
                break;
            }

            final List<Vec2> neighbours = getNeighbours(current.x, current.y);
            for (Vec2 nb : neighbours) {
                if (!path.containsKey(nb)) {
                    final PlayAlgorithm.Node newNode = new PlayAlgorithm.Node(nb.x, nb.y, current.jumps + 1, current.cost + cost(nb.x, nb.y));
                    frontier.add(newNode);
                    path.put(newNode, current);
                }
            }
        }
        if (!path_found) return;

        reconstructPath(path, finalPos);
    }

    private void createWallMap() {
        wallMapE = new HashMap<>();
        wallMapS = new HashMap<>();
        for (int x = 0; x < map[0].length; x++) {
            wallMapS.put(x, new TreeSet<>());
        }

        for (int y = 0; y < map.length; y++) {
            wallMapE.put(y, new TreeSet<>());
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x].getType() == Trampoline.Type.WALL) {
                    wallMapE.get(y).add(x);
                    wallMapS.get(x).add(y);
                }
            }
        }
    }

    @Override
    protected Integer getFirstPosBeforeWallE(int x1, int y, int x2) {
        // Check the starting tile and the adjacent tile.
        if (map[y][x1].getType() == Trampoline.Type.WALL
                || (x1 + 1 < map[0].length
                && map[y][x1 + 1].getType() == Trampoline.Type.WALL)) return null;

        Integer nextWall = wallMapE.get(y).ceiling(x1);
        if (nextWall == null || nextWall > x2) return (x2 < map[0].length) ? x2 : null;
        return nextWall - 1;
    }

    @Override
    protected Integer getFirstPosBeforeWallS(int x, int y1, int y2) {
        if (map[y1][x].getType() == Trampoline.Type.WALL
                || (y1 + 1 < map.length
                && map[y1 + 1][x].getType() == Trampoline.Type.WALL)) return null;

        Integer nextWall = wallMapS.get(x).ceiling(y1);
        if (nextWall == null || nextWall > y2) return (y2 < map.length) ? y2 : null;
        return nextWall - 1;
    }
}
