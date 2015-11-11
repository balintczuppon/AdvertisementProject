package com.mycompany.advertisementproject.UIs.Views;

import com.google.gwt.maps.client.overlays.Marker;
import static com.mycompany.advertisementproject.Enums.StyleNames.ADVERTTITLE;
import com.mycompany.advertisementproject.Tools.MailSender;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.LetterFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.codemonkey.simplejavamail.MailException;

@CDIView("SELECTED")
public class SelectedAdvert extends HorizontalLayout implements View {

    private static Advertisement advertisement;

    private VerticalLayout vl;

    private Panel advertPanel;

    private List<Picture> pictures = new ArrayList<>();

    private Embedded mainImage;

    private File file;
    private Label lblTitle;
    private Label lblAdvertiser;
    private Label lblRegDate;
    private Label lblPrice;
    private Label lblDescription;
    private Label lblConnection;
    private Label lblAdvertiserPhoneNumber;
    private TextField txtFldName;
    private TextField txtFldEmail;
    private TextField txtFldCustomerPhoneNumber;
    private TextArea txtAreaMessage;
    private Button btnSendMessage;

    @Inject
    AdvertisementFacade advertisementFacade;
    @Inject
    LetterFacade letterFacade;

    @PostConstruct
    public void initComponent() {
        buildView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    private void buildView() {
        advertPanel = new Panel();
        advertPanel.setWidth("1000");
        advertPanel.setHeightUndefined();

        this.setSizeFull();
        vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setSpacing(true);
        vl.setMargin(true);

        lblTitle = new Label(advertisement.getTitle());
        lblTitle.setStyleName(ADVERTTITLE.toString());
        lblTitle.setWidthUndefined();
        lblTitle.setStyleName("title");
        vl.addComponent(lblTitle);
        vl.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        HorizontalLayout metaVl = new HorizontalLayout();
        metaVl.setSpacing(true);
        lblAdvertiser = new Label("Hirdető: " + advertisement.getAdvertiserId().getName());
        metaVl.addComponent(lblAdvertiser);
        lblAdvertiserPhoneNumber = new Label("Elérhetősége: " + advertisement.getAdvertiserId().getPhonenumber());
        metaVl.addComponent(lblAdvertiserPhoneNumber);
        String formattedDate = new SimpleDateFormat("yyyy MMMM dd").format(advertisement.getRegistrationDate());
        lblRegDate = new Label("Feladás ideje: " + formattedDate);
        metaVl.addComponent(lblRegDate);
        vl.addComponent(metaVl);
        vl.setComponentAlignment(metaVl, Alignment.TOP_CENTER);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        pictures = (List<Picture>) advertisement.getPictureCollection();

        if (!pictures.isEmpty()) {
            file = new File(pictures.get(0).getAccessPath());
            FileResource source = new FileResource(file);
            final Embedded img = new Embedded();
            img.setSource(source);
            loadMainImage(img);
            vl.addComponent(mainImage);
            vl.setComponentAlignment(mainImage, Alignment.TOP_CENTER);
        }

        HorizontalLayout hlPictures = new HorizontalLayout();
        hlPictures.setSpacing(true);
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
        vl.addComponent(hlPictures);
        vl.setComponentAlignment(hlPictures, Alignment.TOP_CENTER);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        lblPrice = new Label("Ár: " + advertisement.getPrice().toString() + " Ft");
        lblPrice.setWidthUndefined();
        lblPrice.setStyleName("title");
        vl.addComponent(lblPrice);
        vl.setComponentAlignment(lblPrice, Alignment.TOP_CENTER);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        lblDescription = new Label(advertisement.getDescription());
        lblDescription.setWidth("640");
        lblDescription.setHeightUndefined();
        vl.addComponent(lblDescription);
        vl.setComponentAlignment(lblDescription, Alignment.TOP_CENTER);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        GoogleMap googleMap = new GoogleMap("apiKey", null, "Hungarian");
        googleMap.setSizeFull();
        googleMap.setCenter(new LatLon(46.080472, 18.211872));
        googleMap.setZoom(18);
        googleMap.addMarker("", new LatLon(46.080472, 18.211872), true, null);

        vl.addComponent(googleMap);

        vl.addComponent(new Label("<hr />", ContentMode.HTML));

        lblConnection = new Label("További információk");
        lblConnection.setWidthUndefined();
        vl.addComponent(lblConnection);
        vl.setComponentAlignment(lblConnection, Alignment.TOP_CENTER);

        HorizontalLayout hor = new HorizontalLayout();
        hor.setWidth("640");

        txtFldName = new TextField();
        txtFldName.setInputPrompt("Név");
        txtFldEmail = new TextField();
        txtFldEmail.setInputPrompt("E-mail cím");
        txtFldCustomerPhoneNumber = new TextField();
        txtFldCustomerPhoneNumber.setInputPrompt("Telefonszám");

        hor.addComponent(txtFldName);
        hor.addComponent(txtFldEmail);
        hor.addComponent(txtFldCustomerPhoneNumber);

        hor.setSpacing(true);

        vl.addComponent(hor);
        vl.setComponentAlignment(hor, Alignment.TOP_CENTER);

        txtAreaMessage = new TextArea();
        txtAreaMessage.setWidth("640");
        txtAreaMessage.setHeightUndefined();
        txtAreaMessage.setInputPrompt("Ide írja az üzenetét...");
        vl.addComponent(txtAreaMessage);
        vl.setComponentAlignment(txtAreaMessage, Alignment.TOP_CENTER);

        btnSendMessage = new Button("Elküldöm az üzenetet");
        btnSendMessage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                sendMail(advertisement);
            }
        });
        vl.addComponent(btnSendMessage);
        vl.setComponentAlignment(btnSendMessage, Alignment.TOP_CENTER);

        advertPanel.setContent(vl);
        this.addComponent(advertPanel);
        this.setComponentAlignment(advertPanel, Alignment.TOP_CENTER);
        this.setSpacing(true);
        this.setMargin(true);
    }

    private void setAsMainImage(Embedded image) {
        mainImage.setSource(image.getSource());
        loadMainImage(mainImage);
    }

    private void loadMainImage(Embedded image) {
        if (!pictures.isEmpty()) {
            mainImage = image;
            mainImage.setWidth("640");
            mainImage.setHeight("480");
        }
    }

    public static void setAdvertisement(Advertisement Advertisement) {
        SelectedAdvert.advertisement = Advertisement;
    }

//    public String enquirerLetter() {
//        String htmlLink = "<a href=http://localhost:8080/advertisementproject/#!ADVERTS>Click here</a></br>";
//        String text
//                = "<p>"
//                + "Dear Enquirer,<br><br>"
//                + "You got a new message from " + advertisement.getAdvertiserId().getName() + "<br><br>"
//                + "You can read it below this link:<br>"
//                + htmlLink + "<br><br>"
//                + "Greetings,<br>"
//                + "VaadinThesis Team"
//                + "</p>";
//        return text;
//    }
    private String advertiserLetter() {
        String htmlLink = "<a href=http://localhost:8080/advertisementproject/#!LOGIN>I'll check it.</a></br>";
        String text
                = "<p>"
                + "Dear Advertiser,<br><br>"
                + txtFldName.getValue() + " has some questions about you advertisenment: " + advertisement.getTitle() + "<br><br>"
                + "Answer them!<br>"
                + htmlLink + "<br><br>"
                + "Greetings,<br>"
                + "VaadinThesis Team"
                + "</p>";
        return text;
    }

    private void sendMail(Advertisement advertisement) {
        try {
            //Sending the mail.
            MailSender ms = new MailSender();
            ms.setReceiver(advertisement.getAdvertiserId().getEmail());
            ms.setSender(txtFldEmail.getValue());

            //Saját email ellenőrzés miatt.
            ms.setReceiver("balintczuppon@gmail.com");
            ms.setSender("balintczuppon@gmail.com");

            ms.setSubject("Enquiry");
            ms.setText(advertiserLetter());
            ms.send();

            //Storing the mail in DB.
            Letter letter = new Letter();
            letter.setMailtext(txtAreaMessage.getValue());
            letter.setMailtitle(advertisement.getTitle());
            letter.setQuestionermail(txtFldEmail.getValue());
            letter.setQuestionername(txtFldName.getValue());
            letter.setQuestionerphone(txtFldCustomerPhoneNumber.getValue());
            letter.setSender(Boolean.FALSE);
            letter.setPostBoxId(advertisement.getAdvertiserId().getPostbox());

            //Store in the advertiser's postbox
            advertisement.getAdvertiserId().getPostbox().addLetter(letter);

            letterFacade.create(letter);

        } catch (MailException me) {
            me.printStackTrace();
        }
    }
}
