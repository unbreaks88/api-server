package com.kakaopay.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    private static final Map<String, Integer> hangulToLongCurrencyMap = new HashMap<>();

    static {
        hangulToLongCurrencyMap.put("백만원", 1000000);
        hangulToLongCurrencyMap.put("천만원", 10000000);
        hangulToLongCurrencyMap.put("억원", 100000000);
    }

    public static double[] convertRateStringToDouble(final String rate) {
        if (rate.trim().endsWith("전액")) {
            return new double[]{100.00};
        }
        List<String> rateList = Arrays.stream(rate.split("~")).collect(Collectors.toList());
        double[] convertedRate;
        double minRate = Double.valueOf(rateList.get(0).trim().replace("%", ""));

        if (rateList.size() == 2) {
            double maxRate = Double.valueOf(rateList.get(1).trim().replace("%", ""));
            convertedRate = new double[]{minRate, maxRate};
        } else {
            convertedRate = new double[]{minRate};
        }
        Arrays.sort(convertedRate);
        return convertedRate;
    }

    public static double getAverageRate(final double[] rate) {
        double averageRate;
        if (rate.length == 1) {
            averageRate = rate[0];
        } else {
            double sum = Arrays.stream(rate).sum();
            averageRate = (sum / rate.length);
        }
        return averageRate;
    }

    public static long convertCurrencyHangulToLong(final String hangulKoreanWon) {
        // FIXME
        if (hangulKoreanWon.startsWith("추천금액")) {
            return Long.MIN_VALUE;
        }
        int number = 0;
        int unit = 0;

        for (String key : hangulToLongCurrencyMap.keySet()) {
            int index = hangulKoreanWon.indexOf(key);
            if (index != -1) {
                number = Integer.valueOf(hangulKoreanWon.substring(0, index));
                unit = hangulToLongCurrencyMap.get(key);
            }
        }
        return (long) number * unit;
    }

//    private static boolean isKoreanWon(String hangulKoreanWon) {
//        for (String key : hangulToLongCurrencyMap.keySet()) {
//        }
//        return false;
//    }
}
