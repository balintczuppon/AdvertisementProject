
package com.mycompany.advertisementproject.UIs.Views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@CDIView("USERPAGE")
public class AccountView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }

}
