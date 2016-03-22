package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.enumz.StyleNames;
import com.mycompany.advertisementproject.control.LoginController;
import com.mycompany.advertisementproject.toolz.XmlFileReader;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    private String errorText;

    private PasswordField pfPassWord;
    private TextField tfEmail;
    private Button btnLogin;
    private Label lblTitle;

    private LoginController logincontroller;

    private XmlFileReader xmlReader;

    private FormLayout fl;

    @Inject
    private AdvertiserFacade advertiserFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initContent() {
            setMargin(true);
            addTitle();
            addForm();
            addButton();
            addLabelText();
    }

    private void addTitle() {
        lblTitle = new Label();
        lblTitle.setSizeUndefined();
        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
        lblTitle.setStyleName(StyleNames.TITLE.toString());
    }

    private void addForm() {
        fl = new FormLayout();
        tfEmail = new TextField();
        tfEmail.setValue("admin@vaadinthesis.java");
        pfPassWord = new PasswordField();
        pfPassWord.setValue("admin");
        fl.addComponent(tfEmail);
        fl.addComponent(pfPassWord);
        fl.setWidthUndefined();
        addComponent(fl);
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        logincontroller = new LoginController(this);
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
                    tfEmail.setComponentError(new UserError(errorText));
                    pfPassWord.setComponentError(new UserError(errorText));
                } finally {
                    tfEmail.clear();
                    pfPassWord.clear();
                }
            }
        });
        addComponent(btnLogin);
        setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
    }

    private void addLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setLoginView(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
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

    public Button getBtnLogin() {
        return btnLogin;
    }

    public Label getLblTitle() {
        return lblTitle;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    public AdvertiserFacade getAdvertiserFacade() {
        return advertiserFacade;
    }
}
