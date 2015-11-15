package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.StyleNames;
import com.mycompany.advertisementproject.Enums.control.LoginController;
import com.mycompany.advertisementproject.facades.AdvertiserFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    @Inject
    AdvertiserFacade advertiserFacade;

    private LoginController controller;

    private final String eMailText = "E-mail cím";
    private final String passWordText = "Jelszó";
    private final String regButtonText = "Bejelentkezés";

    private PasswordField pfPassWord;
    private TextField tfEmail;
    private Button btnLogin;
    private Label lblTitle;

    private FormLayout fl;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initContent() {
        addTitle();
        addForm();
        addButton();
        addEvents();
        markFields();
    }

    private void addTitle() {
        setMargin(true);
        lblTitle = new Label("Bejelentkezés");
        lblTitle.setStyleName(StyleNames.TITLE.toString());
        lblTitle.setSizeUndefined();
        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
    }

    private void addForm() {
        fl = new FormLayout();
        tfEmail = new TextField(eMailText);
        fl.addComponent(tfEmail);
        pfPassWord = new PasswordField(passWordText);
        fl.addComponent(pfPassWord);
        addComponent(fl);
        fl.setWidthUndefined();
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnLogin = new Button(regButtonText);
        addComponent(btnLogin);
        setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
    }

    private void markFields() {
        pfPassWord.setRequired(true);
        tfEmail.setRequired(true);
    }

    private void addEvents() {
        try {
            controller = new LoginController(this);
            controller.addButtonEvent(btnLogin);
        } catch (Exception ex) {
            Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PasswordField getPfPassWord() {
        return pfPassWord;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }

    public AdvertiserFacade getAdvertiserFacade() {
        return advertiserFacade;
    }
}
