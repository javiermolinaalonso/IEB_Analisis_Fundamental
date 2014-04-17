package info.invertirenbolsa.fundamentales.price.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class FactoryStatisticList {

    public static StatisticList getReversedList(List<BigDecimal> origin) {
        StatisticList list = new StatisticList(origin);
        Collections.reverse(list);
        return list;
    }
    public static StatisticList getRandomList(String maximum, Integer size) {
        StatisticList list = new StatisticList();
        for(int i = 0; i < size; i++){
            list.add(generateRandomNumber(maximum));
        }
        return list;
    }

    private static BigDecimal generateRandomNumber(String maximum) {
        BigDecimal max = new BigDecimal(maximum);
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        return randFromDouble.divide(max, BigDecimal.ROUND_DOWN);
    }
}
