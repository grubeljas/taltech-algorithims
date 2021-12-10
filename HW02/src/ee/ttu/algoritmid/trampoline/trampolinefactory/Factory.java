package ee.ttu.algoritmid.trampoline.trampolinefactory;

import ee.ttu.algoritmid.trampoline.Trampoline;
import ee.ttu.algoritmid.trampoline.implementation.TrampolineTile;

import java.util.Random;

public class Factory {

    private static final Random RND = new Random();

    public static Trampoline[][] randomTrampolineField(int w, int h, int minF, int maxF, float wallChance, float fineChance) {
        Trampoline[][] field = new Trampoline[h][];

        for(int y = 0; y < h; y++) {
            field[y] = new Trampoline[w];

            for (int x = 0; x < w; x++) {
                field[y][x] = randomTrampoline(minF, maxF, wallChance, fineChance);
            }
        }

        return field;
    }

    public static Trampoline randomTrampoline(int minF, int maxF, float wallChance, float fineChance) {
        Trampoline.Type type = Trampoline.Type.NORMAL;
        if (RND.nextFloat() < fineChance) type = Trampoline.Type.WITH_FINE;
        if (RND.nextFloat() < wallChance) type = Trampoline.Type.WALL;

        return new TrampolineTile(
                RND.nextInt(maxF + 1 - minF) + minF,
                type
        );
    }

    private static final String RESET = "\033[0m";              // Text Reset
    private static final String RED_BACKGROUND = "\033[41m";    // RED
    private static final String BLUE_BACKGROUND = "\033[44m";   // BLUE

    public static void printTrampolineField(Trampoline[][] field) {
        for(Trampoline[] row : field) {
            for(Trampoline t : row) {
                if (t.getType() == Trampoline.Type.WITH_FINE) System.out.print(RED_BACKGROUND);
                else if (t.getType() == Trampoline.Type.WALL) System.out.print(BLUE_BACKGROUND);
                System.out.printf("%d, " + RESET, t.getJumpForce());
            }
            System.out.print("\n");
        }
    }
}
