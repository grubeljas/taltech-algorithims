package ee.ttu.algoritmid.trampoline;

import java.util.LinkedList;
import java.util.List;

public class ResultImpl implements Result{

    private int fine;
    private final List<String> jumps;

    public ResultImpl(List<String> jumps, int fine) {
        this.jumps = jumps;
        this.fine = fine;
    }

    public ResultImpl(ResultImpl fromSouth) {
        this.fine = fromSouth.fine;
        this.jumps = new LinkedList<>(fromSouth.getJumps());
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    @Override
    public List<String> getJumps() {
        return jumps;
    }

    @Override
    public int getTotalFine() {
        return fine;
    }
}
