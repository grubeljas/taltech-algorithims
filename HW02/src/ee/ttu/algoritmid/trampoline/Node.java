package ee.ttu.algoritmid.trampoline;

public class Node {

    // THESE ARE NEEDED FOR DIJKSTRA SOLUTION
    //public int x;
    //public int y;
    //public int h;
    //public String jump;
    public int nextX;
    public int nextY;

    public int fine;
    public int pathLength;

    public Node(int nextX, int nextY, int fine, int pathLength) {
        this.nextX = nextX;
        this.nextY = nextY;
        //this.jump = jump;
        this.fine = fine;
        this.pathLength = pathLength;
    }

    public Node() {

    }
}