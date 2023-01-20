package com.maverick.algorithms.a_start;

import lombok.extern.slf4j.Slf4j;
import java.util.*;

/**
 * @author Om Narayan Singh
 * @version 1.0
 * @project algorithms
 * @package com.maverick.algorithms
 * @since 1/19/23
 **/

@Slf4j
public class CoinChanger {
    public static void main(String[] args) {
        int amount = 5000;
        var denominator = new ArrayList<Integer>();
        denominator.add(10);
        denominator.add(25);
        denominator.add(35);
        denominator.add(75);
        denominator.add(1);
        var result = changeCoin(amount, denominator);
        log.info("Result: {}", result);
    }

    /**
     * @param amount can be set as double, but doing so may require adjustment at denominator list,
     *               for instance: denominator here only contains for nepali rupee value, but it should also
     *               include (or in a new denominator list) paisa value as well.
     * @param denominator list of coin values. For now, it only contains for rupee and not cent.
     * @return
     */
    private static HashMap<Integer, Long> changeCoin(int amount, List<Integer> denominator) {
        denominator.sort(Collections.reverseOrder());
        log.info("Given denominator:  {}", denominator);
        var coinAmountMap = new HashMap<Integer, Long>();

        long centCount;

        for (int cent : denominator) {
            centCount = (long) amount / cent;
            if (centCount != 0) {
                coinAmountMap.put(cent, centCount);
                amount = amount % cent;
            }
        }
        return coinAmountMap;
    }
}
