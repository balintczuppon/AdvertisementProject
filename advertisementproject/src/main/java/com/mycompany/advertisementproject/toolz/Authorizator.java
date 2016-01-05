/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.toolz;

import com.vaadin.server.VaadinSession;

/**
 *
 * @author balin
 */
public class Authorizator {

    private int currentLevel;

    public boolean isAuthorized(int requieredLevel) {
        getCurrentLevel();
        boolean value = false;
        switch (requieredLevel) {
            case 0: {
                if (currentLevel >= 0) {
                    value = true;
                }
                break;
            }
            case 1: {
                if (currentLevel >= 1) {
                    value = true;
                }
                break;
            }
            case 2: {
                if (currentLevel >= 2) {
                    value = true;
                }
                break;
            }
            default: {
                value = false;
                break;
            }
        }
        return value;
    }

    private int getCurrentLevel() {
        try {
            currentLevel = (int) VaadinSession.getCurrent().getAttribute("authorization_level");
            return currentLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
