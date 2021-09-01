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
    public String iterativeF(final Integer n) {
        int a = 0;
        int b = 1;
        int c;
        for (Integer i = 1; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return String.valueOf(a);
    }
}
