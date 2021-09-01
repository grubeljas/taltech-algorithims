package ee.ttu.algoritmid.fibonacci;

import javax.management.timer.Timer;
import java.math.BigInteger;

public class AL01B {

    /**
     * day in seconds.
     */
    private long dayInSeconds = Timer.ONE_DAY * 365;

    /**
     * find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(final int n) {
        long start = System.currentTimeMillis();
        recursiveF(n);
        long finish = System.currentTimeMillis();
        long time = finish - start;
        time = calculateMillisToYears(time);
        return String.valueOf(time);
    }

    /**
     * Calculate millis to years.
     * @param millis
     * @return time in years
     */
    public long calculateMillisToYears(final long millis) {
        long time;
        time = millis / dayInSeconds;
        time = time / yearInDays;
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
