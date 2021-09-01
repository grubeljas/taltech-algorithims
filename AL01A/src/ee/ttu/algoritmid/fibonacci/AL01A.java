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
    public Integer iterativeF(Integer n) {
        if (n == 1) {
            return 1;
        } else if (n == 0) {
            return 0;
        }
        Integer[] f = new Integer[n + 1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }
}
