package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.RegistrationLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class RegistrationView extends VerticalLayout implements View {

    RegistrationLayout registrationLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }

    public RegistrationView() {
        this.setSizeFull();
        registrationLayout = new RegistrationLayout();
        addComponent(registrationLayout);
        this.setComponentAlignment(registrationLayout, Alignment.TOP_LEFT);
    }
}
