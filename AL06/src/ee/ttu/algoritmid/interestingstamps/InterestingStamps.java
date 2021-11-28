package ee.ttu.algoritmid.interestingstamps;
import javax.swing.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        int len = stampOptions.size();
        stampOptions = stampOptions.stream().sorted().collect(Collectors.toList());
        List<Integer> stampValues = getValues(stampOptions);
        int max, current = 0;
        int v[] = new int[sum + 1]; //map of value of collection
        int s[] = new int[sum + 1]; //map of sum of collection
        int size[] = new int[sum + 1]; //map of size of collections
        LinkedList<Integer> seq[] = new LinkedList[sum + 1];

        for (int i = 0; i <= sum; i += stampOptions.stream().mapToInt(c -> c).min().orElseThrow()) {
            LinkedList<Integer> linkedList = new LinkedList<>();
            for (int j = 0; j < stampOptions.size(); j++){
                if (stampOptions.get(j) <= i) {
                    if (i == 100) {
                        System.out.println(s);
                    }
                    if (s[i] <= s[i - stampOptions.get(j)] + stampOptions.get(j) &&
                            s[i - stampOptions.get(j)] + stampOptions.get(j) <= i) {

                        s[i] = s[i - stampOptions.get(j)] + stampOptions.get(j); //МБ ПЕРЕДВИНУТЬ ВНИЗ
                        if (size[i] >= size[i - stampOptions.get(j)] + 1) {
                            if (v[i] < v[i - stampOptions.get(j)] + stampValues.get(j)) {
                                s[i] = s[i - stampOptions.get(j)] + stampOptions.get(j);
                                size[i] = size[i - stampOptions.get(j)] + 1;
                                v[i] = v[i - stampOptions.get(j)] + stampValues.get(j);
                                seq[i].add(stampOptions.get(j));
                            }
                        }

                    }


                    s[i] = max(s[i], s[i - stampOptions.get(j)] + stampOptions.get(j));
                    v[i] = max(v[i], v[i - stampOptions.get(j)] + stampValues.get(j));
                }
            }
        }
        return seq[sum];
    }

    private static List<Integer> getValues(List<Integer> stampOptions) {
        List<Integer> integerList = new LinkedList<>();
        for (Integer integer: stampOptions) {
            integerList.add((integer % 10 == 0 || integer == 1) ? 1 : 0);
        }
        return integerList;
    }

    private static int max(int i, int j)
    {
        return (i > j) ? i : j;
    }

    public static void main(String[] args)
    {
        int W = 5;
        List<Integer> val = List.of(1, 2, 3);
        System.out.println(findStamps(W, val));
    }

}