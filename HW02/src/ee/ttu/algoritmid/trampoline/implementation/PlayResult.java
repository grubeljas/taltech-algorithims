package ee.ttu.algoritmid.trampoline.implementation;

import ee.ttu.algoritmid.trampoline.Result;

import java.util.ArrayList;
import java.util.List;

public class PlayResult implements Result {

    private List<String> jumps = new ArrayList<>();
    private int totalFine = 0;

    @Override
    public List<String> getJumps() {
        return jumps;
    }

    @Override
    public int getTotalFine() {
        return totalFine;
    }

    public void addJump(String jump) {
        jumps.add(jump);
    }

    public void addFine(int fine) {
        totalFine += fine;
    }

    public void setTotalFine(int fine) {
        totalFine = fine;
    }
}
