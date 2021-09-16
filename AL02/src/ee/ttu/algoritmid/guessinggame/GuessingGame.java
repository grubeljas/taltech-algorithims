package ee.ttu.algoritmid.guessinggame;

import java.util.Arrays;
import java.util.Comparator;

public class GuessingGame {

    /**
     * oracle.
     */
    Oracle oracle;

    /**
     * Constructor.
     * @param oracle
     */
    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {
        fruitArray = sort(fruitArray);
        int low = 0;
        int high = fruitArray.length - 1;
        int mid = high / 2;

        while (true) {
            Fruit guessFruit = fruitArray[mid];
            String guess = oracle.isIt(fruitArray[mid]);
            if (guess.equals("correct!")) {
                return guessFruit.getName();
            } else if (guess.equals("lighter")) {
                high = mid - 1;
            } else if (guess.equals("heavier")) {
                low = mid + 1;
            }
            mid = (high - low) / 2 + low;
        }
    }

    /**
     * Sort array by weights.
     * @param fruits fruits assortment.
     * @return sorted.
     */
    public Fruit[] sort(final Fruit[] fruits) {
        return Arrays.stream(fruits)
                .sorted(Comparator.comparing(Fruit::getWeight))
                .toArray(Fruit[]::new);
    }
}
