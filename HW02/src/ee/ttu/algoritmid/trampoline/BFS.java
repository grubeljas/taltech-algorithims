package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class BFS {

    public HashMap<Vertex, List<Vertex>> findShortestPaths(Vertex start, Vertex goal) {
        if (start == null || goal == null) return null;
        Queue<Vertex> unvisitedQueue = new LinkedList<>();
        HashMap<Vertex, Integer> distanceMap = new HashMap<>();
        HashMap<Vertex, List<Vertex>> previousMap = new HashMap<>();
        int distance = 0;
        boolean found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found){
            Vertex element = unvisitedQueue.poll();
            if (element == goal) found = true;
            HashSet<Vertex> connectedVertexes = element.getConnectedVertexes();
            for (Vertex v : connectedVertexes) {
                if(!distanceMap.containsKey(v) || distance < distanceMap.get(v)){
                    distanceMap.put(v, distance);
                    ArrayList<Vertex> list = new ArrayList<>();
                    list.add(element);
                    previousMap.put(v, list);
                    unvisitedQueue.add(v);
                } else if (distance == distanceMap.get(v)) {
                    previousMap.get(v).add(element);
                }
                if (!distanceMap.containsKey(v)){
                    unvisitedQueue.add(v);
                }
            }
            distance += 1;
        }
        return previousMap;
    }

    public HashMap<Vertex, Vertex> searchPathWithLowestFine(Vertex start, Vertex goal, HashMap<Vertex, List<Vertex>> paths){
        if (start == null || goal == null) return null;
        Queue<Vertex> unvisitedQueue = new LinkedList<>();
        HashMap<Vertex, Integer> fineMap = new HashMap<>();
        HashMap<Vertex, Vertex> previousMap = new HashMap<>();
        boolean found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found) {
            Vertex element = unvisitedQueue.poll();
            if (element == goal) found = true;
            List<Vertex> connectedVertexes = paths.get(element);
            if (connectedVertexes != null) {
                for (Vertex v : connectedVertexes) {
                    int fine = element.fine + v.fine;
                    if (!fineMap.containsKey(v) || fine < fineMap.get(v)) {
                        fineMap.put(v, fine);
                        previousMap.put(v, element);
                        unvisitedQueue.add(v);
                    }
                }
            }
        }
        return previousMap;
    }

    public List<Vertex> getPath(HashMap<Vertex, Vertex> map, Vertex source, Vertex goal){
        if (map == null || source == null || goal == null) return null;
        LinkedList<Vertex> result = new LinkedList<>();
        Vertex vertex = source;
        if (map.containsKey(source) || source == goal) {
            while (map.containsKey(vertex)) {
                result.add(vertex);
                vertex = map.get(vertex);
            }
            result.add(goal);
        }
        return result;
    }
}
