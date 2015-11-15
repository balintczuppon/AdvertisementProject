package com.mycompany.advertisementproject.Enums.control;

import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.Layouts.AppLayout;
import com.mycompany.advertisementproject.UIs.Views.LogInView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private LogInView loginView;
    private String user;
    private String password;

    public LoginController(LogInView loginView) {
        this.loginView = loginView;
    }

    public void addButtonEvent(Button button) {
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                authentication();
            }
        });
    }

    private void authentication() {
        user = loginView.getTfEmail().getValue();
        password = loginView.getPfPassWord().getValue();

        try {
            for (Advertiser a : loginView.getAdvertiserFacade().findAll()) {
                if (a.getEmail().equals(user) && a.getPassword().equals(password)) {
                    revealSecuredContex(a);
                    return;
                }
            }
            Notification.show("You have failed this login.");
        } catch (Exception ex) {
            Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            clearFields();
        }
    }

    private void revealSecuredContex(Advertiser a) {
        AppLayout.login(a);
        loginView.getUI().getNavigator().navigateTo(USERPAGE.toString());
    }

    private void clearFields() {
        loginView.getTfEmail().clear();
        loginView.getPfPassWord().clear();
    }

    public void setLoginView(LogInView loginView) {
        this.loginView = loginView;
    }
}
