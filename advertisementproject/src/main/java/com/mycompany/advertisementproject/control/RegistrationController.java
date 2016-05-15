package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.RegistrationView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Postbox;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.model.facades.PostboxFacade;
import com.mycompany.advertisementproject.toolz.Global;
import com.vaadin.data.validator.EmailValidator;
import java.io.Serializable;
import javax.inject.Inject;

public class RegistrationController implements Serializable {

    @Inject
    private AdvertiserFacade advertiserFacade;
    @Inject
    private PostboxFacade postboxFacade;

    private RegistrationView view;
    private Advertiser advertiserToCheck;

    public void registration() throws Exception {
        checkUserExists();
        checkDetails();
        registerNewUser();
    }

    private void registerNewUser() throws Exception {
        Encryptor encryptor = new Encryptor();

        try {
            Postbox postbox = new Postbox();
            Advertiser advertiser = new Advertiser();
            advertiser.setEmail(view.getTfEmail().getValue().trim());
            advertiser.setPassword(encryptor.hashPassword(view.getPfPassWord1().getValue().trim()));
            advertiser.setName(view.getTfName().getValue().trim());
            advertiser.setPhonenumber(view.getTfPhoneNumber().getValue().trim());
            advertiser.setNewsletter(view.getChkBxNewsLetter().getValue());
            advertiser.setAuthority(Global.DEFAULT_AUTHORITY);

            advertiserFacade.create(advertiser);
            int id = advertiser.getId();
            postbox.setId(id);

            postboxFacade.create(postbox);

            view.goForward();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void checkUser() throws Exception {
        advertiserToCheck = (Advertiser) advertiserFacade.getAdvertiserByMail(view.getTfEmail().getValue());
    }

    private void checkUserExists() throws Exception {
        if (advertiserToCheck != null) {
            advertiserToCheck = null;
            throw new Exception(view.emailUsedError());
        }
    }

    private void checkFieldsFilled() throws Exception {
        if (view.getTfEmail().isEmpty()
                || view.getPfPassWord1().isEmpty()
                || view.getPfPassWord2().isEmpty()
                || view.getTfName().isEmpty()
                || view.getTfPhoneNumber().isEmpty()) {
            throw new Exception(view.emptyFieldError());
        }
    }

    private void checkPasswordsEquals() throws Exception {
        if (!view.getPfPassWord1().getValue().equals(view.getPfPassWord2().getValue())) {
            throw new Exception(view.passwordError());
        }
    }

    private void checkTermsChecked() throws Exception {
        if (!view.getChkBxTerms().getValue()) {
            throw new Exception(view.conditionError());
        }
    }

    private void validateEmail(String value) {
        EmailValidator validator = new EmailValidator(view.emailValidatorMessage());
        validator.validate(value);
    }

    private void checkDetails() throws Exception {
        validateEmail(view.getTfEmail().getValue());
        checkFieldsFilled();
        checkPasswordsEquals();
        checkTermsChecked();
    }

    public void setView(RegistrationView view) {
        this.view = view;
    }

}
