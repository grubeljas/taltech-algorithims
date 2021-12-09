package ee.ttu.algoritmid.trampoline;

import java.util.List;

public class Node {
    int x, y, fine;
    List<String> jumps;
    Trampoline trampoline;

    public Node(int x, int y, int fine, List<String> jumps, Trampoline trampoline) {
        this.x = x;
        this.y = y;
        this.fine = fine;
        this.jumps = jumps;
        this.trampoline = trampoline;
    }
}
