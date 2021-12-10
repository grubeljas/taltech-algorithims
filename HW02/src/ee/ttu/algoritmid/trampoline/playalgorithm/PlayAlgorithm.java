package ee.ttu.algoritmid.trampoline.playalgorithm;

import ee.ttu.algoritmid.trampoline.Result;
import ee.ttu.algoritmid.trampoline.Trampoline;
import ee.ttu.algoritmid.trampoline.implementation.PlayResult;
import ee.ttu.algoritmid.trampoline.implementation.Vec2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public abstract class PlayAlgorithm {

    protected Trampoline[][] map;
    protected Result result = new PlayResult();
    protected boolean exactJump;

    public PlayAlgorithm(Trampoline[][] map, boolean exactJump) {
        this.map = map;
        this.exactJump = exactJump;
    }

    public Result getResult() {
        return result;
    }

    public abstract void play();

    /**
     * Reset the map.
     */
    public void setMap(Trampoline[][] newMap) {
        map = newMap;
        result = new PlayResult();
    }

    /**
     * Get the possible neighbours to a tile.
     * @return List of neighbours.
     */
    protected List<Vec2> getNeighbours(int x, int y) {
        if (positionObstructed(x, y)) return new ArrayList<>();
        return (exactJump) ? getExactNeighbours(x, y) : getNonExactNeighbours(x, y);
    }

    /**
     * Get the neighbours when it is only possible to jump = tiles.
     * @param x start x.
     * @param y start y.
     * @return List of possible next positions.
     */
    protected List<Vec2> getExactNeighbours(int x, int y) {
        final int force = map[y][x].getJumpForce();
        final List<Vec2> neighbours = new ArrayList<>(2);

        final Integer nextX = getFirstPosBeforeWallE(x, y, x + force);
        final Integer nextY = getFirstPosBeforeWallS(x, y, y + force);
        if (nextX != null) neighbours.add(new Vec2(nextX, y));
        if (nextY != null) neighbours.add(new Vec2(x, nextY));

        return neighbours;
    }

    /**
     * Get the neighbours when it is possible to jump +-1 tiles.
     * @param x start x.
     * @param y start y.
     * @return List of possible next positions.
     */
    protected List<Vec2> getNonExactNeighbours(int x, int y) {
        final int force = map[y][x].getJumpForce();
        final List<Vec2> neighbours = new ArrayList<>(6);

        final Integer nextX = getFirstPosBeforeWallE(x, y, x + force);
        final Integer nextY = getFirstPosBeforeWallS(x, y, y + force);

        if (nextX != null) {
            neighbours.add(new Vec2(nextX, y));
            if (nextX == x + force) {
                if (nextX - 1 != x) neighbours.add(new Vec2(nextX - 1, y));
                if (isPositionFree(nextX + 1, y)) neighbours.add(new Vec2(nextX + 1, y));
            }
        }

        if (nextY != null) {
            neighbours.add(new Vec2(x, nextY));
            if (nextY == y + force) {
                if (nextY - 1 != y) neighbours.add(new Vec2(x, nextY - 1));
                if (isPositionFree(x, nextY + 1)) neighbours.add(new Vec2(x, nextY + 1));
            }
        }

        return neighbours;
    }

    protected Integer getFirstPosBeforeWallE(int x1, int y, int x2) {
        // Check the starting tile and the adjacent tile.
        if (map[y][x1].getType() == Trampoline.Type.WALL
            || (x1 + 1 < map[0].length
                && map[y][x1 + 1].getType() == Trampoline.Type.WALL)) return null;

        for (int i = x1 + 2; i <= x2; i++) {
            if (i > map[0].length - 1) return null;
            if (map[y][i].getType() == Trampoline.Type.WALL) return i - 1;
        }
        return (x2 < map[0].length) ? x2 : null;
    }

    protected Integer getFirstPosBeforeWallS(int x, int y1, int y2) {
        if (map[y1][x].getType() == Trampoline.Type.WALL
            || (y1 + 1 < map.length
                && map[y1 + 1][x].getType() == Trampoline.Type.WALL)) return null;

        for (int i = y1 + 2; i <= y2; i++) {
            if (i > map.length - 1) return null;
            if (map[i][x].getType() == Trampoline.Type.WALL) return i - 1;
        }
        return (y2 < map.length) ? y2 : null;
    }

    private boolean isPositionFree(int x, int y) {
        return (y < map.length
                && x < map[0].length
                && map[y][x].getType() != Trampoline.Type.WALL);
    }

    /**
     * Get the fine at a position on the map.
     */
    protected int cost(int x, int y) {
        return (map[y][x].getType() == Trampoline.Type.WITH_FINE) ? map[y][x].getJumpForce() : 0;
    }

    /**
     * @return Manhattan-distance to the goal from given position.
     */
    protected int distanceToGoal(int x, int y) {
        final int xd = map[0].length - 1 - x;
        final int yd = map.length - 1 - y;
        return xd + yd;
    }

    /**
     * Check if the position is obstructed.
     * It is obstructed if:
     *      there are walls in both directions,
     *      the position is at the edge of the map,
     *      the jump force is 0.
     * @param x x coordinate.
     * @param y y coordinate.
     * @return True if obstructed.
     */
    protected boolean positionObstructed(int x, int y) {
        if (map[y][x].getJumpForce() <= 0) return true;
        return xObstructed(x, y) && yObstructed(x, y);
    }

    private boolean xObstructed(int x, int y) {
        if (x >= map[0].length - 1) return true;
        return (map[y][x+1].getType() == Trampoline.Type.WALL);
    }

    private boolean yObstructed(int x, int y) {
        if (y >= map.length - 1) return true;
        return (map[y+1][x].getType() == Trampoline.Type.WALL);
    }

    /**
     * Reconstruct the path starting from the goal.
     * @param path Map with the key being a position and the value is the position from which to get to it.
     * @param goal The goal position.
     */
    protected void reconstructPath(Map<Node, Node> path, Node goal) {
        Node currentNode = goal;
        Stack<Node> constructedPath = new Stack<>();

        while (currentNode != null) {
            constructedPath.push(currentNode);
            currentNode = path.get(currentNode);
        }

        Node lastNode = constructedPath.pop();
        final PlayResult res = ((PlayResult) result);
        while (!constructedPath.isEmpty()) {
            final Node n = constructedPath.pop();

            String s;
            if (n.x != lastNode.x) s = "E" + (n.x - lastNode.x);
            else s = "S" + (n.y - lastNode.y);
            res.addJump(s);
            res.setTotalFine(lastNode.cost);

            lastNode = n;
        }
    }


    /**
     * A node is a position on the map.
     */
    public static class Node {
        protected final int x, y;
        protected final int jumps;
        protected final int cost;

        public Node(int x, int y, int jumps, int cost) {
            this.x = x;
            this.y = y;
            this.jumps = jumps;
            this.cost = cost;
        }

        protected boolean equalsPos(Vec2 n) {
            return (n.x == x && n.y == y);
        }

        @Override
        public String toString() {
            return "N(" + x + "," + y + ")";
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y * 524288);
        }
    }


    /**
     * Comparator for comparing nodes.
     *      A node has higher value the fewer jumps it has.
     *      If it has equal number of jumps then a node has higher value the smaller the fine is.
     *      If the fine is equal the nodes are equal.
     */
    protected static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.jumps < o2.jumps) return 1;
            if (o1.jumps == o2.jumps && o1.cost != o2.cost) return (o1.cost < o2.cost) ? 1 : -1;
            return 0;
        }
    }

    protected static class NodeComparatorReverse implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.jumps > o2.jumps) return 1;
            if (o1.jumps < o2.jumps) return -1;
            if (o1.cost != o2.cost) return (o1.cost > o2.cost) ? 1 : -1;
            return 0;
        }
    }
}
