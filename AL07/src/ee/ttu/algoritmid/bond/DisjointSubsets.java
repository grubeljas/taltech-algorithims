package ee.ttu.algoritmid.bond;

import java.util.HashMap;

public class DisjointSubsets {

    public HashMap<String, String> parent = new HashMap<>();
    public HashMap<String, Integer> rank = new HashMap<>();

    public String find(String element) throws IllegalArgumentException {
        checkElement(element);

        if (!parent.get(element).equals(element)) {
            parent.put(element, find(parent.get(element)));
        }

        return parent.get(element);
    }

    public void union(String element1, String element2) throws IllegalArgumentException {
        checkElement(element1);
        checkElement(element2);

        String xRoot = find(element1), yRoot = find(element2);

        // Elements are in the same set, no need
        // to unite anything.
        if (xRoot.equals(yRoot))
            return;

        // If x's rank is less than y's rank
        if (rank.get(xRoot) < rank.get(yRoot))

            // Then move x under y  so that depth
            // of tree remains less
            parent.put(xRoot, yRoot);

            // Else if y's rank is less than x's rank
        else if (rank.get(yRoot) < rank.get(xRoot))

            // Then move y under x so that depth of
            // tree remains less
            parent.put(yRoot, xRoot);

        else // if ranks are the same
        {
            parent.put(yRoot, xRoot);

            rank.put(xRoot, rank.get(xRoot) + 1);
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        checkElement(element);
        parent.put(element, element);
        rank.put(element, 1);
    }

    public void checkElement(String element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
    }
}
