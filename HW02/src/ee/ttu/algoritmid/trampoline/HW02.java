package ee.ttu.algoritmid.trampoline;

import ee.ttu.algoritmid.trampoline.implementation.PlayResult;
import ee.ttu.algoritmid.trampoline.playalgorithm.PlayAlgorithm;
import ee.ttu.algoritmid.trampoline.playalgorithm.DijkstraAlgorithm;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        if (!checkValidMap(map)) return new PlayResult();
        PlayAlgorithm algorithm = new DijkstraAlgorithm(map, false);
        algorithm.play();
        return algorithm.getResult();
    }

    private boolean checkValidMap(Trampoline[][] map) {
        return map.length != 0
                && map[0].length != 0
                && (map.length >= 2 || map[0].length >= 2);
    }
}
