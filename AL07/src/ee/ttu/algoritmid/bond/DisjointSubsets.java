package ee.ttu.algoritmid.bond;

import java.util.HashMap;

public class DisjointSubsets {

    public HashMap<String, String> parent = new HashMap<>();
    public HashMap<String, Integer> rank = new HashMap<>();

    public String find(String element) throws IllegalArgumentException {
        checkElement(element);
        if (!parent.containsKey(element)) {
            throw new IllegalArgumentException();
        }

        if (!parent.get(element).equals(element)) {
            parent.put(element, find(parent.get(element)));
        }

        return parent.get(element);
    }

    public void union(String element1, String element2) throws IllegalArgumentException {
        checkElement(element1);
        checkElement(element2);
        if (!parent.containsKey(element1) || !parent.containsKey(element2)) {
            throw new IllegalArgumentException();
        }

        String xRoot = find(element1), yRoot = find(element2);
        if (xRoot.equals(yRoot))
            return;
        if (rank.get(xRoot) < rank.get(yRoot))
            parent.put(xRoot, yRoot);
        else if (rank.get(yRoot) < rank.get(xRoot))
            parent.put(yRoot, xRoot);
        else
        {
            parent.put(yRoot, xRoot);
            rank.put(xRoot, rank.get(xRoot) + 1);
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        checkElement(element);
        if (parent.containsKey(element)) {
            throw new IllegalArgumentException();
        }
        parent.put(element, element);
        rank.put(element, 1);
    }

    public void checkElement(String element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
    }
}
