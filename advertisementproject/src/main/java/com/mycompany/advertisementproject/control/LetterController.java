package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.LETTERTOSHOW;
import com.mycompany.advertisementproject.enumz.Views;
import com.mycompany.advertisementproject.view.layouts.AppLayout;
import com.mycompany.advertisementproject.toolz.MailSender;
import com.mycompany.advertisementproject.view.vaadinviews.LetterView;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;

public class LetterController {

    private LetterView view;

    private int letterID = 0;

    public LetterController(LetterView view) {
        this.view = view;
    }

    public void checkSessionAttribute() throws Exception {
        if (letterID == 0) {
            try {
                Letter letter = (Letter) VaadinSession.getCurrent().getAttribute(LETTERTOSHOW.toString());
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

    public void response(Letter letter) throws Exception {
        Advertiser current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());

        Letter responseLetter = new Letter();
        responseLetter.setMailtext(view.getTaLetterToWrite().getValue());
        responseLetter.setMailtitle(view.getResponsePrefix() + letter.getMailtitle());
        responseLetter.setSendermail(current_advertiser.getEmail());
        responseLetter.setSendername(current_advertiser.getName());
        responseLetter.setSenderphone(current_advertiser.getPhonenumber());
        responseLetter.setSender(Boolean.TRUE);
        responseLetter.setSendDate(AppLayout.currentDate());
        responseLetter.setPostBoxId(current_advertiser.getPostbox());
        responseLetter.setAdvertisementId(letter.getAdvertisementId());

        current_advertiser.getPostbox().addLetter(responseLetter);

        view.getLetterFacade().create(responseLetter);

        responseLetterID = responseLetter.getId();

        MailSender ms = new MailSender();
        ms.setReceiver(letter.getSendermail());
        ms.setSender(current_advertiser.getEmail());
        ms.setReceiver("balintczuppon@gmail.com");
        ms.setSender("balintczuppon@gmail.com");
        ms.setSubject(view.getResponsePrefix() + letter.getMailtitle());
        ms.setText(letterText());
        ms.send();
    }

    public void deleteLetter(Letter letter) throws Exception {
        view.getLetterFacade().remove(letter);
        view.getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    public void jumpBack() {
        view.getUI().getNavigator().navigateTo(Views.USERPAGE.toString());
    }

    private int responseLetterID;

    private String letterText() throws Exception {
        Advertiser current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        String htmlLink = "<a href="+view.getPageLink()+view.getViewName()+"/" + responseLetterID + ">"+view.getLinkText()+"</a></br>";
        String text
                = "<p>"
                + view.getGreetingText()+"<br><br>"
                + current_advertiser.getName() + view.getMessageText1()+"<br><br>"
                + view.getMessageText2()+"<br>"
                + htmlLink + "<br><br>"
                + view.getGoodbyeText()+"<br>"
                + view.getSenderName()
                + "</p>";
        return text;
    }

}
