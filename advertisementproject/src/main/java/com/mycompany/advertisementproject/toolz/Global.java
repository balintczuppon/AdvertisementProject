package com.mycompany.advertisementproject.toolz;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class Global {

    public static final int DEFAULT_AUTHORITY = 1;
    
    public static final int GBP = 400;

    public static final Locale Locale_HU = new Locale("hu", "HU");
    public static final Locale Locale_EN = new Locale("en", "GB");

    public static final String DateFormat_HU = "yyyy.MM.dd";
    public static final String DateFormat_EN = "dd.MM.yyyy";

    public static final String TEMP_SERVER = "D:\\TempServer\\";

    public static SimpleDateFormat DATEFORMAT;
    public static NumberFormat CURRENCY;

    public static final String APP_BANNER_HEIGHT = "120";

    public static final java.sql.Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    public static String generatedId() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    public static void setDATEFORMAT(SimpleDateFormat DATEFORMAT) {
        Global.DATEFORMAT = DATEFORMAT;
    }

    public static void setCURRENCY(NumberFormat CURRENCY) {
        Global.CURRENCY = CURRENCY;
    }

}
