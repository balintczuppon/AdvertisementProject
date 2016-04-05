package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.toolz.Authorizator;
import javax.ejb.EJBException;

public class LoginController {

    private final LogInView view;
    private Encryptor encryptor;

    public LoginController(LogInView loginView) {
        this.view = loginView;
    }

    public void authentication(String user, String password) throws Exception {
        Advertiser current_advertiser = null;
        try {
            current_advertiser = (Advertiser) view.getAdvertiserFacade().getAdvertiserByMail(user);
        } catch (EJBException e) {
            e.printStackTrace();
        }
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

    private void revealSecuredContex(Advertiser a) {
        new Authorizator(view).authorize(a);
    }
}
