
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Layouts.AdvertListLayout;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View{

    final AdvertListLayout adverLayout;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       Notification.show("Under Developement.");
    }
    
    public AdvertListView(){
        this.setSizeFull();
        adverLayout = new AdvertListLayout();
        VerticalLayout vl = adverLayout.buildView();
        addComponent(vl);
        this.setComponentAlignment(vl, Alignment.TOP_CENTER);
    }
    
}
