package com.mycompany.advertisementproject.toolz;

public class EmailVerificator {

    private MailSender ms;

    public EmailVerificator() {
        ms = new MailSender();
    }

    public void sendVerification(String verificationID, String emailAdresse) {
        ms.setReceiver(emailAdresse);
        ms.setSender(ms.getUsername());
        ms.setSubject("Email Verification");
        ms.setText(emailText(verificationID));
        ms.send();
    }

    private String emailText(String verificationID) {
        String htmlLink = "<a href=http://localhost:8080/advertisementproject/#!LOGIN/" + verificationID + ">click here to verify your account.</a></br>";
        String text = "<p>" + htmlLink + "</p>";
        System.out.println(htmlLink);
        return text;
    }
}
