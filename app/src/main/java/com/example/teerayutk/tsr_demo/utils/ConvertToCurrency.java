package com.example.teerayutk.tsr_demo.utils;

import java.text.DecimalFormat;

/**
 * Created by teera-s on 10/7/2016 AD.
 */

public class ConvertToCurrency {

    public static String Currency(String str) {
        String strEwallet = null;
        DecimalFormat myFormatter;

        try {
            myFormatter = new DecimalFormat("#,##0");
            Number num = myFormatter.parse(str);
            strEwallet = myFormatter.format(num.doubleValue());
        } catch (Exception e) {
        }
        return strEwallet;
    }
}
