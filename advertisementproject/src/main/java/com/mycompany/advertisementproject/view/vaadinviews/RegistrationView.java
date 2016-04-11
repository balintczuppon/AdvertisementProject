package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.Views.LOGIN;
import com.mycompany.advertisementproject.control.RegistrationController;
import com.mycompany.advertisementproject.enumz.StyleNames;
import com.mycompany.advertisementproject.model.facades.AdvertiserFacade;
import com.mycompany.advertisementproject.model.facades.PostboxFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("REGISTRATION")
public class RegistrationView extends VerticalLayout implements View {

    private ResourceBundle bundle;

    private TextField tfEmail;
    private TextField tfName;
    private TextField tfPhoneNumber;

    private CheckBox chkBxTerms;
    private CheckBox chkBxNewsLetter;

    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;

    private Label lblTitle;

    private Button btnRegistration;

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
        bundle = AppBundle.currentBundle("");
        setMargin(true);
        addTitle();
        addForm();
        addButton();
        updateStrings();
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
        fl.addComponents(tfEmail, pfPassWord1, pfPassWord2, tfName, tfPhoneNumber, chkBxNewsLetter, chkBxTerms);
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

    public void updateStrings() {
        lblTitle.setCaption(bundle.getString("Registration"));
        tfEmail.setCaption(bundle.getString("TfEmail"));
        tfName.setCaption(bundle.getString("TfName"));
        tfPhoneNumber.setCaption(bundle.getString("TfPhone"));
        pfPassWord1.setCaption(bundle.getString("PfPassword"));
        pfPassWord2.setCaption(bundle.getString("PfPassword"));
        chkBxNewsLetter.setCaption(bundle.getString("CbNewsLetter"));
        chkBxTerms.setCaption(bundle.getString("CbTerms"));
        btnRegistration.setCaption(bundle.getString("Registration"));
    }

    public void goForward() {
        getUI().getNavigator().navigateTo(LOGIN.toString());
    }

    public String emailUsedError() {
        return bundle.getString("EmailUsedError");
    }

    public String emptyFieldError() {
        return bundle.getString("EmptyFieldError");
    }

    public String passwordError() {
        return bundle.getString("PasswordMissMatchError");
    }

    public String conditionError() {
        return bundle.getString("ConditionError");
    }

    public String emailValidatorMessage() {
        return bundle.getString("ValidatorMessage");
    }

    public AdvertiserFacade getAdvertiserFacade() {
        return advertiserFacade;
    }

    public PostboxFacade getPostboxFacade() {
        return postboxFacade;
    }

    public CheckBox getChkBxTerms() {
        return chkBxTerms;
    }

    public CheckBox getChkBxNewsLetter() {
        return chkBxNewsLetter;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }

    public TextField getTfName() {
        return tfName;
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
}
