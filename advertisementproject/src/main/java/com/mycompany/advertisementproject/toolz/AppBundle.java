package com.mycompany.advertisementproject.toolz;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTLOCALE;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppBundle {

    private static final Locale HUNGARIAN = new Locale("hu");

    private static String currentLocale;

    public static ResourceBundle currentBundle(String value) {
        currentLocale = (String) VaadinSession.getCurrent().getAttribute(CURRENTLOCALE.toString());
        if (currentLocale != null) {
            switch (currentLocale) {
                case "hu": {
                    return ResourceBundle.getBundle("ApplicationResources_hu_HU", HUNGARIAN);
                }
                default: {
                    return ResourceBundle.getBundle("ApplicationResources_en_GB", Locale.ENGLISH);
                }
            }
        } else {
            try {
                VaadinSession.getCurrent().getLockInstance().lock();
                VaadinSession.getCurrent().setAttribute(CURRENTLOCALE.toString(), "en");
            } finally {
                VaadinSession.getCurrent().getLockInstance().unlock();
            }
            return ResourceBundle.getBundle("ApplicationResources_en_GB", Locale.ENGLISH);
        }
    }
}
