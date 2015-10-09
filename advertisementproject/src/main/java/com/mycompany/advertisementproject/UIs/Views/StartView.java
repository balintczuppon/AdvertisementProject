package com.mycompany.advertisementproject.UIs.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.mycompany.advertisementproject.Layouts.StartLayout;
import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@CDIView("START")
public class StartView extends VerticalLayout implements View {

    private StartLayout startLayout = new StartLayout();

    
    public StartView() {
        HorizontalLayout hl = startLayout.buildView();
        addComponent(hl);
        setComponentAlignment(hl, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Hi, This is StartView!");
    }
}
