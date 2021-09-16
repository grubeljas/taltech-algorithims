package ee.ttu.algoritmid.guessinggame;
public class Fruit {

    /**
     * name.
     */
    private final String name;
    /**
     * weight.
     */
    private final int weight;

    /**
     * Fruit contructor.
     * @param name
     * @param weight
     */
    public Fruit(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * Get name.
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get weight.
     * @return
     */
    public int getWeight() {
        return this.weight;
    }
}
