package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.toolz.Authorizator;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;

public class LoginController implements Serializable {

    @Inject
    private AdvertiserFacade advertiserFacade;

    private LogInView view;
    private Encryptor encryptor;

    public void authentication(String user, String password) throws Exception {
        Advertiser current_advertiser = null;
        try {
            current_advertiser = (Advertiser) advertiserFacade.getAdvertiserByMail(user);
        } catch (EJBException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
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

    public void setView(LogInView view) {
        this.view = view;
    }

}
