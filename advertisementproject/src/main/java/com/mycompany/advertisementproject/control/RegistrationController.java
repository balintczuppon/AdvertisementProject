package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.RegistrationView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Postbox;
import com.vaadin.data.validator.EmailValidator;

public class RegistrationController {

    private final int DEFAULT_AUTHORITY;

    private final RegistrationView view;
    private Advertiser advertiserToCheck;

    public RegistrationController(RegistrationView registrationView) {
        this.view = registrationView;
        DEFAULT_AUTHORITY = 1;
    }

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
            advertiser.setAuthority(DEFAULT_AUTHORITY);

            view.getAdvertiserFacade().create(advertiser);
            int id = advertiser.getId();
            postbox.setId(id);

            view.getPostboxFacade().create(postbox);
            view.goForward();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void checkUser() throws Exception {
        advertiserToCheck = (Advertiser) view.getAdvertiserFacade().getAdvertiserByMail(view.getTfEmail().getValue());
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
}
