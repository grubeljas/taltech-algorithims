package ee.ttu.algoritmid.guessinggame;
public class Oracle {

    /**
     * Fruit.
     */
    private final Fruit fruit;

    /**
     * Oracle constructor.
     * @param fruit
     */
    public Oracle(final Fruit fruit) {
        this.fruit = fruit;
    }

    /**
     * @param fruitGuess - fruit you think is correct
     * @return
     *     "heavier" if correct fruit is heavier than your guess
     *     "lighter" if correct fruit is lighter than your guess
     *     "correct!" if your guess is correct
     */
    public String isIt(final Fruit fruitGuess) {
        if (this.fruit.getWeight() > fruitGuess.getWeight()) {
            return "heavier";
        } else if (this.fruit.getWeight() < fruitGuess.getWeight()) {
            return "lighter";
        }
        return "correct!";
    }
}
