/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.AdvertRegLayout;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author balin
 */
@CDIView("ADVERTREG")
public class AdvertRegView extends VerticalLayout implements View {

    final AdvertRegLayout advertRegLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }

    public AdvertRegView() {
        advertRegLayout = new AdvertRegLayout();
        setSizeFull();
        VerticalLayout vl = advertRegLayout.buildView();
        addComponent(vl);
        setComponentAlignment(vl, Alignment.TOP_CENTER);
    }

}
