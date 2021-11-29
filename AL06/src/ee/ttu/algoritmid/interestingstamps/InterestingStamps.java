package ee.ttu.algoritmid.interestingstamps;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {

        List<Integer>[] seq = new LinkedList[sum+1];

        for (int i = 0; i < sum + 1; i++) {
            seq[i] = new LinkedList<Integer>();
        }

        for (int i = 0; i <= sum; i++) {
            seq[i] = new LinkedList<Integer>();
            for (int j = 0; j < stampOptions.size(); j++){
                if (stampOptions.get(j) <= i) {
                    List<Integer> variant = new LinkedList<>();
                    if (i != 0) {
                        seq[i] = max(seq[i], seq[i - 1]);
                    }
                    if (sum(seq[i - stampOptions.get(j)]) + stampOptions.get(j) <= i) {
                        variant = new LinkedList<>(seq[i - stampOptions.get(j)]);
                    }
                    variant.add(stampOptions.get(j));
                    seq[i] = max(seq[i], variant);
                    System.out.println(Arrays.toString(seq));
                }
            }
        }
        System.out.println(Arrays.toString(seq));
        return seq[sum];
    }

    private static int sum(List<Integer> stampOptions) {
        if (stampOptions.isEmpty()) {
            return 0;
        }
        return stampOptions.stream().mapToInt(Integer::intValue).sum();
    }

    private static List<Integer> getValues(List<Integer> stampOptions1, List<Integer> stampOptions2) {
        int h1 = 0, h2 = 0;
        for (Integer integer: stampOptions1) {
            h1 += ((integer % 10 == 0 || integer == 1) ? 0 : 1);
        }
        for (Integer integer: stampOptions2) {
            h2 += ((integer % 10 == 0 || integer == 1) ? 0 : 1);
        }
        return (h1 < h2) ? stampOptions2 : stampOptions1;
    }

    private static int max(int i, int j)
    {
        return (i > j) ? i : j;
    }

    private static List<Integer> max(List<Integer> i, List<Integer> j)
    {
        if (sum(i) == sum(j) && i.size() == j.size()) {
            return getValues(i, j);
        }
        return (sum(i) > sum(j) && i.size() <= j.size()) ? i : j;
    }

    public static void main(String[] args)
    {
        int W = 100;
        List<Integer> val = List.of(1, 10, 24, 30, 33, 36);
        System.out.println(findStamps(W, val));
    }

}