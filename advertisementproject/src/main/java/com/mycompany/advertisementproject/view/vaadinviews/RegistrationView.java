package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.StyleNames.TITLE;
import static com.mycompany.advertisementproject.enumz.Views.LOGIN;
import com.mycompany.advertisementproject.control.RegistrationController;
import com.mycompany.advertisementproject.toolz.XmlFileReader;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.model.facades.PostboxFacade;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("REGISTRATION")
public class RegistrationView extends VerticalLayout implements View {

    private String emailUsedError;
    private String emptyFieldError;
    private String passwordError;
    private String conditionError;
    private String txtSuccess;
    private String emailValidatorMessage;

    private TextField tfEmail;
    private TextField tfName;
    private TextField tfPhoneNumber;

    private CheckBox chkBxTerms;
    private CheckBox chkBxNewsLetter;

    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;

    private Label lblTitle;

    private Button btnRegistration;

    private XmlFileReader xmlReader;

    private RegistrationController controller;

    private FormLayout fl;

    @Inject
    private AdvertiserFacade advertiserFacade;
    @Inject
    private PostboxFacade postboxFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        setMargin(true);
        addTitle();
        addForm();
        addButton();
        addLabelText();
    }

    private void addTitle() {
        lblTitle = new Label();
        lblTitle.setStyleName(TITLE.toString());
        lblTitle.setSizeUndefined();

        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
    }

    private void addForm() {
        fl = new FormLayout();
        fl.setWidthUndefined();

        createFields();
        addComponentsToContent();

        addComponent(fl);
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void createFields() {
        tfEmail = new TextField();
        tfName = new TextField();
        tfPhoneNumber = new TextField();
        pfPassWord1 = new PasswordField();
        pfPassWord2 = new PasswordField();
        chkBxNewsLetter = new CheckBox();
        chkBxTerms = new CheckBox();
    }

    private void addComponentsToContent() {
        fl.addComponent(tfEmail);
        fl.addComponent(pfPassWord1);
        fl.addComponent(pfPassWord2);
        fl.addComponent(tfName);
        fl.addComponent(tfPhoneNumber);
        fl.addComponent(chkBxNewsLetter);
        fl.addComponent(chkBxTerms);
    }

    private void addButton() {
        btnRegistration = new Button();
        addComponent(btnRegistration);
        setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);

        controller = new RegistrationController(this);
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.checkUser();
                } catch (Exception ex) {
                    Logger.getLogger(RegistrationView.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    controller.registration();
                } catch (Exception ex) {
                    Notification.show(ex.getMessage());
                }
            }
        });
    }

    public void goForward() {
        Notification.show(txtSuccess);
        getUI().getNavigator().navigateTo(LOGIN.toString());
    }

    private void addLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setRegView(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
        } catch (Exception ex) {
            Logger.getLogger(RegistrationView.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Label getLblTitle() {
        return lblTitle;
    }

    public Button getBtnRegistration() {
        return btnRegistration;
    }

    public void setTxtSuccess(String txtSuccess) {
        this.txtSuccess = txtSuccess;
    }

    public String getEmailUsedError() {
        return emailUsedError;
    }

    public void setEmailUsedError(String emailUsedError) {
        this.emailUsedError = emailUsedError;
    }

    public String getEmptyFieldError() {
        return emptyFieldError;
    }

    public void setEmptyFieldError(String emptyFieldError) {
        this.emptyFieldError = emptyFieldError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConditionError() {
        return conditionError;
    }

    public void setConditionError(String conditionError) {
        this.conditionError = conditionError;
    }

    public String getEmailValidatorMessage() {
        return emailValidatorMessage;
    }

    public void setEmailValidatorMessage(String emailValidatorMessage) {
        this.emailValidatorMessage = emailValidatorMessage;
    }

}
