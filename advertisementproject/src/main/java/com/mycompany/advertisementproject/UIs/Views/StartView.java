package com.mycompany.advertisementproject.UIs.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.mycompany.advertisementproject.Layouts.StartLayout;
import com.vaadin.ui.Alignment;

public class StartView extends VerticalLayout implements View {

    StartLayout startLayout;

    public StartView() {
        this.setSizeFull();
        startLayout = new StartLayout();
        addComponent(startLayout);
        this.setComponentAlignment(startLayout, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
