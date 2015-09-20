package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.LoginLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class LogInView extends VerticalLayout implements View {

    LoginLayout LoginLayout;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }
    
    public LogInView(){
        LoginLayout = new LoginLayout();
        this.setSizeFull();
        addComponent(LoginLayout);
        this.setComponentAlignment(LoginLayout, Alignment.TOP_CENTER);
    }

}
