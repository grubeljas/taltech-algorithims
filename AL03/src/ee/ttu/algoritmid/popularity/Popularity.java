package ee.ttu.algoritmid.popularity;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;

public class Popularity {

    private final int max;
    public HashMap<Integer, Integer> points = new HashMap<>();

    public Popularity(int maxCoordinates) {
        if (maxCoordinates >= 100000) {
            max = 99999;
        } else {
            max = maxCoordinates;
        }
    }

    /**
     * @param x, y - coordinates
     * adds a picture at the point with coordinates (x, y)
     */
    void addPoint(Integer x, Integer y) {
        Integer pointKey = new Point(x, y).hashCode();
        if (!points.containsKey(pointKey)) {
            if (points.size() < max) {
                points.put(pointKey, 1);
            }
        } else {
            Integer value = points.get(pointKey);
            points.put(pointKey, value + 1);
        }
    }

    /**
     * @param x, y - coordinates
     * @return the number of occurrennces of the point
     */
    int pointPopularity(Integer x,Integer y) {
        Integer pointKey = new Point(x, y).hashCode();
        if (points.containsKey(pointKey)) {
            return points.get(pointKey);
        } else {
            return 0;
        }
    }


    /**
     * @return the number of occurrennces of the most popular point
     */
    int maxPopularity() {
        if (points.isEmpty()) {
            return 0;
        }
        return (int)points.values().stream().sorted().toArray()[points.size() - 1];
    }
}
