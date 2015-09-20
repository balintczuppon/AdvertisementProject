/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.AdverLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author balin
 */
public class AdvertView extends VerticalLayout implements View{

    
    AdverLayout adverLayout;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       Notification.show("Under Developement.");
    }
    
    public AdvertView(){
        this.setSizeFull();
        adverLayout = new AdverLayout();
        addComponent(adverLayout);
        this.setComponentAlignment(adverLayout, Alignment.TOP_CENTER);
    }
    
}
