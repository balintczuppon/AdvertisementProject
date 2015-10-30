package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Postbox;
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

    private static final int DEFAULT_AUTHORITY = 2;

    @Inject
    AdvertiserFacade advertiserFacade;
    @Inject
    PostboxFacade postboxFacade;

    private String eMailText = "E-mail cím";
    private String passWordText1 = "Jelszó";
    private String passWordText2 = "Jelszó újra";
    private String regButtonText = "Regisztrálok";

    private Label lblTitle;

    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;
    private TextField tfEmail;
    private CheckBox chkBxTerms;

    private Button btnRegistration;

    private FormLayout fl;

    @PostConstruct
    public void initComponent() {
        buildView();
    }

    public void buildView() {
        addTitle();
        addForm();
        addButton();
        addListeners();
        markFields();
    }

    private void addTitle() {
        setMargin(true);
        lblTitle = new Label("Fiók létrehozása");
        lblTitle.setStyleName("title");
        lblTitle.setSizeUndefined();
        addComponent(lblTitle);
        setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
    }

    private void addForm() {
        fl = new FormLayout();
        tfEmail = new TextField(eMailText);
        fl.addComponent(tfEmail);
        pfPassWord1 = new PasswordField(passWordText1);
        fl.addComponent(pfPassWord1);
        pfPassWord2 = new PasswordField(passWordText2);
        fl.addComponent(pfPassWord2);
        chkBxTerms = new CheckBox("Szerződési feltételek elfogadása.");
        fl.addComponent(chkBxTerms);
        addComponent(fl);
        fl.setWidthUndefined();
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnRegistration = new Button(regButtonText);
        addComponent(btnRegistration);
        setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);
    }

    private void addListeners() {
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                registration();
            }
        });
    }

    private void registration() {
        if (!tfEmail.isEmpty() && !pfPassWord1.isEmpty() && !pfPassWord2.isEmpty()) {
            if (pfPassWord1.getValue().equals(pfPassWord2.getValue())) {
                if (chkBxTerms.getValue()) {
                    registerNewUser();
                } else {
                    Notification.show(new Exception("A feltételek elfogadása nélkül nem regisztrálhat.").getMessage());
                }
            } else {
                Notification.show(new Exception("A jelszavak nem egyeznek").getMessage());
            }
        } else {
            Notification.show(new Exception("Minden mező kitöltése kötelező").getMessage());
        }
    }

    private void registerNewUser() {
        Postbox postbox = new Postbox();

        Advertiser advertiser = new Advertiser();
        advertiser.setEmail(tfEmail.getValue().trim());
        advertiser.setPassword(pfPassWord2.getValue().trim());
        advertiser.setAuthority(DEFAULT_AUTHORITY);
        
        advertiser.setName("JóskaPista");
        advertiser.setPhonenumber("+36303030300");
        advertiser.setNewsletter(true);
        
        advertiserFacade.create(advertiser);      
        
        /**
         * Az utolsóként beszúrt hirdető Id-jét lekérjük, majd ez lesz a postaláda Id-ja is.
         * Meglehetősen ronda megoldás de máshogy nem jött össze a beszúrás :(
         **/
        
        int id = advertiserFacade.findAll().get(advertiserFacade.findAll().size()-1).getId();
        postbox.setId(id);
        postboxFacade.create(postbox);
        
        Notification.show("Sikeres regisztráció");
        clearFields();
    }

    private void markFields() {
        pfPassWord1.setRequired(true);
        pfPassWord2.setRequired(true);
        tfEmail.setRequired(true);
    }

    private void clearFields() {
        pfPassWord1.clear();
        pfPassWord2.clear();
        tfEmail.clear();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
        getUI().focus();
    }

    private void jumpToAdverts() {
        getUI().getNavigator().navigateTo(USERPAGE.toString());
    }

}
