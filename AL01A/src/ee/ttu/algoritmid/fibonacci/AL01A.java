/**
 * Package for fibonacci sequence iteratively.
 */
package ee.ttu.algoritmid.fibonacci;

public class AL01A {

    /**
     * Compute the Fibonacci sequence numbr.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public Integer iterativeF(final Integer n) {
        int a = 1;
        int b = 1;
        int c;
        for (Integer i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return a;
    }
}
