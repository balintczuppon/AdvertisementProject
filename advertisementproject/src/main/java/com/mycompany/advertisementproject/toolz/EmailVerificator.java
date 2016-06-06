package com.mycompany.advertisementproject.toolz;

import com.vaadin.ui.Notification;

/**
 *
 * @author Czuppon Balint Peter
 */
public class EmailVerificator {

    private MailSender ms;
    private I18Helper i18Helper;
    
    private String login;
    private String message;
    private String subject;
    private String emailSendFailed;
    private String greetingText;
    private String messageText;
    private String generatedMessage;
    private String goodbyeText;
    private String senderName;

    public EmailVerificator() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        ms = new MailSender();
        updateStrings();
    }

    public void sendVerification(String verificationID, String emailAdresse) {
        try {
            ms.setReceiver(emailAdresse);
            ms.setSender(Global.USERNAME);
            ms.setSubject(subject);
            ms.setText(emailText(verificationID));
            ms.send();
        } catch (Exception e) {
            Notification.show(emailSendFailed);
        }
    }

    private String emailText(String verificationID) {
        String htmlLink = "<a href=" + Global.PAGE_LINK + login + "/" + verificationID + " >" + message + "</a> </br>";
        String text
                = "<p>"
                + greetingText + "<br><br>"
                + messageText + "<br><br><"
                + htmlLink + "<br><br>"
                + generatedMessage
                + "<br><br>"
                + goodbyeText + "<br>"
                + senderName
                + "</p>";
        return text;

    }

    private void updateStrings() {
        subject = i18Helper.getMessage("EmialVerification.Subject");
        login = i18Helper.getMessage("Login.ViewName");
        message = i18Helper.getMessage("VerificationMessage");
        emailSendFailed = i18Helper.getMessage("EmailSendFailed");
        generatedMessage = i18Helper.getMessage("GeneratedMessage");
        senderName = i18Helper.getMessage("Selected.SenderName");
        goodbyeText = i18Helper.getMessage("Selected.GoodbyeText");
        greetingText = i18Helper.getMessage("Verificator.GreetingText");
        messageText = i18Helper.getMessage("Verificator.Message");
    }
}
