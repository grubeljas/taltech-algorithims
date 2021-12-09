package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02Previous implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        Node bestNode = minStepToReachTarget(map);
        return constructNewResult(new TrampolineCenterResult(), bestNode.jumps, bestNode.fine);
    }

    private boolean isInside(int x, int y, int xLength, int yLength) {
        return x >= 0 && x < xLength && y >= 0 && y < yLength;
    }

    private TrampolineCenterResult constructNewResult(TrampolineCenterResult result, List<String> jumps, int fine) {
        result.setFine(fine);
        result.setJumps(jumps);
        return result;
    }

    private Integer CheckForWallEast(int x, int y, Trampoline[][] map, int step) {
        for (int index = y; index < y + step; index++) {
            if (map[x][index].getType() == Trampoline.Type.WALL) {
                return index;
            }
        }
        return null;
    }

    private Integer CheckForWallSouth(int x, int y, Trampoline[][] map, int step) {
        for (int index = x; index < x + step; index++) {
            if (map[index][y].getType() == Trampoline.Type.WALL) {
                return index;
            }
        }
        return null;
    }

    public Node minStepToReachTarget(Trampoline[][] map) {
        List<Integer> targetPos = List.of(map.length - 1, map[0].length - 1);
        Queue<Node> queue = new LinkedList<>();
        ArrayList<String> jumps = new ArrayList<>();

        queue.add(new Node(0, 0, 0, jumps, map[0][0]));
        Node bestNode = null;

        int bestResultLength = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node.x == targetPos.get(0) && node.y == targetPos.get(1)) {
                if (bestNode == null || node.fine < bestNode.fine) {
                    bestNode = node;
                }
                bestResultLength = node.jumps.size();
                continue;
            }

            if (node.jumps.size() > bestResultLength) {
                return bestNode;
            }

            int jumpForce = node.trampoline.getJumpForce();
            int fine = node.fine;
            int y = node.y;
            int x = node.x;

            int nextSouthIndex = x + jumpForce;
            Integer wallSouth = CheckForWallSouth(x, y, map, jumpForce);
            if (wallSouth != null && wallSouth - 1 > 0) {
                nextSouthIndex = wallSouth - 1;
            }
            if (isInside(nextSouthIndex, y, map.length, map[0].length)) {
                int jumpModifier;
                if (wallSouth != null && nextSouthIndex == wallSouth - 1) {
                    jumpModifier = wallSouth - 1 - x;
                } else {
                    jumpModifier = jumpForce;
                }
                if (jumpModifier > 0) {
                    Trampoline nextTrampoline = map[nextSouthIndex][y];
                    int newFine = fine;
                    if (node.trampoline.getType() == Trampoline.Type.WITH_FINE) {
                        newFine += jumpForce;
                    }
                    List<String> joined = new ArrayList<>(node.jumps);
                    joined.add("S"+ jumpModifier);
                    queue.add(new Node(nextSouthIndex, y, newFine, joined, nextTrampoline));
                }
            }

            int nextEastIndex = y + jumpForce;
            Integer wallEast = CheckForWallEast(x, y, map, jumpForce);
            if (wallEast != null && wallEast - 1 > 0) {
                nextEastIndex = wallEast - 1;
            }
            if (isInside(x, nextEastIndex, map.length, map[0].length)) {
                int jumpModifier;
                if (wallEast != null && nextEastIndex == wallEast - 1) {
                    jumpModifier = wallEast - 1 - y;
                } else {
                    jumpModifier = jumpForce;
                }
                if (jumpModifier > 0) {
                    Trampoline nextTrampoline = map[x][nextEastIndex];
                    int newFine = fine;
                    if (node.trampoline.getType() == Trampoline.Type.WITH_FINE) {
                        newFine += jumpForce;
                    }
                    List<String> joined = new ArrayList<>(node.jumps);
                    joined.add("E" + jumpModifier);
                    queue.add(new Node(x, nextEastIndex, newFine, joined, nextTrampoline));
                }
            }
        }
        return bestNode;
    }
}