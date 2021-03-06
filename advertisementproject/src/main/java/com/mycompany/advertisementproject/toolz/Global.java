package com.mycompany.advertisementproject.toolz;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTLOCALE;
import com.vaadin.server.VaadinSession;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

/**
 *
 * @author Czuppon Balint Peter
 */
public class Global {

    public static final String ENCRYPT_ALG = "SHA-256";
    public static final String PHONE_REGEX = "\\d{11}|\\d{14}";
    
    public static final String PAGE_LINK = "http://213.181.208.30:8080/advertisementproject/#!";
    public static final String USERNAME = "vaadinproject2016@gmail.com";
    public static final String PASSWORD = "vaadin2016";

    public static final int DEFAULT_AUTHORITY = 1;
    public static final int EXCHANGE_RATE = 400;
    public static final int MIN_PASS_LENGHT = 6;
    
    public static final Locale Locale_HU = new Locale("hu", "HU");
    public static final Locale Locale_EN = new Locale("en", "GB");

    public static final String DateFormat_HU = "yyyy.MM.dd";
    public static final String DateFormat_EN = "dd.MM.yyyy";

    public static final String TEMP_SERVER = "/root/pictures";

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

    public static Integer exchange_huf_to_gbp(Integer price) {
        Locale locale = (Locale) VaadinSession.getCurrent().getAttribute(CURRENTLOCALE.toString());
        if (locale == Locale_HU) {
            return price;
        } else {
            return price / EXCHANGE_RATE;
        }
    }

    public static Integer exchange_gbp_to_huf(Integer price) {
        Locale locale = (Locale) VaadinSession.getCurrent().getAttribute(CURRENTLOCALE.toString());
        if (locale == Locale_EN) {
            return price * EXCHANGE_RATE;
        } else {
            return price;
        }
    }

}
