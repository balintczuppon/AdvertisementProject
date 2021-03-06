
package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Czuppon Balint Peter
 */
@CDIView("ADVERTMOD")
public class AdvertModView extends AdvertRegView {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        try {
            controller.linkDataToFields();
        } catch (Exception ex) {
            Logger.getLogger(AdvertModView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addRegButtonListener() {
        btnRegister.setCaption(modify);
        btnRegister.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                txtFldPrice.setComponentError(null);
                txtFldCordX.setComponentError(null);
                txtFldCordY.setComponentError(null);
                try {
                    controller.modifyAdvert();
                    Notification.show(successModification);
                    getUI().getNavigator().navigateTo(USERPAGE.toString());
                } catch (NumberFormatException e) {
                    Notification.show(numberFormatError);
                    Logger.getLogger(AdvertModView.class.getName()).log(Level.SEVERE, null, e);
                } catch (Exception e) {
                    Notification.show(failedModification);
                    Logger.getLogger(AdvertModView.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }
}
