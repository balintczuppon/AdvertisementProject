package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.Encryptor;
import com.mycompany.advertisementproject.view.vaadinviews.RegistrationView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.toolz.EmailVerificator;
import com.mycompany.advertisementproject.toolz.Global;
import com.vaadin.data.validator.EmailValidator;
import java.io.Serializable;
import javax.inject.Inject;
import de.steinwedel.messagebox.MessageBox;

/**
 *
 * @author Czuppon Balint Peter
 */
public class RegistrationController implements Serializable {

    @Inject
    private AdvertiserFacade advertiserFacade;

    private RegistrationView view;

    private String verificationID;

    public void registration() throws Exception {
        checkDetails();
        registerNewUser();
    }

    private void registerNewUser() throws Exception {
        Encryptor encryptor = new Encryptor();
        verificationID = Global.generatedId();

        Advertiser advertiser = new Advertiser();
        advertiser.setEmail(view.getTfEmail().getValue().trim());
        advertiser.setPassword(encryptor.hashPassword(view.getPfPassWord1().getValue().trim()));
        advertiser.setSurname(view.getTfSurName().getValue().trim());
        advertiser.setFirstname(view.getTfFirstName().getValue().trim());
        advertiser.setPhonenumber(view.getTfPhoneNumber().getValue().trim());
        advertiser.setAuthority(Global.DEFAULT_AUTHORITY);
        advertiser.setVerificationID(verificationID);
        advertiser.setIsVerificated(false);
        advertiserFacade.create(advertiser);

        verification();
    }

    private void checkUserExists() throws Exception {
        long number = advertiserFacade.countUsers(view.getTfEmail().getValue());
        if (number != 0) {
            throw new Exception(view.emailUsedError());
        }
    }

    private void checkFieldsFilled() throws Exception {
        if (view.getTfEmail().isEmpty()
                || view.getPfPassWord1().isEmpty()
                || view.getPfPassWord2().isEmpty()
                || view.getTfSurName().isEmpty()
                || view.getTfFirstName().isEmpty()) {
            throw new Exception(view.emptyFieldError());
        }
    }

    private void checkPasswordLenght(String pass) throws Exception {
        if (pass.length() < Global.MIN_PASS_LENGHT) {
            throw new Exception(view.passwordWeak());
        }
    }

    private void checkPasswordsEquals(String pass1, String pass2) throws Exception {
        if (!pass1.equals(pass2)) {
            throw new Exception(view.passwordError());
        }
    }

    private void validateEmail(String value) {
        EmailValidator validator = new EmailValidator(view.emailValidatorMessage());
        validator.validate(value);
    }

    private void checkPhoneNumber(String phoneNumber) throws Exception {
        if (!phoneNumber.isEmpty()) {
            if (!phoneNumber.matches(Global.PHONE_REGEX)) {
                throw new Exception(view.phoneNumberError());
            }
        }
    }

    private void checkDetails() throws Exception {
        checkUserExists();
        validateEmail(view.getTfEmail().getValue());
        checkFieldsFilled();
        checkPasswordLenght(view.getPfPassWord1().getValue());
        checkPasswordsEquals(view.getPfPassWord1().getValue(), view.getPfPassWord2().getValue());
        checkPhoneNumber(view.getTfPhoneNumber().getValue().trim());
    }

    public void setView(RegistrationView view) {
        this.view = view;
    }

    private void verification() {
        new EmailVerificator().sendVerification(verificationID, view.getTfEmail().getValue().trim());
        MessageBox.createInfo().withCaption(view.getVerification()).withMessage(view.getVerificationWarning()).withOkButton().open();
        view.goForward();
    }

}
