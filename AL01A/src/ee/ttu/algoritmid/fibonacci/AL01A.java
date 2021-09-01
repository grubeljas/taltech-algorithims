/**
 * Package for fibonacci sequence iteratively.
 */
package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {

    /**
     * Compute the Fibonacci sequence numbr.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(final int n) {
        BigInteger a = new BigInteger("0");
        BigInteger b = new BigInteger("1");
        BigInteger c;
        for (int i = 1; i <= n; i++) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return String.valueOf(a);
    }
}
