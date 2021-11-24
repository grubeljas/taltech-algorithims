package ee.ttu.algoritmid.interestingstamps;
import java.util.Comparator;
import java.util.List;

public class InterestingStamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        int len = stampOptions.size();
        int max, current = 0;
        for (int a = 0; a < len; a++) {
            for (int b = 0; b < len; b++) {
                for (int c = 0; c < len; c++) {
                    for (int d = 0; d < len; d++) {
                        for (int e = 0; e < len; e++) {
                            for (int f = 0; f < len; f++) {
                                current = 0;
                                if (a * stampOptions.get(0) + b * stampOptions.get(1) + c * stampOptions.get(2) +
                                        d * stampOptions.get(3) + e * stampOptions.get(4) + f * stampOptions.get(5)
                                        == sum) {
                                    for (Integer integer: stampOptions) {
                                        if (!(integer == 1 || integer % 10 == 0)) {
                                            current += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}