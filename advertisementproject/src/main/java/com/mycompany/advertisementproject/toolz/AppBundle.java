package com.mycompany.advertisementproject.toolz;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTLOCALE;
import com.vaadin.server.VaadinSession;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppBundle {
    
    private static Locale currentLocale;

    public static ResourceBundle currentBundle() {
        currentLocale = (Locale) VaadinSession.getCurrent().getAttribute(CURRENTLOCALE.toString());
        if (currentLocale == null) {
            currentLocale = Locale.getDefault();
            setDefaultLocale();
        }
        switch (currentLocale.getLanguage()) {
            case "hu": {
                getHunSettings();
                return ResourceBundle.getBundle("ApplicationResources_hu_HU", Global.Locale_HU);
            }
            default: {
                getEngSettings();
                return ResourceBundle.getBundle("ApplicationResources_en_GB", Global.Locale_EN);
            }
        }
    }

    private static void getHunSettings() {
        Global.setCURRENCY(NumberFormat.getCurrencyInstance(Global.Locale_HU));
        Global.setDATEFORMAT(new SimpleDateFormat(Global.DateFormat_HU, Global.Locale_HU));
    }

    private static void getEngSettings() {
        Global.setCURRENCY(NumberFormat.getCurrencyInstance(Global.Locale_EN));
        Global.setDATEFORMAT(new SimpleDateFormat(Global.DateFormat_EN, Global.Locale_EN));
    }

    private static void setDefaultLocale() {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(CURRENTLOCALE.toString(), (Locale) Global.Locale_HU);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }
}
