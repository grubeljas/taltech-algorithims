package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;

public class HW02 implements TrampolineCenter {

    private Trampoline[][] fMap;
    private int[] max;

    @Override
    public Result play(Trampoline[][] map) {
        fMap = map;
        int[] coordinats = new int[]{0, 0};
        max = new int[]{fMap.length - 1, fMap[0].length - 1};
        ResultSimple result = new ResultSimple();
        return recJump(coordinats, result);
    }

    public Result recJump(int[] coordinats, ResultSimple result) {
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
                    south = recJump(southCoordinats, southResult);
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
                    east = recJump(eastCoordinats, eastResult);
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

}
