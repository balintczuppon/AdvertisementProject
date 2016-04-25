package com.mycompany.advertisementproject.view.vaadinviews;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import java.util.logging.Logger;

@CDIView("ADVERTMOD")
public class AdvertModView extends AdvertRegView {

    @Override
    public void build() {
        try {
            super.build();
            
            controller.linkDataToFields();
            btnRegister.setCaption(modify);
            btnRegister.addClickListener(new Button.ClickListener() {
                
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        controller.modifyAdvert();
                        Notification.show(successModification);
                    } catch (Exception e) {
                        Notification.show(failedModification);
                        Logger.getLogger(AdvertModView.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AdvertModView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
