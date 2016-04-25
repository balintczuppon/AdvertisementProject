package com.mycompany.advertisementproject.toolz;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I18Helper {

    private final ResourceBundle resourceBundle;

    public I18Helper(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String string) {
        try {
            return resourceBundle.getString(string);
        } catch (MissingResourceException e) {
            Logger.getLogger(I18Helper.class.getName()).log(Level.SEVERE, null, e);
            return string;
        }
    }
}
