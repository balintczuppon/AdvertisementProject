package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.LoginLayout;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    private LoginLayout loginLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }

    @PostConstruct
    public void initContent() {
        loginLayout = new LoginLayout();
        this.setSizeFull();
        VerticalLayout vl = loginLayout.buildView();
        addComponent(vl);
        this.setComponentAlignment(vl, Alignment.TOP_CENTER);
    }

}
