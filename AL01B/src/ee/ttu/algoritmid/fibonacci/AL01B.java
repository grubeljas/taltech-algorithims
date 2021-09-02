package ee.ttu.algoritmid.fibonacci;

import javax.management.timer.Timer;
import java.math.BigInteger;

public class AL01B {

    /**
     * day in seconds.
     */
    private long dayInSeconds = Timer.ONE_DAY;
    private final long oNEYEAR = 365;
    private Long TIMEPERLINE; //in millis

    public AL01B() {
        computeSpeed(10);
    }

    /**
     * find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(final int n) {
        BigInteger fN = iterativeF(n);
        BigInteger numberOfLines = fN.multiply(BigInteger.valueOf(3L)).subtract(BigInteger.valueOf(2L));
        float time = calculateMillisToYears(numberOfLines.multiply(BigInteger.valueOf(TIMEPERLINE)));
        return String.valueOf(time);
    }

    public void computeSpeed(int base) {
        Long start = System.currentTimeMillis();  // 3 F(n) - 2
        BigInteger f = recursiveF(base);
        Long finish = System.currentTimeMillis();
        finish -= start;
        Long l = finish / (3 * f.intValue() - 2);
        TIMEPERLINE = l;
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

    /**
     * Calculate millis to years.
     * @param millis
     * @return time in years
     */
    public float calculateMillisToYears(final BigInteger millis) {
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

    public static void main(String[] args) {
        AL01B a = new AL01B();
        System.out.println(a.iterativeF(10));
        System.out.println(a.timeToComputeRecursiveFibonacci(10));
    }

}

