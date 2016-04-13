package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.enumz.StyleNames;
import com.mycompany.advertisementproject.control.LoginController;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    private ResourceBundle bundle;

    private PasswordField pfPassWord;
    private TextField tfEmail;
    private Button btnLogin;
    private Label lblTitle;

    private LoginController logincontroller;

    private FormLayout formLayout;

    @Inject
    private AdvertiserFacade advertiserFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initContent() {
        bundle = AppBundle.currentBundle();
        defaultSettings();
        addTitle();
        addForm();
        addButton();
        updateStrings();
    }

    private void defaultSettings() {
        logincontroller = new LoginController(this);
        logincontroller.setAdvertiserFacade(advertiserFacade);
        setMargin(true);
        setImmediate(true);
    }

    private void addTitle() {
        lblTitle = new Label();
        lblTitle.setSizeUndefined();
        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
        lblTitle.setStyleName(StyleNames.TITLE.toString());
    }

    private void addForm() {
        formLayout = new FormLayout();
        tfEmail = new TextField();
        pfPassWord = new PasswordField();
        tfEmail.setValue("admin@vaadinthesis.java");
        pfPassWord.setValue("admin");
        formLayout.addComponents(tfEmail, pfPassWord);
        formLayout.setWidthUndefined();
        addComponent(formLayout);
        setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnLogin = new Button();
        btnLogin.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                String user = tfEmail.getValue();
                String password = pfPassWord.getValue();

                try {
                    logincontroller.authentication(user, password);
                } catch (Exception e) {
                    Notification.show(e.getMessage());
                    tfEmail.setComponentError(new UserError(errorText()));
                    pfPassWord.setComponentError(new UserError(errorText()));
                } finally {
                    tfEmail.clear();
                    pfPassWord.clear();
                }
            }
        });
        addComponent(btnLogin);
        setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
    }

    public String errorText() {
        return bundle.getString("LoginError");
    }

    public void updateStrings() {
        lblTitle.setValue(bundle.getString("Login"));
        tfEmail.setCaption(bundle.getString("TfEmail"));
        pfPassWord.setCaption(bundle.getString("PfPassword"));
        btnLogin.setCaption(bundle.getString("Login"));
    }
}
