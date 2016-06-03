package com.mycompany.advertisementproject.toolz;

import com.vaadin.ui.Notification;

public class EmailVerificator {

    private MailSender ms;
    private I18Helper i18Helper;

    private String pageLink;
    private String login;
    private String message;
    private String subject;

    public EmailVerificator() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        ms = new MailSender();
        updateStrings();
    }

    public void sendVerification(String verificationID, String emailAdresse) {
        try{
        ms.setReceiver(emailAdresse);
        ms.setSender(ms.getUsername());
        ms.setSubject(subject);
        ms.setText(emailText(verificationID));
        ms.send();
        }catch(Exception e){
            Notification.show("Sikeretelen üzenetküldés");
        }
    }

    private String emailText(String verificationID) {
        String htmlLink = "<a href=" + pageLink + login + "/" + verificationID + " >" + message + "</a> </br>";
        String text = "<p>" + htmlLink + "</p>";
        System.out.println(htmlLink);
        return text;
    }

    private void updateStrings() {
        subject = i18Helper.getMessage("EmialVerification.Subject");
        pageLink = i18Helper.getMessage("Pagelink");
        login = i18Helper.getMessage("Login.ViewName");
        message = i18Helper.getMessage("VerificationMessage");
    }
}
