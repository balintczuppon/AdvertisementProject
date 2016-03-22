package com.mycompany.advertisementproject.toolz;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.Views.ADMINPAGE;
import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.view.layouts.AppLayout;
import com.mycompany.advertisementproject.view.vaadinviews.AccountView;
import com.mycompany.advertisementproject.view.vaadinviews.AdminView;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertRegView;
import com.mycompany.advertisementproject.view.vaadinviews.LetterView;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.vaadin.server.VaadinSession;

public class Authorizator {

    private LogInView view;

    public Authorizator(LogInView loginView) {
        this.view = loginView;
    }

    public void authorize(Advertiser a) {
        setCurrentUser(a);
        maintainAvailability(a);
    }

    private void setCurrentUser(Advertiser a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(CURRENTUSER.toString(), a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    private void maintainAvailability(Advertiser a) {
        switch (a.getAuthority()) {
            case 1: {
                userSettings();
                break;
            }
            case 2: {
                adminSettings();
                break;
            }
        }
    }

    private void userSettings() {
        AccountView.setAvailability(true);
        AdvertRegView.setAvailability(true);
        LetterView.setAvailability(true);
        AdminView.setAvailability(false);
        AppLayout.userLogin();
        view.getUI().getNavigator().navigateTo(USERPAGE.toString());
    }

    private void adminSettings() {
        AccountView.setAvailability(true);
        AdvertRegView.setAvailability(true);
        LetterView.setAvailability(true);
        AdminView.setAvailability(true);
        AppLayout.adminLogin();
        view.getUI().getNavigator().navigateTo(ADMINPAGE.toString());
    }

}
