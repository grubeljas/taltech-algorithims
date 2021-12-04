package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    private Trampoline[][] fMap;
    private int[] max;

    @Override
    public Result play(Trampoline[][] map) {
        fMap = map;
        Trampoline current = fMap[0][0];
        int[] coordinats = new int[]{0, 0};
        max = new int[]{fMap.length - 1, fMap[0].length - 1};
        ResultSimple result = new ResultSimple();
        Trampoline previous;
        return recJump(coordinats, result);
    }

    public Result recJump(int[] coordinats, ResultSimple result) {
        if (max[0] == coordinats[0] && max[1] == coordinats[1]) {
            return result;
        }
        System.out.println(result.getJumps());

        int jumpForce = fMap[coordinats[0]][coordinats[1]].getJumpForce();
        int[] southCoordinats, eastCoordinats;
        Result east = new ResultSimple(), south = new ResultSimple();

        if (coordinats[0] + jumpForce <= max[0]) {
            //South
            southCoordinats = new int[]{coordinats[0] + jumpForce, coordinats[1]};
            ResultSimple southResult = new ResultSimple();
            southResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
            southResult.addJumps("S" + jumpForce);
            south = recJump(southCoordinats, southResult);
        } else if (coordinats[1] + jumpForce <= max[1]) {
            //East
            eastCoordinats = new int[]{coordinats[0], coordinats[1] + jumpForce};
            ResultSimple eastResult = new ResultSimple();
            eastResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
            eastResult.addJumps("E" + jumpForce);
            east = recJump(eastCoordinats, eastResult);
        }

        if (south.getJumps().size() == 0) {
            return east;
        } else if (east.getJumps().size() == 0) {
            return south;
        }
        return (south.getJumps().size() < east.getJumps().size()) ? south : east;
    }

}
