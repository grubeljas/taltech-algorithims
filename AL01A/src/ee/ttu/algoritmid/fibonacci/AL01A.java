package ee.ttu.algoritmid.fibonacci;

public class AL01A {

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

    public static void main(String[] args) {
        AL01A a = new AL01A();
        System.out.println(a.iterativeF(3));
        System.out.println(a.iterativeF(5));
        System.out.println(a.iterativeF(4));
        System.out.println(a.iterativeF(6));
    }
}
