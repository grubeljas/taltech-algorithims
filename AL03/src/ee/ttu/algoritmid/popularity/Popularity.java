package ee.ttu.algoritmid.popularity;

import java.util.Comparator;
import java.util.HashMap;

public class Popularity {

    private int max;
    public HashMap<Integer[], Integer> points = new HashMap<>();

    public Popularity(int maxCoordinates) {
        max = maxCoordinates;
    }

    /**
     * @param x, y - coordinates
     * adds a picture at the point with coordinates (x, y)
     */
    void addPoint(Integer x, Integer y) {
        Integer[] point = {x, y};
        if (points.size() < max && !points.containsKey(point)) {
            return;
        }
        points.put(point, points.getOrDefault(point, 0) + 1);
    }

    /**
     * @param x, y - coordinates
     * @return the number of occurrennces of the point
     */
    int pointPopularity(Integer x,Integer y) {
        return points.getOrDefault(new Integer[]{x,y}, 0);
    }


    /**
     * @return the number of occurrennces of the most popular point
     */
    int maxPopularity() {
        Integer[] maxPopularPoint = points.keySet().stream()
                .max(Comparator.comparing(x -> points.get(x))).get();
        return maxPopularPoint.length;
    }

}
