package com.mycompany.advertisementproject.Enums.control;

import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.Tools.Encryptor;
import com.mycompany.advertisementproject.UIs.Views.LogInView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.vaadin.server.VaadinSession;
import javax.ejb.EJBException;

public class LoginController {

    private LogInView loginView;
    private Encryptor encryptor;

    public LoginController(LogInView loginView) {
        this.loginView = loginView;
    }

    public void authentication(String user, String password) throws Exception {
        Advertiser current_advertiser = null;
        try {
            current_advertiser = (Advertiser) loginView.getAdvertiserFacade().getAdvertiserByMail(user);
        } catch (EJBException e) {
            e.printStackTrace();
        }
        if (current_advertiser != null) {
            encryptor = new Encryptor();
            if (current_advertiser.getPassword().equals(encryptor.hashPassword(password))) {
//            if (current_advertiser.getPassword().equals(password)) {
                revealSecuredContex(current_advertiser);
            } else {
                throw new Exception("Hibás felhasználónév vagy jelszó");
            }
        } else {
            throw new Exception("Hibás felhasználónév vagy jelszó");
        }
    }

    private void revealSecuredContex(Advertiser a) {
        setCurrentUser(a);
        setUserRole(a);
        loginView.getUI().getNavigator().navigateTo(USERPAGE.toString());
    }

    private void setCurrentUser(Advertiser a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("current_user", a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    private void setUserRole(Advertiser a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("authorization_level", a.getAuthority());
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }
}
