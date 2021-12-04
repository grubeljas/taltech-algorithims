package ee.ttu.algoritmid.trampoline;

import java.util.LinkedList;
import java.util.List;

public class ResultSimple implements Result {

    public List<String> jumps = new LinkedList<>();
    public int fine = 0;

    @Override
    public List<String> getJumps() {
        return jumps;
    }

    public void setJumps(List<String> jumps) {
        this.jumps = jumps;
    }

    public void addJumps(String jump) {
        jumps.add(jump);
    }

    @Override
    public int getTotalFine() {
        return fine;
    }

    public void addFine(int add) {
        fine += add;
    }

}
