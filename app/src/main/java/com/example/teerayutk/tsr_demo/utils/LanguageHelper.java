package com.example.teerayutk.tsr_demo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * Created by teera-s on 11/10/2016 AD.
 */

public class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {
       // Configuration config;
        Locale local;
        //config = new Configuration();
        switch (locale) {
            case "en":
                local = Locale.ENGLISH;
                break;
            case "th":
                local = new Locale("th");
                break;
            case "th_TH":
                local = new Locale("th_TH");
                break;
            default:
                local = new Locale("ไทย");
                break;
        }
        Locale.setDefault(local);
        Configuration config = new Configuration();
        config.locale = local;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static void loadLocale(Resources res) {
        try {
            Locale local = new Locale(MyApplication.getInstance().getPrefManager().getLanguage());
            Locale.setDefault(local);
            Configuration config = new Configuration();
            config.locale = local;
            res.updateConfiguration(config, res.getDisplayMetrics());
        } catch (Exception ex) {
            Log.e(LanguageHelper.class.getSimpleName(), "Get settings : " + ex.toString());
        }
    }
}
