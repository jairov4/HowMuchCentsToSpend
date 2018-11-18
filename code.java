import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Main {

    static long findHowMuchCentsToSpend(int n, int L, long[] c) {
        int filled = 0;
        long totalCost = 0;
        Integer[] r = new Integer[c.length];
        for (int i = 0; i < c.length; i++) r[i] = i;
        Arrays.sort(r, (x, y) -> BottleComparer(c, x, y));
        long bestOverage = Long.MAX_VALUE;
        for (int i = 0; i < c.length && filled < L; i++) {
            int j = r[i];
            int liters = 1 << j;
            int bottles = (L - filled) / liters;
            filled += bottles * liters;
            totalCost += bottles * c[j];
            bestOverage = Long.min(bestOverage, totalCost + c[j]);
            //System.out.println("Take " + bottles + " bottles of " + liters + "L for $" + c[j] +
            //        ", total " + totalCost + " filled " + filled + "L, bestOverage " + bestOverage);
        }

        return Long.min(totalCost, bestOverage);
    }

    private static int BottleComparer(long[] c, int x, int y) {
        Function<Integer, Float> costPerLiter = z -> (float)c[z] / (1 << z);
        float c1 = costPerLiter.apply(x); float c2 = costPerLiter.apply(y);
        // it is too weird but I choose higher granularity for completeness (just in case)
        if(c1 == c2) return x < y ? -1 : 1;
        return c1 < c2 ? -1 : 1;
    }

    private static void Assert(long expected, long value)
    {
        if(expected != value) System.out.println("ERROR: Expected was " + expected + " but found " + value);
        else System.out.println("PASSED!");
    }

    /**
     * DO NOT MODIFY THIS METHOD!
     */
    public static void main(String[] args) throws IOException {
        Assert(10 ,findHowMuchCentsToSpend(4, 3, new long[] { 10000, 1000, 100, 10 }));
        Assert(30, findHowMuchCentsToSpend(4, 3, new long[]{ 10, 100, 1000, 10000}));
        Assert(120, findHowMuchCentsToSpend(4, 12, new long[] { 10, 100, 1000, 10000 }));
        Assert(150, findHowMuchCentsToSpend(4, 12, new long[]{ 20, 30, 70, 90 }));
        Assert(170, findHowMuchCentsToSpend(4, 13, new long[] { 26, 30, 70, 85 }));
        Assert(18, findHowMuchCentsToSpend(2, 3, new long[]{10, 9}));
        Assert(44981600785557577L, findHowMuchCentsToSpend(5, 787787787, new long[] { 123456789, 234567890, 345678901, 456789012 , 987654321 }));
    }
}

     
