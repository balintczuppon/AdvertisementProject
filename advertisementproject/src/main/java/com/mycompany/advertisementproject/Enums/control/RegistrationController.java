package com.mycompany.advertisementproject.Enums.control;

import com.mycompany.advertisementproject.Tools.Encryptor;
import com.mycompany.advertisementproject.UIs.Views.RegistrationView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Postbox;

public class RegistrationController {

    private final int DEFAULT_AUTHORITY;

    private RegistrationView view;

    public RegistrationController(RegistrationView registrationView) {
        this.view = registrationView;
        DEFAULT_AUTHORITY = 1;
    }

    public void registration() throws Exception {
        
        Advertiser advertiser = (Advertiser) view.getAdvertiserFacade().getAdvertiserByMail(view.getTfEmail().getValue());

        if (advertiser != null) {
            throw new Exception(view.getEmailUsedError());
        }
        if (!emailIsValid()) {
            throw new Exception(view.getEmailFormatError());
        }
        if (view.getTfEmail().getValue().isEmpty() || view.getPfPassWord1().getValue().isEmpty() || view.getPfPassWord2().getValue().isEmpty()
                || view.getTfName().getValue().isEmpty() || view.getTfPhoneNumber().getValue().isEmpty()) {
            throw new Exception(view.getEmptyFieldError());
        }
        if (!view.getPfPassWord1().getValue().equals(view.getPfPassWord2().getValue())) {
            throw new Exception(view.getPasswordError());
        }
        if (!view.getChkBxTerms().getValue()) {
            throw new Exception(view.getConditionError());
        }
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

    private boolean emailIsValid() {
        return true;
    }
}
