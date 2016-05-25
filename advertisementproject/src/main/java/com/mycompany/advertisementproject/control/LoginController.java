package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.toolz.Authorizator;
import com.vaadin.navigator.ViewChangeListener;
import java.io.Serializable;
import javax.inject.Inject;

public class LoginController implements Serializable {

    @Inject
    private AdvertiserFacade advertiserFacade;

    private Advertiser current_advertiser = null;

    private LogInView view;
    private Encryptor encryptor;

    private int numberOfUsers = 0;

    public void authentication(String user, String password) throws Exception {
        checkIfUserExists(user);
        if (current_advertiser != null) {
            encryptor = new Encryptor();
            if (current_advertiser.getPassword().equals(encryptor.hashPassword(password))) {
                revealSecuredContex(current_advertiser);
            } else {
                throw new Exception(view.errorText());
            }
        } else {
            throw new Exception(view.errorText());
        }
    }

    public void checkIfUserExists(String user) {
        numberOfUsers = advertiserFacade.countUsers(user);
        if (numberOfUsers == 1) {
            current_advertiser = (Advertiser) advertiserFacade.getAdvertiserByMail(user);
        }
    }

    private void revealSecuredContex(Advertiser a) {
        new Authorizator(view).authorize(a);
    }

    public void setView(LogInView view) {
        this.view = view;
    }

    public LogInView getView() {
        return view;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void getParameter(ViewChangeListener.ViewChangeEvent event) {
        String parameter = event.getParameters().split("/")[0];
        if (!parameter.isEmpty()) {
            System.out.println(parameter);
            advertiserFacade.setEmailVerifyed(parameter);
        }
    }
}
