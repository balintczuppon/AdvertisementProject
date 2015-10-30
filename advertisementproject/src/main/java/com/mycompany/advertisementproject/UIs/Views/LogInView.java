package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.Layouts.AppLayout;
import com.mycompany.advertisementproject.entities.Advertiser;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LOGIN")
public class LogInView extends VerticalLayout implements View {

    private List<Advertiser> users = new ArrayList<>();

    @Inject
    AdvertiserFacade advertiserFacade;

    private String eMailText = "E-mail cím";
    private String passWordText1 = "Jelszó";
    private String regButtonText = "Bejelentkezés";

    private Label lblTitle;
    private PasswordField pfPassWord1;
    private TextField tfEmail;
    private Button btnRegistration;

    private FormLayout fl;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
        getUI().focus();
    }

    @PostConstruct
    public void initContent() {
        addTitle();
        addForm();
        addButton();
        addListeners();
        markFields();
    }

    private void addTitle() {
        setMargin(true);
        lblTitle = new Label("Bejelentkezés");
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
        addComponent(fl);
        fl.setWidthUndefined();
        setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnRegistration = new Button(regButtonText);
        addComponent(btnRegistration);
        setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);
    }

    private void markFields() {
        pfPassWord1.setRequired(true);
        tfEmail.setRequired(true);
    }

    private void addListeners() {
        btnRegistration.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    authentication();
                } catch (Exception ex) {
                    Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void authentication() throws Exception {
                users = advertiserFacade.findAll();
                try {
                    for (Advertiser a : users) {
                        if (a.getEmail().equals(tfEmail.getValue())
                                && a.getPassword().equals(pfPassWord1.getValue())) {
                            revealSecuredContex(a);
                            return;
                        }
                    }
                    Notification.show("You have failed this login.");
                } catch (Exception e) {
                    throw new Exception(e);
                } finally {
                    clearFields();
                }
            }

            private void clearFields() {
                tfEmail.clear();
                pfPassWord1.clear();
            }

            private void revealSecuredContex(Advertiser a) {
                AppLayout.login(a);
                getUI().getNavigator().navigateTo(USERPAGE.toString());
            }
        });
    }
}
