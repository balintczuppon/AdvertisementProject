
package com.mycompany.advertisementproject.Enums.control;

import com.mycompany.advertisementproject.Tools.MailSender;
import com.mycompany.advertisementproject.UIs.Views.SelectedAdvert;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.entities.Picture;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectedAdvertController {

    private List<Picture> pictures = new ArrayList<>();
    private File file;

    private SelectedAdvert selected;

    public SelectedAdvertController(SelectedAdvert selectedAdvert) {
        selected = selectedAdvert;
    }

    public void sendMail(Advertisement adv) {
        try {

            Letter letter = new Letter();
            letter.setMailtext(selected.getTxtAreaMessage().getValue());
            letter.setMailtitle(adv.getTitle());
            letter.setSendermail(selected.getTxtFldEmail().getValue());
            letter.setSendername(selected.getTxtFldName().getValue());
            letter.setSenderphone(selected.getTxtFldCustomerPhoneNumber().getValue());
            letter.setSender(Boolean.FALSE);
            letter.setPostBoxId(adv.getAdvertiserId().getPostbox());
            letter.setAdvertisementId(adv.getId());

            adv.getAdvertiserId().getPostbox().addLetter(letter);
            selected.getLetterFacade().create(letter);

            MailSender ms = new MailSender();
            ms.setReceiver(adv.getAdvertiserId().getEmail());
            ms.setSender(selected.getTxtFldEmail().getValue());
            ms.setReceiver("balintczuppon@gmail.com");
            ms.setSender("balintczuppon@gmail.com");
            ms.setSubject("Enquiry");
            ms.setText(letterText(adv));
            ms.send();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String letterText(Advertisement a) {
        String htmlLink = "<a href=http://localhost:8080/advertisementproject/#!LOGIN>I check it.</a></br>";
        String text
                = "<p>"
                + "Dear Advertiser,<br><br>"
                + selected.getTxtFldName().getValue() + " has some questions about your advertisenment: " + a.getTitle() + "<br><br>"
                + "Answer them!<br>"
                + htmlLink + "<br><br>"
                + "Best regards,<br>"
                + "VaadinThesis Team"
                + "</p>";
        return text;
    }

    public void addMainPicture() {
        pictures = (List<Picture>) selected.getAdvertisement().getPictureCollection();
        if (!pictures.isEmpty()) {
            file = new File(pictures.get(0).getAccessPath());
            FileResource source = new FileResource(file);
            final Embedded img = new Embedded();
            img.setSource(source);
            loadMainImage(img);
        }
    }

    private void setAsMainImage(Embedded image) {
        selected.getMainImage().setSource(image.getSource());
        loadMainImage(selected.getMainImage());
    }

    private void loadMainImage(Embedded image) {
//        if (!pictures.isEmpty()) {
//            mainImage = image;
//            mainImage.setWidth("640");
//            mainImage.setHeight("480");
//        }
    }

    public void addOtherPictures(HorizontalLayout hlPictures) {
        if (!pictures.isEmpty()) {
            for (int i = 0; i < pictures.size(); i++) {
                file = new File(pictures.get(i).getAccessPath());
                final Embedded image = new Embedded();
                image.setWidth("128");
                image.setHeight("96");
                image.setSource(new FileResource(file));
                hlPictures.addComponent(image);
                image.addClickListener(new MouseEvents.ClickListener() {
                    @Override
                    public void click(MouseEvents.ClickEvent event) {
                        setAsMainImage(image);
                    }
                });
            }
        }
    }
}
