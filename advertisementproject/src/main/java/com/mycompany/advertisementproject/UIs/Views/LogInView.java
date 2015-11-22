package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.StyleNames;
import com.mycompany.advertisementproject.Enums.control.LoginController;
import com.mycompany.advertisementproject.Tools.XmlFileReader;
import com.mycompany.advertisementproject.facades.AdvertiserFacade;
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
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    private LoginController logincontroller;
    private XmlFileReader xmlReader;

    @Inject
    private AdvertiserFacade advertiserFacade;

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
        pfPassWord = new PasswordField();
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
                    tfEmail.setComponentError(new UserError("Hibás felhasználónév vagy jelszó!"));
                    pfPassWord.setComponentError(new UserError("Hibás felhasználónév vagy jelszó!"));
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
        xmlReader = new XmlFileReader();
        xmlReader.setLoginView(this);
        xmlReader.setTagName(this.getClass().getSimpleName());
        xmlReader.readXml();
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

    public AdvertiserFacade getAdvertiserFacade() {
        return advertiserFacade;
    }
}
