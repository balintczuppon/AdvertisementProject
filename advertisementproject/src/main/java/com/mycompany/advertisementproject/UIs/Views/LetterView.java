/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.Views;
import com.mycompany.advertisementproject.Tools.MailSender;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.facades.LetterFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.codemonkey.simplejavamail.MailException;

/**
 *
 * @author balin
 */
@CDIView("LETTERVIEW")
public class LetterView extends VerticalLayout implements View {

    private int letterID = 0;

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

    @Inject
    private LetterFacade LetterFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getParameter(event);
        getUI().focus();
        try {
            checkSessionAttribute();
        } catch (Exception ex) {
            Logger.getLogger(LetterView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkSessionAttribute() throws Exception {
        if (letterID == 0) {
            try {
                Letter letter = (Letter) VaadinSession.getCurrent().getAttribute("letterToShow");
                if (letter != null) {
                    showLetter(letter);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Letter letter = LetterFacade.findById(letterID);
                if (letter != null) {
                    showLetter(letter);
                }
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }

    private void getParameter(ViewChangeListener.ViewChangeEvent event) {
        String parameter = event.getParameters().split("/")[0];
        if (!parameter.isEmpty()) {
            letterID = Integer.valueOf(parameter);
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

    private void showLetter(final Letter letter) {
        vlLetter.setSpacing(true);

        lblEnquirer = new Label("Feladó:");
        lblEnquirerName = new Label(letter.getSendername());

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
        btnDeleteMail = new Button("Törlöm");
        btnBack = new Button("Vissza");
        addClickListeners(letter);

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

    private void addClickListeners(final Letter letter) {
        btnAnswerMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                response(letter);
            }
        });
        btnDeleteMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                deleteLetter(letter);
            }
        });
        btnBack.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                jumpBack();
            }
        });
    }

    private int responseLetterID;

    private void response(Letter letter) {
        Advertiser current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        try {
            //Storing the mail in DB.
            Letter responseLetter = new Letter();
            responseLetter.setMailtext(taLetterToWrite.getValue());
            responseLetter.setMailtitle("RE:" + letter.getMailtitle());
            responseLetter.setSendermail(current_advertiser.getEmail());
            responseLetter.setSendername(current_advertiser.getName());
            responseLetter.setSenderphone(current_advertiser.getPhonenumber());
            responseLetter.setSender(Boolean.TRUE);
            responseLetter.setPostBoxId(current_advertiser.getPostbox());
            
            responseLetter.setAdvertisementId(letter.getAdvertisementId());
            //Store in the advertiser's postbox
            current_advertiser.getPostbox().addLetter(responseLetter);

            LetterFacade.create(responseLetter);
            
            responseLetterID = responseLetter.getId();

            //Sending the mail.
            MailSender ms = new MailSender();
            ms.setReceiver(letter.getSendermail());
            ms.setSender(current_advertiser.getEmail());

            //Saját email ellenőrzés miatt.
            ms.setReceiver("balintczuppon@gmail.com");
            ms.setSender("balintczuppon@gmail.com");

            ms.setSubject("RE:" + letter.getMailtitle());
            ms.setText(letterText());
            ms.send();

        } catch (MailException me) {
            me.printStackTrace();
        }
    }

    private String letterText() {
        Advertiser current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        String htmlLink = "<a href=http://localhost:8080/advertisementproject/#!LETTERVIEW/" + responseLetterID + ">I check it.</a></br>";
        String text
                = "<p>"
                + "Dear Enquirer,<br><br>"
                + current_advertiser.getName() + " has answered to your question. <br><br>"
                + "Check it!<br>"
                + htmlLink + "<br><br>"
                + "Best regards,<br>"
                + "VaadinThesis Team"
                + "</p>";
        return text;
    }

    private void deleteLetter(Letter letter) {
        LetterFacade.remove(letter);
        getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    private void jumpBack() {
        getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    private void addTextArea() {
        taLetterToWrite = new TextArea();
        taLetterToWrite.setInputPrompt("Ide írhatja az üzetet..");
        taLetterToWrite.setWidth("800");
        taLetterToWrite.setHeight("200");
    }
}
