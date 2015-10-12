package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.AdvertListLayout;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.facades.MaincategoryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

    private AdvertListLayout adverLayout;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
    }

    @PostConstruct
    public void initComponent() {
        this.setSizeFull();
        adverLayout = new AdvertListLayout();
        VerticalLayout vl = adverLayout.buildView();
        addComponent(vl);
        this.setComponentAlignment(vl, Alignment.TOP_CENTER);
    }

}
