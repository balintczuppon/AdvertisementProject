package com.mycompany.advertisementproject.Enums.control;

import static com.mycompany.advertisementproject.Enums.Views.LOGIN;
import com.mycompany.advertisementproject.Tools.Encryptor;
import com.mycompany.advertisementproject.UIs.Views.RegistrationView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Postbox;
import com.vaadin.ui.Notification;
import javax.ejb.EJBException;

public class RegistrationController {

    private final int DEFAULT_AUTHORITY;

    private RegistrationView regView;

    private String name;
    private String email;
    private String password1;
    private String password2;
    private String phoneNumber;
    private boolean newsLetter;
    private boolean isAccepted;

    public RegistrationController(RegistrationView registrationView) {
        this.regView = registrationView;
        DEFAULT_AUTHORITY = 1;
    }

    public void registration() throws Exception {
        
        initialize();
        
        Advertiser advertiser = null;
        try {
            advertiser = (Advertiser) regView.getAdvertiserFacade().getAdvertiserByMail(email);
        } catch (EJBException ejbex) {
            ejbex.printStackTrace();
        }

        if (advertiser != null) {
            throw new Exception("Az email cím már használatban van.");
        }
        if(!emailIsValid()){
            throw new Exception("Nem megfelelő email cím formátum");
        }
        if (email.isEmpty() || password1.isEmpty() || password2.isEmpty() ||
                name.isEmpty() || phoneNumber.isEmpty()) {
            throw new Exception("Minden mező kitöltése kötelező");
        }
        if (!password1.equals(password2)) {
            throw new Exception("A megadott jelszavak nem egyeznek");
        }
        if (!isAccepted) {
            throw new Exception("A feltételek elfogadása nélkül nem regisztrálhat.");
        }
        registerNewUser();
    }

    private void registerNewUser() throws Exception {
        Encryptor encryptor = new Encryptor();

        try {
            Postbox postbox = new Postbox();
            Advertiser advertiser = new Advertiser();

            advertiser.setEmail(email.trim());
            advertiser.setPassword(encryptor.hashPassword(password1.trim()));
            advertiser.setName(name.trim());
            advertiser.setPhonenumber(phoneNumber.trim());
            advertiser.setNewsletter(newsLetter);
            advertiser.setAuthority(DEFAULT_AUTHORITY);

            regView.getAdvertiserFacade().create(advertiser);

            int id = advertiser.getId();
            postbox.setId(id);

            regView.getPostboxFacade().create(postbox);

            goForward();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private void goForward() {
        Notification.show("Sikeres regisztráció");
        regView.getUI().getNavigator().navigateTo(LOGIN.toString());
    }

    private void initialize() {
        email = regView.getTfEmail().getValue();
        password1 = regView.getPfPassWord1().getValue();
        password2 = regView.getPfPassWord2().getValue();
        name = regView.getTfName().getValue();
        phoneNumber = regView.getTfPhoneNumber().getValue();
        newsLetter = regView.getChkBxNewsLetter().getValue();
        isAccepted = regView.getChkBxTerms().getValue();
    }

    private boolean emailIsValid() {
        return true;
    }
}
