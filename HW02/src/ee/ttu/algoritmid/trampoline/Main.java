package ee.ttu.algoritmid.trampoline;

import ee.ttu.algoritmid.trampoline.playalgorithm.DijkstraAlgorithm;
import ee.ttu.algoritmid.trampoline.playalgorithm.MappedWallsAlgorithm;
import ee.ttu.algoritmid.trampoline.playalgorithm.ModifiedDijkstra;
import ee.ttu.algoritmid.trampoline.trampolinefactory.Factory;

public class Main {
    public static void main(String[] args) {
//        testAverageTime(10000, 100, 10, 10);
        HW02 hw = new HW02();

        Trampoline[][] field = Factory.randomTrampolineField(10, 10, 1, 3, 0.05f, 0.1f);
        Factory.printTrampolineField(field);

        long startTime = System.currentTimeMillis();
        Result result = hw.play(field);
        System.out.println("time taken: " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println(result.getJumps());
        System.out.println("fine: " + result.getTotalFine());
    }

    /**
     * Test the average time of each algorithm completion.
     * @param n How many iterations to complete for average.
     * @param ignore How many iterations to ignore at the start (useful if JVM is slower when starting up).
     * @param x The width of the map.
     * @param y The height of the map.
     */
    private static void testAverageTime(int n, int ignore, int x, int y) {
        final boolean EXACT_JUMP = true;

        int counter = 0;
        long diTotalTime = 0;
        long mdTotalTime = 0;
        long mwTotalTime = 0;

        while (counter < n + ignore) {
            final Trampoline[][] field = Factory.randomTrampolineField(x, y, 1, 3, 0.02f, 0.1f);

            final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(field, EXACT_JUMP);
            final ModifiedDijkstra mDijkstra = new ModifiedDijkstra(field, EXACT_JUMP);
            final MappedWallsAlgorithm mapWall = new MappedWallsAlgorithm(field, EXACT_JUMP);

            final long diStartTime = System.currentTimeMillis();
            dijkstra.play();
            final Result diResult = dijkstra.getResult();
            final long diTimeTaken = System.currentTimeMillis() - diStartTime;

            final long mdStartTime = System.currentTimeMillis();
            mDijkstra.play();
            final Result mdResult = mDijkstra.getResult();
            final long mdTimeTaken = System.currentTimeMillis() - mdStartTime;

            final long mwStartTime = System.currentTimeMillis();
            mapWall.play();
            final Result mwResult = mapWall.getResult();
            final long mwTimeTaken = System.currentTimeMillis() - mwStartTime;

            if (counter >= ignore && diResult.getJumps().size() > 0) {
                diTotalTime += diTimeTaken;
                mdTotalTime += mdTimeTaken;
                mwTotalTime += mwTimeTaken;
                counter++;
            }
            if (counter < ignore) counter++;
        }
        final double dAvgTime = (double)diTotalTime / n;
        final double mdAvgTime = (double)mdTotalTime / n;
        final double mwAvgTime = (double)mwTotalTime / n;
        System.out.println("Dijkstra total time: " + diTotalTime);
        System.out.println("Modified total time: " + mdTotalTime);
        System.out.println("Map-Wall total time: " + mwTotalTime);
        System.out.println("Dijkstra average time for " + x + " by " + y + " map: " + dAvgTime + " (" + n + " cycles)");
        System.out.println("Modified average time for " + x + " by " + y + " map: " + mdAvgTime + " (" + n + " cycles)");
        System.out.println("Map Wall average time for " + x + " by " + y + " map: " + mwAvgTime + " (" + n + " cycles)");
    }
}
