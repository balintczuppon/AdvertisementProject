package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.toolz.MailSender;
import com.mycompany.advertisementproject.view.vaadinviews.SelectedAdvertView;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.model.entities.Picture;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.toolz.Global;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Czuppon Balint Peter
 */
public class SelectedAdvertController implements Serializable {

    @Inject
    private LetterFacade letterFacade;

    private List<Picture> pictures = new ArrayList<>();
    private File file;

    private SelectedAdvertView view;

    public void sendMail(Advertisement adv) throws Exception {
        checkPhoneNumber(view.getTxtFldCustomerPhoneNumber().getValue().trim());
        createLetter(adv);
        sendLetter(adv);
    }

    private void createLetter(Advertisement adv) throws Exception {
        Letter letter = new Letter();
        letter.setMailtext(view.getTxtAreaMessage().getValue());
        letter.setMailtitle(adv.getTitle());
        letter.setSendermail(view.getTxtFldEmail().getValue());
        letter.setSendername(view.getTxtFldName().getValue());
        letter.setSenderphone(view.getTxtFldCustomerPhoneNumber().getValue());
        letter.setSendDate(Global.currentDate());
        letter.setSender(Boolean.FALSE);
        letter.setAdvertiserId(adv.getAdvertiserId());
        letter.setAdvertisementId(adv.getId());
        adv.getAdvertiserId().addLetter(letter);
        letterFacade.create(letter);
    }

    private void sendLetter(Advertisement adv) throws Exception {
        MailSender ms = new MailSender();
        ms.setReceiver(adv.getAdvertiserId().getEmail());
        ms.setSender(view.getTxtFldEmail().getValue());
        ms.setSubject(view.getLetterSubject());
        ms.setText(letterText(adv));
        ms.send();
    }

    private String letterText(Advertisement a) throws Exception {
        String htmlLink = "<a href=" + Global.PAGE_LINK + view.getViewName() + " >" + view.getLinkText() + "</a></br>";
        String text
                = "<p>"
                + view.getGreetingText() + "<br><br>"
                + view.getTxtFldName().getValue() + view.getMessageText1() + a.getTitle() + "<br><br>"
                + view.getMessageText2() + "<br>"
                + htmlLink + "<br><br>"
                + view.getGeneratedMessage()
                + "<br><br>"
                + view.getGoodbyeText() + "<br>"
                + view.getSenderName()
                + "</p>";
        return text;
    }

    public void addMainPicture() throws Exception {
        pictures = (List<Picture>) view.getAdvertisement().getPictureCollection();
        if (!pictures.isEmpty()) {
            file = new File(pictures.get(0).getAccessPath());
            FileResource source = new FileResource(file);
            final Embedded img = new Embedded();
            img.setSource(source);
            loadMainImage(img);
        }
    }

    private void setAsMainImage(Embedded image) throws Exception {
        view.getMainImage().setSource(image.getSource());
        loadMainImage(view.getMainImage());
    }

    private void loadMainImage(Embedded image) throws Exception {
        if (!pictures.isEmpty()) {
            image.setWidth(view.getMainImageWidth());
            image.setHeight(view.getMainImageHeight());
            view.setMainImage(image);
        }
    }

    public void addOtherPictures(HorizontalLayout hlPictures) throws Exception {
        if (!pictures.isEmpty()) {
            for (Picture picture : pictures) {
                file = new File(picture.getAccessPath());
                final Embedded image = new Embedded();
                image.setWidth(view.getImageWidth());
                image.setHeight(view.getImageHeight());
                image.setSource(new FileResource(file));
                hlPictures.addComponent(image);
                image.addClickListener(new MouseEvents.ClickListener() {
                    @Override
                    public void click(MouseEvents.ClickEvent event) {
                        try {
                            setAsMainImage(image);
                        } catch (Exception ex) {
                            Logger.getLogger(SelectedAdvertController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
    }

    public void validateEmail(String value) throws Exception {
        EmailValidator validator = new EmailValidator(view.getValidatorMessage());
        validator.validate(value);
        if (value.isEmpty()) {
            throw new InvalidValueException(view.getValidatorMessage());
        }
    }

    private void checkPhoneNumber(String phoneNumber) throws Exception {
        if (!phoneNumber.isEmpty()) {
            if (!phoneNumber.matches(Global.PHONE_REGEX)) {
                throw new Exception(view.getPhoneNumberError());
            }
        }
    }

    public void setView(SelectedAdvertView view) {
        this.view = view;
    }
}
