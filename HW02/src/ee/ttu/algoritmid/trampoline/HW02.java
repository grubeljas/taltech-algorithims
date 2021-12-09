package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        Graph graph = new Graph();
        graph.create(map);
        Vertex start = graph.getStartVertex();
        Vertex end = graph.getEndVertex();

        BFS bfs = new BFS();
        HashMap<Vertex, List<Vertex>> shortestPaths = bfs.findShortestPaths(start, end);
        HashMap<Vertex, Vertex> pathWithLowestFine = bfs.searchPathWithLowestFine(end, start, shortestPaths);
        List<Vertex> finalPath = bfs.getPath(pathWithLowestFine, start, end);
        List<String> jumps = pathAsJumps(finalPath, end);

        return new Result() {
            @Override
            public List<String> getJumps() {
                return jumps;
            }

            @Override
            public int getTotalFine() {
                return HW02.this.getTotalFine(finalPath);
            }
        };
    }

    private int getTotalFine(List<Vertex> vertex) {
        if (vertex == null) return 0;
        int totalFine = 0;
        for (var v : vertex){
            totalFine = totalFine + v.fine;
        }
        return totalFine;
    }

    private List<String> pathAsJumps(List<Vertex> path, Vertex end) {
        List<String> jumps = new ArrayList<>();
        if (path == null || end == null) return jumps;
        for (int i = 0; i < path.size() - 1; ++i) {
            Point current = path.get(i).coordinate;
            Point next = path.get(i + 1).coordinate;
            String jump = null;
            if (current.x == next.x) jump = "E" + (next.y - current.y);
            if (current.y == next.y) jump = "S" + (next.x - current.x);
            if (jump != null) jumps.add(jump);
            if (next == end.coordinate) break;
        }
        return jumps;
    }

}
