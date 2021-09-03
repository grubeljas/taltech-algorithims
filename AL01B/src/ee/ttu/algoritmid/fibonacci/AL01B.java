package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01B {

    private final int BASE = 20;

    /**
     * Estimate exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        float time = methodViaSpeed(n);
        float time1 = methodViaProportion(n);
        return String.valueOf(convertNanoToYears(time));
    }

    public float methodViaSpeed(int n) {
        float timePerLine = computeSpeedOfBaseValue(BASE) / (iterativeF(BASE).multiply(BigInteger.valueOf(3L)).subtract(BigInteger.valueOf(2L))).floatValue();
        BigInteger number = iterativeF(n);
        return timePerLine * (number.multiply(BigInteger.valueOf(3L)).subtract(BigInteger.valueOf(2L))).floatValue();
    }

    public float methodViaProportion(int n) {
        float timeBase = computeSpeedOfBaseValue(BASE);
        float koefficient = computeSpeedOfBaseValue(BASE + 1) / timeBase;
        return  (float) (timeBase * (Math.pow(koefficient, (n - BASE))));
    }

    /**
     * Convert time from nanoseconds to years.
     * @param time time
     * @return years
     */
    public float convertNanoToYears(float time) {
        float millis = time / (1000F * 1000F);
        return millis / (365 * 24 * 60 * 60 * 1000F);
    }

    /**
     * Compute speed based on base value.
     * @param n base
     * @return time in nano
     */
    public float computeSpeedOfBaseValue(int n) {
        long start = System.nanoTime();
        BigInteger number = recursiveF(n); // 3 * F(n) - 2
        float time = (System.nanoTime() - start);
        return time;
        // return time / (number.multiply(BigInteger.valueOf(3L)).subtract(BigInteger.valueOf(2L))).floatValue();
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }

    /**
     * Compute the Fibonacci sequence numbr.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public BigInteger iterativeF(final int n) {
        BigInteger a = new BigInteger("0");
        BigInteger b = new BigInteger("1");
        BigInteger c;
        for (int i = 1; i <= n; i++) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return a;
    }

    public static void main(String[] args) {
        AL01B a = new AL01B();
    }
}
