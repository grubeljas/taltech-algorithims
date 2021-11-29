package ee.ttu.algoritmid.interestingstamps;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        stampOptions = stampOptions.stream().sorted().collect(Collectors.toList());
        List<Integer>[] seq = new LinkedList[sum+1];

        for (int i = 0; i <= sum; i += stampOptions.stream().mapToInt(c -> c).min().orElseThrow()) {
            List<Integer> variant;
            seq[i] = new LinkedList<Integer>();
            for (int j = 0; j < stampOptions.size(); j++){
                if (stampOptions.get(j) <= i) {
                    variant = seq[i - stampOptions.get(j)];
                    variant.add(stampOptions.get(j));
                    if (seq[i].size() >= variant.size() && sum(variant) <= sum
                    && sum(variant) - sum(seq[i]) > 0) {
                        seq[i] = getValues(seq[i], variant);
                    }
                    seq[i] = max(seq[i], variant);
                }
            }
        }
        System.out.println(Arrays.toString(seq));
        return seq[sum];
    }

    private static int sum(List<Integer> stampOptions) {
        return stampOptions.stream().mapToInt(Integer::intValue).sum();
    }

    private static List<Integer> getValues(List<Integer> stampOptions1, List<Integer> stampOptions2) {
        int h1 = 0, h2 = 0;
        for (Integer integer: stampOptions1) {
            h1 += ((integer % 10 == 0 || integer == 1) ? 1 : 0);
        }
        for (Integer integer: stampOptions2) {
            h2 += ((integer % 10 == 0 || integer == 1) ? 1 : 0);
        }
        return (h1 < h2) ? stampOptions2 : stampOptions1;
    }

    private static int max(int i, int j)
    {
        return (i > j) ? i : j;
    }

    private static List<Integer> max(List<Integer> i, List<Integer> j)
    {
        return (i.size() > j.size()) ? i : j;
    }

    public static void main(String[] args)
    {
        int W = 5;
        List<Integer> val = List.of(1, 2, 3);
        System.out.println(findStamps(W, val));
    }

}