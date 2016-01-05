package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.AUTHORIZATIONLEVEL;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.mycompany.advertisementproject.view.layouts.AppLayout;
import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.vaadin.server.VaadinSession;
import javax.ejb.EJBException;

public class LoginController {

    private LogInView view;
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
                throw new Exception(view.getErrorText());
            }
        } else {
            throw new Exception(view.getErrorText());
        }
    }

    private void revealSecuredContex(Advertiser a) {
        setCurrentUser(a);
        setUserRole(a);
        
        AppLayout.login();
        view.getUI().getNavigator().navigateTo(USERPAGE.toString());
    }

    private void setCurrentUser(Advertiser a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(CURRENTUSER.toString(), a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    private void setUserRole(Advertiser a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(AUTHORIZATIONLEVEL.toString(), a.getAuthority());
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }
}
