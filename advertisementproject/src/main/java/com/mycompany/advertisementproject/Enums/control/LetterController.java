/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.Enums.control;

import com.mycompany.advertisementproject.Enums.Views;
import com.mycompany.advertisementproject.Tools.MailSender;
import com.mycompany.advertisementproject.UIs.Views.LetterView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Letter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import org.codemonkey.simplejavamail.MailException;

/**
 *
 * @author balin
 */
public class LetterController {

    private LetterView view;

    private int letterID = 0;

    public LetterController(LetterView view) {
        this.view = view;
    }

    public void checkSessionAttribute() throws Exception {
        if (letterID == 0) {
            try {
                Letter letter = (Letter) VaadinSession.getCurrent().getAttribute("letterToShow");
                if (letter != null) {
                    view.showLetter(letter);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Letter letter = view.getLetterFacade().findById(letterID);
                if (letter != null) {
                    view.showLetter(letter);
                }
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }

    public void getParameter(ViewChangeListener.ViewChangeEvent event) {
        String parameter = event.getParameters().split("/")[0];
        if (!parameter.isEmpty()) {
            letterID = Integer.valueOf(parameter);
        }
    }

    public void response(Letter letter) {
        Advertiser current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        try {
            //Storing the mail in DB.
            Letter responseLetter = new Letter();
            responseLetter.setMailtext(view.getTaLetterToWrite().getValue());
            responseLetter.setMailtitle("RE:" + letter.getMailtitle());
            responseLetter.setSendermail(current_advertiser.getEmail());
            responseLetter.setSendername(current_advertiser.getName());
            responseLetter.setSenderphone(current_advertiser.getPhonenumber());
            responseLetter.setSender(Boolean.TRUE);
            responseLetter.setPostBoxId(current_advertiser.getPostbox());

            responseLetter.setAdvertisementId(letter.getAdvertisementId());
            //Store in the advertiser's postbox
            current_advertiser.getPostbox().addLetter(responseLetter);

            view.getLetterFacade().create(responseLetter);

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

    public void deleteLetter(Letter letter) {
        view.getLetterFacade().remove(letter);
        view.getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    public void jumpBack() {
        view.getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    private int responseLetterID;

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

}
