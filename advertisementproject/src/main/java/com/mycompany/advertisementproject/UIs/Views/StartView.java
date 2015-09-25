package com.mycompany.advertisementproject.UIs.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.mycompany.advertisementproject.Layouts.StartLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

public class StartView extends VerticalLayout implements View {

    final StartLayout startLayout;

    public StartView() {
        startLayout = new StartLayout();
        HorizontalLayout hl = startLayout.buildView();
        addComponent(hl);
        setComponentAlignment(hl, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
