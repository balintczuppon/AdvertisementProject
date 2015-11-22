package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.control.RegistrationController;
import com.mycompany.advertisementproject.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.facades.PostboxFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("REGISTRATION")
public class RegistrationView extends VerticalLayout implements View {

    private RegistrationController controller;

    @Inject
    private AdvertiserFacade advertiserFacade;
    @Inject
    private PostboxFacade postboxFacade;

    private String eMailText = "E-mail cím";
    private String passWordText1 = "Jelszó";
    private String passWordText2 = "Jelszó újra";
    private String regButtonText = "Regisztrálok";

    private Label lblTitle;
    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;
    private TextField tfEmail;
    private TextField tfName;
    private TextField tfPhoneNumber;
    private CheckBox chkBxTerms;
    private CheckBox chkBxNewsLetter;
    private Button btnRegistration;

    private FormLayout fl;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        buildView();
    }

    public void buildView() {
        setMargin(true);
        addTitle();
        addForm();
        addButton();
    }

    private void addTitle() {
        lblTitle = new Label("Fiók létrehozása");
        
        lblTitle.setStyleName("title");
        lblTitle.setSizeUndefined();
        
        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
    }

    private void addForm() {
        fl = new FormLayout();
        fl.setWidthUndefined();
        
        tfEmail = new TextField(eMailText);
        tfName = new TextField("Név");
        tfPhoneNumber = new TextField("Telefonszám");
        pfPassWord1 = new PasswordField(passWordText1);
        pfPassWord2 = new PasswordField(passWordText2);
        chkBxNewsLetter = new CheckBox("Kérek hírlevelet");
        chkBxTerms = new CheckBox("Szerződési feltételek elfogadása.");
        
        fl.addComponent(tfEmail);
        fl.addComponent(pfPassWord1);
        fl.addComponent(pfPassWord2);
        fl.addComponent(tfName);
        fl.addComponent(tfPhoneNumber);
        fl.addComponent(chkBxNewsLetter);
        fl.addComponent(chkBxTerms);
        
        addComponent(fl);
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnRegistration = new Button(regButtonText);
        addComponent(btnRegistration);
        setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);

        controller = new RegistrationController(this);
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.registration();
                } catch (Exception ex) {
                    Notification.show(ex.getMessage());
                }
            }
        });
    }
    
    public PasswordField getPfPassWord1() {
        return pfPassWord1;
    }

    public PasswordField getPfPassWord2() {
        return pfPassWord2;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }

    public CheckBox getChkBxTerms() {
        return chkBxTerms;
    }

    public AdvertiserFacade getAdvertiserFacade() {
        return advertiserFacade;
    }

    public PostboxFacade getPostboxFacade() {
        return postboxFacade;
    }

    public TextField getTfName() {
        return tfName;
    }

    public TextField getTfPhoneNumber() {
        return tfPhoneNumber;
    }

    public CheckBox getChkBxNewsLetter() {
        return chkBxNewsLetter;
    }
}
