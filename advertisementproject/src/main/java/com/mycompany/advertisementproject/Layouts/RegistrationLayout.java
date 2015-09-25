/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.Layouts;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author balin
 */
public class RegistrationLayout {

    private String eMailText = "E-mail cím";
    private String passWordText1 = "Jelszó";
    private String passWordText2 = "Jelszó újra";
    private String regButtonText = "Regisztrálok";

    private Label lblTitle;

    private PasswordField pfPassWord1;
    private PasswordField pfPassWord2;
    private TextField tfEmail;

    private Button btnRegistration;

    private FormLayout fl;
    private VerticalLayout vl;

    public VerticalLayout buildView() {
        vl = new VerticalLayout();
        addTitle();
        addForm();
        addButton();
        markFields();
        return vl;
    }

    private void addTitle() {
        lblTitle = new Label("Fiók létrehozása");
        lblTitle.setStyleName("title");
        lblTitle.setSizeUndefined();
        vl.addComponent(lblTitle);
        vl.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
    }

    private void addForm() {
        fl = new FormLayout();
        tfEmail = new TextField(eMailText);
        fl.addComponent(tfEmail);
        pfPassWord1 = new PasswordField(passWordText1);
        fl.addComponent(pfPassWord1);
        pfPassWord2 = new PasswordField(passWordText2);
        fl.addComponent(pfPassWord2);
        vl.addComponent(fl);
        fl.setWidthUndefined();
        vl.setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
    }

    private void addButton() {
        btnRegistration = new Button(regButtonText);
        vl.addComponent(btnRegistration);
        vl.setComponentAlignment(btnRegistration, Alignment.MIDDLE_CENTER);
    }

    private void markFields() {
        pfPassWord1.setRequired(true);
        pfPassWord2.setRequired(true);
        tfEmail.setRequired(true);
    }
}
