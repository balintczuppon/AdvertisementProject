package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.Views.LOGIN;
import com.mycompany.advertisementproject.control.RegistrationController;
import com.mycompany.advertisementproject.enumz.StyleNames;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.I18Helper;
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
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("REGISTRATION")
public class RegistrationView extends VerticalLayout implements View {

    @Inject
    private RegistrationController controller;

    private TextField tfEmail;
    private TextField tfSurName;
    private TextField tfFirstName;
    private TextField tfPhoneNumber;

    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;

    private Label lblTitle;

    private Button btnRegistration;

    private FormLayout formLayout;

    private I18Helper i18Helper;

    private String verification;
    private String verificationWarning;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        defaultSettings();
        addTitle();
        addForm();
        addButton();
        updateStrings();
    }

    private void defaultSettings() {
        controller.setView(this);
        setMargin(true);
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
        formLayout.setWidthUndefined();

        createFields();
        addComponentsToContent();

        addComponent(formLayout);
        setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
    }

    private void createFields() {
        tfEmail = new TextField();
        tfSurName = new TextField();
        tfFirstName = new TextField();
        tfPhoneNumber = new TextField();
        pfPassWord1 = new PasswordField();
        pfPassWord2 = new PasswordField();
    }

    private void addComponentsToContent() {
        formLayout.addComponents(tfEmail, pfPassWord1, pfPassWord2, tfSurName, tfFirstName, tfPhoneNumber);
    }

    private void addButton() {
        btnRegistration = new Button();
        addComponent(btnRegistration);
        setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.registration();
                    clearFields();
                } catch (Exception ex) {
                    Notification.show(ex.getMessage());
                }
            }
        });
    }

    public void updateStrings() {
        lblTitle.setValue(i18Helper.getMessage("Registration"));
        tfEmail.setCaption(i18Helper.getMessage("TfEmail"));
        tfSurName.setCaption(i18Helper.getMessage("TfSurName"));
        tfFirstName.setCaption(i18Helper.getMessage("TfFirstName"));
        tfPhoneNumber.setCaption(i18Helper.getMessage("TfPhone"));
        pfPassWord1.setCaption(i18Helper.getMessage("PfPassword"));
        pfPassWord2.setCaption(i18Helper.getMessage("PfPassword"));
        btnRegistration.setCaption(i18Helper.getMessage("Registration"));
        verificationWarning = i18Helper.getMessage("VerificationWarning");
        verification = i18Helper.getMessage("Verification");
    }

    public void goForward() {
        getUI().getNavigator().navigateTo(LOGIN.toString());
    }
    
    private void clearFields(){
        tfEmail.clear();
        tfFirstName.clear();
        tfSurName.clear();
        tfPhoneNumber.clear();
        pfPassWord1.clear();
        pfPassWord2.clear();
    }

    public String emailUsedError() {
        return i18Helper.getMessage("EmailUsedError");
    }

    public String emptyFieldError() {
        return i18Helper.getMessage("EmptyFieldError");
    }

    public String passwordError() {
        return i18Helper.getMessage("PasswordMissMatchError");
    }

    public String conditionError() {
        return i18Helper.getMessage("ConditionError");
    }

    public String emailValidatorMessage() {
        return i18Helper.getMessage("ValidatorMessage");
    }

    public String passwordWeak() {
        return i18Helper.getMessage("PasswordWeak");
    }

    public String phoneNumberError() {
        return i18Helper.getMessage("PhoneNumberError");
    }
    
    public TextField getTfEmail() {
        return tfEmail;
    }

    public TextField getTfSurName() {
        return tfSurName;
    }

    public TextField getTfFirstName() {
        return tfFirstName;
    }

    public TextField getTfPhoneNumber() {
        return tfPhoneNumber;
    }

    public PasswordField getPfPassWord1() {
        return pfPassWord1;
    }

    public PasswordField getPfPassWord2() {
        return pfPassWord2;
    }

    public String getVerificationWarning() {
        return verificationWarning;
    }

    public String getVerification() {
        return verification;
    }
}
