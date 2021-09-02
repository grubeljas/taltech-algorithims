package ee.ttu.algoritmid.fibonacci;

import javax.management.timer.Timer;
import java.math.BigInteger;

public class AL01B {

    /**
     * day in seconds.
     */
    private long dayInSeconds = Timer.ONE_DAY;
    private final long oNEYEAR = 365;

    /**
     * find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(final int n) {
        Long start = System.currentTimeMillis();
        iterativeF(n);
        Long finish = System.currentTimeMillis();
        Long time = finish - start;
        Float trueTime = calculateMillisToYears(time);
        return String.valueOf(trueTime);
    }

    /**
     * Compute the Fibonacci sequence numbr.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        Integer[] f = new Integer[n + 1];
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++)
            f[i] = f[i-1] + f[i-2];
        return String.valueOf(f[n]);
    }

    /**
     * Calculate millis to years.
     * @param millis
     * @return time in years
     */
    public float calculateMillisToYears(final Long millis) {
        float time;
        float millisInDouble = millis.floatValue();
        time = millisInDouble / dayInSeconds;
        time = time / oNEYEAR;
        return time;
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(final int n) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        }
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }
}
