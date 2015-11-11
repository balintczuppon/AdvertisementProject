/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.entities.Letter;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

/**
 *
 * @author balin
 */
@CDIView("LETTERVIEW")
public class LetterView extends VerticalLayout implements View {

    private VerticalLayout vlLetter = new VerticalLayout();

    private Panel panel;
    private Label lblMailText;
    private Label lblMailTitle;
    private Label lblText;
    private Label lblEnquirer;
    private Label lblEnquirerName;
    private Label lblTitle;

    private TextArea taLetterToWrite;
    private TextArea taLetterToShow;

    private Button btnAnswerMail;
    private Button btnDeleteMail;
    private Button btnBack;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
        checkSessionAttribute();
    }

    private void checkSessionAttribute() {
        try {
            Letter letter = (Letter) VaadinSession.getCurrent().getAttribute("letterToShow");
            if (letter != null) {
                showLetter(letter);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PostConstruct
    public void initComponents() {
        buildView();
    }

    private void buildView() {
        panel = new Panel();
        panel.setWidth("1000");
        panel.setHeightUndefined();
        vlLetter.setMargin(true);
        panel.setContent(vlLetter);
    }

    private void showLetter(Letter letter) {
        vlLetter.setSpacing(true);

        lblEnquirer = new Label("Feladó:");
        lblEnquirerName = new Label(letter.getQuestionername());

        lblTitle = new Label("Tárgy:");
        lblMailTitle = new Label(letter.getMailtitle());
        lblMailTitle.setWidthUndefined();

        lblText = new Label("Üzenet:");
        taLetterToShow = new TextArea();
        taLetterToShow.setWidth("950");
        taLetterToShow.setHeightUndefined();
        taLetterToShow.setValue(letter.getMailtext());
        taLetterToShow.setReadOnly(true);
        taLetterToShow.setWordwrap(true);

        addTextArea();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthUndefined();
        hl.setSpacing(true);

        btnAnswerMail = new Button("Válaszolok");
        btnAnswerMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
//                response();
            }
        });
        btnDeleteMail = new Button("Törlöm");
        btnBack = new Button("Vissza");
        hl.addComponent(btnAnswerMail);
        hl.addComponent(btnDeleteMail);
        hl.addComponent(btnBack);

        vlLetter.addComponent(lblEnquirer);
        vlLetter.addComponent(lblEnquirerName);
        vlLetter.addComponent(lblTitle);
        vlLetter.addComponent(lblMailTitle);
        vlLetter.addComponent(lblText);
        vlLetter.addComponent(taLetterToShow);

        vlLetter.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        vlLetter.addComponent(new Label("<hr />", ContentMode.HTML));
        vlLetter.addComponent(taLetterToWrite);
        vlLetter.addComponent(hl);

        addComponent(panel);
        setComponentAlignment(panel, Alignment.TOP_CENTER);
        setMargin(true);
    }

    private void addTextArea() {
        taLetterToWrite = new TextArea();
        taLetterToWrite.setInputPrompt("Ide írhatja az üzetet..");
        taLetterToWrite.setWidth("800");
        taLetterToWrite.setHeight("200");
    }
}
