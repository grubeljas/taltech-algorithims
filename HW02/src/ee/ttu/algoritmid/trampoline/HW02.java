package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02 implements TrampolineCenter {

    Integer INF = Integer.MAX_VALUE;
    Trampoline goal;

    Node[][] nodes;

    /**
    * Returns row and column of a new square if jump to the east
    */
    public int[] getEastPart(Trampoline[][] map, int row, int column, int addon) {
        int columns = map[0].length;
        int force = map[row][column].getJumpForce() + addon;
        int[] indices = null;
        for (int i = 1; ((i + column) < columns) && ((force - i) >= 0); i++) {
            if (map[row][i + column].getType() == Trampoline.Type.WALL) {
                if (i != 1) {
                    indices = new int[]{row, i + column - 1};
                }
                break;
            } else {
                if ((force - i) == 0) {
                    indices = new int[]{row, i + column};
                    break;
                }
            }
        }
        return indices;
    }

    /**
    * Returns row and column of a new square if jump to the south
    */
    public int[] getSouthPart(Trampoline[][] map, int row, int column, int addon) {
        int rows = map.length;
        int force = map[row][column].getJumpForce() + addon;
        int[] indices = null;
        for (int i = 1; ((i + row) < rows) && ((force - i) >= 0); i++) {
            if (map[i + row][column].getType() == Trampoline.Type.WALL) {
                if (i != 1) {
                    indices = new int[]{i + row - 1, column};
                }
                break;
            } else {
                if ((force - i) == 0) {
                    indices = new int[]{i + row, column};
                    break;
                }
            }
        }
        return indices;
    }

    // THIS IS SECOND SOLUTION!!!

    /*public void resolveMapDijkstra(Trampoline[][] map) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                nodes[i][j] = new Node(0, 0, "", 0, INF);
                nodes[i][j].x = i;
                nodes[i][j].y = j;
            }
        }
        nodes[0][0] = new Node(0, 0, "", 0, 0);
        nodes[0][0].x = 0;
        nodes[0][0].y = 0;
        queue.add(nodes[0][0]);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == nodes[map.length - 1][map[0].length - 1]) {
                return;
            }
            int[] south = getSouthPart(map, current.x, current.y);
            int[] east = getEastPart(map, current.x, current.y);
            int fine = current.fine;
            if (map[current.x][current.y].getType() == Trampoline.Type.WITH_FINE) {
                fine += map[current.x][current.y].getJumpForce();
            }
            if (south != null) {
                int dist = current.pathLength + 1;

                if (dist < nodes[south[0]][south[1]].pathLength || fine < nodes[south[0]][south[1]].fine) {
                    Node next = nodes[south[0]][south[1]];
                    next.nextX = current.x;
                    next.nextY = current.y;
                    next.pathLength = dist;
                    next.fine = fine;
                    next.jump = "S" + (south[0] - current.x);
                    if (!queue.contains(next)) {
                        queue.add(next);
                    }
                }
            }
            if (east != null) {
                int dist = current.pathLength + 1;
                if (dist < nodes[east[0]][east[1]].pathLength || fine < nodes[east[0]][east[1]].fine) {
                    Node next = nodes[east[0]][east[1]];
                    next.nextX = current.x;
                    next.nextY = current.y;
                    next.pathLength = dist;
                    next.fine = fine;
                    next.jump = "E" + (east[1] - current.y);
                    if (!queue.contains(next)) {
                        queue.add(next);
                    }
                }
            }
        }
    }*/


    public void setCurrent(Node current, Node nextNode, int[] jump, int fine) {
        current.fine = fine + nextNode.fine;
        current.nextX = jump[0];
        current.nextY = jump[1];
        current.pathLength = nextNode.pathLength + 1;
    }

    /**
    * Set solution of a given square.
    * Basic version:
    * solution(i, j) = jump + best(solution(south), solution(east))
    * Alternative version:
    * solution(i, j) = jump + best(solution(south), solution(east), solution(east+1), solution(south+1), solution(east-1), solution(south-1))
    *
    * solution(goal) = ""
    */
    public void setSolutionAlternative(Trampoline[][] map, int row, int column, int forceAdd) {
        int fine = 0;
        if (nodes[row][column] == null) {
            nodes[row][column] = new Node(0, 0, 0, INF);
        }
        Node current = nodes[row][column];
        if (map[row][column].getType() == Trampoline.Type.WITH_FINE) {
            fine = map[row][column].getJumpForce();
        }
        int[] east = getEastPart(map, row, column, forceAdd);
        int[] south = getSouthPart(map, row, column, forceAdd);
        if (east != null && nodes[east[0]][east[1]] == null) {
            east = null;
        }
        if (south != null && nodes[south[0]][south[1]] == null) {
            south = null;
        }
        if (east == null && south == null) {
            if (current.pathLength == INF) {
                nodes[row][column] = null;
            }
        } else if (east == null) {
            Node southNode = nodes[south[0]][south[1]];
            if ((southNode.pathLength + 1) < current.pathLength) {
                setCurrent(current, southNode, south, fine);
            } else if ((southNode.pathLength + 1) == current.pathLength) {
                if (southNode.fine <= current.fine) {
                    setCurrent(current, southNode, south, fine);
                }
            }
        } else if (south == null) {
            Node eastNode = nodes[east[0]][east[1]];
            if ((eastNode.pathLength + 1) < current.pathLength) {
                setCurrent(current, eastNode, east, fine);
            } else if ((eastNode.pathLength + 1) == current.pathLength) {
                if (eastNode.fine <= current.fine) {
                    setCurrent(current, eastNode, east, fine);
                }
            }
        } else {
            Node eastNode = nodes[east[0]][east[1]];
            Node southNode = nodes[south[0]][south[1]];
            Node nextNode;
            int[] next;
            if (eastNode.pathLength < southNode.pathLength) {
                nextNode = eastNode;
                next = east;
            } else if (eastNode.pathLength == southNode.pathLength) {
                if (eastNode.fine <= southNode.fine) {
                    nextNode = eastNode;
                    next = east;
                } else {
                    nextNode = southNode;
                    next = south;
                }
            } else {
                nextNode = southNode;
                next = south;
            }
            if ((nextNode.pathLength + 1) < current.pathLength) {
                setCurrent(current, nextNode, next, fine);
            } else if ((nextNode.pathLength + 1) == current.pathLength) {
                if (nextNode.fine <= current.fine) {
                    setCurrent(current, nextNode, next, fine);
                }
            }
        }
    }

    /**
    * Iterate through map from south-east corner to north-west corner and get solution for every square.
    */
    public void resolveMapIterative(Trampoline[][] map) {
        int width = map.length;
        int height = map[0].length;
        nodes[width - 1][height - 1] = new Node(-1, 0, 0, 0);
        int j = 1;
        for (int i = 0; i < width; i++) {
            for (;j < height; j++) {
                int row = width - 1 - i;
                int column = height - 1 - j;
                if (map[row][column].getJumpForce() != 0) {
                    setSolutionAlternative(map, row, column, 1);
                    setSolutionAlternative(map, row, column, -1);
                }
                setSolutionAlternative(map, row, column, 0);

            }
            j = 0;
        }
    }

    @Override
    public Result play(Trampoline[][] map) {

        nodes = new Node[map.length][map[0].length];

        // THIS IS SECOND SOLUTION!!!
        /*resolveMapDijkstra(map);
        goal = map[map.length - 1][map[0].length - 1];
        List<String> jumps = new LinkedList<>();
        Node end = nodes[map.length - 1][map[0].length - 1];
        while (!end.jump.equals("")) {
            jumps.add(end.jump);
            end = nodes[end.nextX][end.nextY];
        }
        Collections.reverse(jumps);
        ResultImpl result = new ResultImpl(jumps, nodes[map.length - 1][map[0].length - 1].fine);*/

        resolveMapIterative(map);
        List<String> jumps = new LinkedList<>();
        Node start = nodes[0][0];
        Node next = nodes[start.nextX][start.nextY];

        // This recreates a list of jumps from nodes[][] array.
        // ---------------------------------------------------
        if (start.nextX == 0) {
            jumps.add("E".concat(String.valueOf(start.nextY)));
        } else if (start.nextY == 0) {
            jumps.add("S".concat(String.valueOf(start.nextX)));
        }
        while (next.nextX != -1) {
            if (start.nextX == next.nextX) {
                jumps.add("E".concat(String.valueOf(next.nextY - start.nextY)));
            } else if (start.nextY == next.nextY) {
                jumps.add("S".concat(String.valueOf(next.nextX - start.nextX)));
            }
            start = next;
            next = nodes[start.nextX][start.nextY];
        }
        // -------------------------------------------------------
        ResultImpl result = new ResultImpl(jumps, nodes[0][0].fine);
        return result;
    }

    public static void main(String[] args) {
        /*Trampoline[][] test0 = new Trampoline[3][5];
        int[][] forceMap = {
                {2, 1, 1, -1, 1},
                {1, 0, 1, -1, 1},
                {1, 1, 1, 1, 0},
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (forceMap[i][j] == -1) {
                    test0[i][j] = new TrampolineImpl(0, Trampoline.Type.WALL);
                } else {
                    test0[i][j] = new TrampolineImpl(forceMap[i][j], Trampoline.Type.NORMAL);
                }

            }
        }
        HW02 hw02 = new HW02();
        Result result = hw02.play(test0);
        System.out.println("dsdfsdf");

        Trampoline[][] test4 = new Trampoline[5][3];
        forceMap = new int[][]{
                {3, 1, 4},
                {2, 0, 3},
                {0, 0, 0},
                {1, 0, -1},
                {1, 1, 0}
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (forceMap[i][j] == -1) {
                    test4[i][j] = new TrampolineImpl(0, Trampoline.Type.WALL);
                } else {
                    test4[i][j] = new TrampolineImpl(forceMap[i][j], Trampoline.Type.NORMAL);
                }

            }
        }

        hw02 = new HW02();
        result = hw02.play(test4);
        System.out.println("dsdfsdf");


        Trampoline[][] test = new Trampoline[3][4];
        forceMap = new int[][]{
                {10, 2, -1, 2},
                {3, 1, 4, 1},
                {-1, 2, 2, 0}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (forceMap[i][j] == -1) {
                    test[i][j] = new TrampolineImpl(0, Trampoline.Type.WALL);
                } else {
                    test[i][j] = new TrampolineImpl(forceMap[i][j], Trampoline.Type.NORMAL);
                }

            }
        }
        hw02 = new HW02();
        Result ResultImpl = hw02.play(test);
        System.out.println("fsdfsf");*/

        HW02 hw02 = new HW02();
        Trampoline[][] test2 = new Trampoline[4][4];
        int[][] forceMap2 = {
                {2, 2, 2, 2},
                {3, 10, 1, 1},
                {2, 2, 1, 1},
                {2, 2, 1, 0}
        };
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                test2[i][j] = new TrampolineImpl(forceMap2[i][j], Trampoline.Type.NORMAL);
            }
        }
        //test2[3][2] = new TrampolineImpl(1, Trampoline.Type.WITH_FINE);
        //test2[2][3] = new TrampolineImpl(2, Trampoline.Type.WITH_FINE);
        test2[0][3] = new TrampolineImpl(2, Trampoline.Type.WITH_FINE);
        test2[3][0] = new TrampolineImpl(2, Trampoline.Type.WITH_FINE);
        test2[0][0] = new TrampolineImpl(3, Trampoline.Type.WITH_FINE);
        Result ResultImpl = hw02.play(test2);
        System.out.println("fsdfsf");
        Trampoline[][] test3 = new Trampoline[500][500];
        hw02 = new HW02();
        //Node[][] text = new Node[1000][1000];
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                test3[i][j] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
                //text[i][j] = new Node(0, 0, "S", 0, 0);
            }
        }
        long start1 = System.nanoTime();
        ResultImpl = hw02.play(test3);
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));

        System.out.println(ResultImpl.getJumps().size());
        System.out.println("fsdfsf");
    }
}
