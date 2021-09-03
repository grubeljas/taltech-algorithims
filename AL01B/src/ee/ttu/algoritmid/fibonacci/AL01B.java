package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01B {

    /**
     * Estimate exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        float timePerLine = computeSpeedOfBaseValue(10);
        BigInteger number = iterativeF(n);
        float time = timePerLine * (number.multiply(BigInteger.valueOf(3L))
                .subtract(BigInteger.valueOf(2L))).floatValue() ;
        return String.valueOf(convertNanoToYears(time));
    }

    public float convertNanoToYears(float time) {
        float nano = time;
        float millis = nano / (1000F * 1000F);
        float years = millis / (365 * 24 * 60 * 60 * 1000F);
        return years;
    }

    public float computeSpeedOfBaseValue(int n) {
        long start = System.nanoTime();
        BigInteger number = recursiveF(n); // 3 * F(n) - 2
        float time = (System.nanoTime() - start);
        return time / (number.multiply(BigInteger.valueOf(3L))
                .subtract(BigInteger.valueOf(2L))).floatValue();
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

    public static void main(String[] args) throws InterruptedException {
        AL01B a = new AL01B();
        System.out.println(a.timeToComputeRecursiveFibonacci(70));
    }
}
