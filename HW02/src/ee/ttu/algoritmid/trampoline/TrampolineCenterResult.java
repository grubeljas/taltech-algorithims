package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class TrampolineCenterResult implements Result {
    private List<String> jumps;
    private int fine;

    public TrampolineCenterResult() {
        this.jumps = new ArrayList<>();
        this.fine = 0;
    }

    @Override
    public List<String> getJumps() {
        return this.jumps;
    }

    @Override
    public int getTotalFine() {
        return this.fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public void setJumps(List<String> jumps) {
        this.jumps = jumps;
    }

    public void addJump(String jump) {
        this.jumps.add(jump);
    }

    public void removeLastJump() {
        this.jumps.remove(jumps.size() - 1);
    }
}
