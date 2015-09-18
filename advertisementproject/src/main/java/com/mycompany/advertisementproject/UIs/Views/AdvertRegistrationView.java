/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.UIs.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author balin
 */
public class AdvertRegistrationView extends VerticalLayout implements View{

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       Notification.show("Under Developement.");
    }
    
}
