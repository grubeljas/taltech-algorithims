package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class HW02 implements TrampolineCenter {

    private Trampoline[][] fMap;
    private int[] max;

    @Override
    public Result play(Trampoline[][] map) {
        fMap = map;
        int[] coordinats = new int[]{0, 0};
        max = new int[]{fMap.length - 1, fMap[0].length - 1};
        ResultSimple result = new ResultSimple();
        if (hasWalls()) {
            return recJumpWithWalls(coordinats, result);
        } else {
            return recJumpSimple(coordinats, result);
        }
    }

    /**
     * Jump algorith with no wall and =.
     * @param coordinats
     * @param result
     * @return
     */
    public Result recJumpSimple(int[] coordinats, ResultSimple result) {
        if (max[0] == coordinats[0] && max[1] == coordinats[1]) {
            return result;
        }

        int jumpForce = fMap[coordinats[0]][coordinats[1]].getJumpForce();
        int[] southCoordinats, eastCoordinats;
        Result east = new ResultSimple(), south = new ResultSimple();
        int jump;

        if (coordinats[0] + jumpForce <= max[0]) {
            //South
            southCoordinats = new int[]{coordinats[0] + jumpForce, coordinats[1]};
            ResultSimple southResult = new ResultSimple();
            southResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
            southResult.addJumps("S" + jumpForce);
            south = recJumpSimple(southCoordinats, southResult);
        } else if (coordinats[1] + jumpForce <= max[1]) {
            //East
            eastCoordinats = new int[]{coordinats[0], coordinats[1] + jumpForce};
            ResultSimple eastResult = new ResultSimple();
            eastResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
            eastResult.addJumps("E" + jumpForce);
            east = recJumpSimple(eastCoordinats, eastResult);
        }

        if (south.getJumps().size() == 0) {
            return east;
        } else if (east.getJumps().size() == 0) {
            return south;
        }
        return (south.getJumps().size() < east.getJumps().size()) ? south : east;
    }

    public Result recJumpWithWalls(int[] coordinats, ResultSimple result) {
        System.out.println(result.getJumps());
        if (max[0] == coordinats[0] && max[1] == coordinats[1]) {
            return result;
        }

        int jumpForce = fMap[coordinats[0]][coordinats[1]].getJumpForce();
        int[] southCoordinats, eastCoordinats;
        Result east = new ResultSimple(), south = new ResultSimple();
        int jump;

        if (coordinats[0] != max[0]) {
            if (!fMap[coordinats[0] + 1][coordinats[1]].getType().equals(Trampoline.Type.WALL)) {
                //South
                jump = jumpForce;
                for (int i = 1; i < jumpForce; i++) {
                    if (coordinats[0] + i == max[0] + 1) {
                        break;
                    }
                    if (fMap[coordinats[0] + i][coordinats[1]].getType().equals(Trampoline.Type.WALL) ||
                            coordinats[0] + i > max[0]) { //мб разделить условия на два ифа
                        jump = i - 1;
                        break;
                    }
                }
                if (jump != jumpForce || coordinats[0] + jump <= max[0]) {
                    southCoordinats = new int[]{coordinats[0] + jump, coordinats[1]};
                    ResultSimple southResult = new ResultSimple();
                    southResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
                    southResult.addJumps("S" + jump);
                    south = recJumpWithWalls(southCoordinats, southResult);
                }
            }
        }
        if (max[1] != coordinats[1]) {
            if (!fMap[coordinats[0]][coordinats[1] + 1].getType().equals(Trampoline.Type.WALL)) {
                //East
                jump = jumpForce;
                for (int i = 1; i < jumpForce; i++) {
                    if (coordinats[1] + i == max[1] + 1) {
                        break;
                    }
                    if (fMap[coordinats[0]][coordinats[1] + i].getType().equals(Trampoline.Type.WALL) ||
                            coordinats[1] + i > max[1]) { //мб разделить условия на два ифа
                        jump = i - 1;
                        break;
                    }
                }
                if (jump != jumpForce || coordinats[1] + jump <= max[1]) {
                    eastCoordinats = new int[]{coordinats[0], coordinats[1] + jump};
                    ResultSimple eastResult = new ResultSimple();
                    eastResult.setJumps(new ArrayList<>(result.getJumps().subList(0, result.getJumps().size())));
                    eastResult.addJumps("E" + jump);
                    east = recJumpWithWalls(eastCoordinats, eastResult);
                }
            }
        }

        if (south.getJumps().size() == 0) {
            return east;
        } else if (east.getJumps().size() == 0) {
            return south;
        }
        return (south.getJumps().size() < east.getJumps().size()) ? south : east;
    }

    public boolean hasWalls() {
        for (Trampoline[] trampoline: fMap) {
            if (Arrays.stream(trampoline).anyMatch(trampoline1 -> trampoline1.getType().equals(Trampoline.Type.WALL))) {
                return true;
            }
        }
        return false;
    }

}
