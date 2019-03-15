package com.kakaopay.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void convertStringToDoubleTest() {
        double[] test1 = Utils.convertRateStringToDouble("3.01% ~ 4.523%");
        double[] test2 = Utils.convertRateStringToDouble("3.00% ~ 4.5%");
        double[] test3 = Utils.convertRateStringToDouble("3.00");

        assert test1.length == 2;
        assert test1[0] == 3.01;
        assert test1[1] == 4.523;

        assert test2.length == 2;
        assert test2[0] == 3.00;
        assert test2[1] == 4.5;

        assert test3.length == 1;
        assert test3[0] == 3.00;
    }


    @Test
    public void getAverageRateTest() {
        double test1 = Utils.getAverageRate(new double[]{3.5, 4.50});
        assert test1 == 4.0;

        double test2 = Utils.getAverageRate(new double[]{3.595});
        assert test2 == 3.595;

        double test3 = Utils.getAverageRate(new double[]{36.54, 452.33, 123.1});
        assert test3 == 203.99;
    }

    @Test
    public void convertCurrencyStringToLong() {
        long test1 = Utils.convertCurrencyHangulToLong("8억원");
        long test2 = Utils.convertCurrencyHangulToLong("300억원");
        long test3 = Utils.convertCurrencyHangulToLong("10억원");
        long test4 = Utils.convertCurrencyHangulToLong("11억원");
        long test5 = Utils.convertCurrencyHangulToLong("4억원");
        long test6 = Utils.convertCurrencyHangulToLong("2억원");
        long test7 = Utils.convertCurrencyHangulToLong("30백만원");

        Assert.assertEquals(800000000L, test1);
        Assert.assertEquals(30000000000L, test2);
        Assert.assertEquals(1000000000L, test3);
        Assert.assertEquals(1100000000L, test4);
        Assert.assertEquals(400000000L, test5);
        Assert.assertEquals(200000000L, test6);
        Assert.assertEquals(30000000L, test7);
    }
}

//    @Test
//    public void java8MapTest() {
//        List<RecommendMunicipality> test = new ArrayList<>();
//        test.add(new RecommendMunicipality("제주시", 300000000, 4.0));
//        test.add(new RecommendMunicipality("강원도", 400000000, 5.0));
//    }

//        Stream<Integer> dd = test.stream().map(x -> {
//                    int a = 2;
//                }

//           Tuple

//           return new Integer(1);
//       });

//               test.stream().
//               map(x -> {
//           return 1;
////            return new HashMap<String, Integer<>();
//        });

//       dd.forEach(System.out::println);
//    }
//}