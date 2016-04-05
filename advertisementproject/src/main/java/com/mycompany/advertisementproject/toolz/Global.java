package com.mycompany.advertisementproject.toolz;

import java.text.SimpleDateFormat;

public class Global {

    public static final String DATEFORMAT = "yyyy MMMM dd";
    public static final String CURRENCY = "HUF";

    public static final String APP_BANNER_HEIGHT = "120";

    public static final java.sql.Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
}
