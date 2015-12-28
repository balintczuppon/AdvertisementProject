package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.StyleNames.ADVERTTITLE;
import com.mycompany.advertisementproject.Enums.control.SelectedAdvertController;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.LetterFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("SELECTED")
public class SelectedAdvertView extends HorizontalLayout implements View {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy MMMM dd");

    private Advertisement advertisement;

    private SelectedAdvertController controller;

    private VerticalLayout vlContent;
    private HorizontalLayout hlMeta;
    private HorizontalLayout hlPictures;
    private HorizontalLayout hlFields;

    private GoogleMap googleMap;

    private Panel advertPanel;

    private Embedded mainImage;

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
        getUI().focus();
    }

    private void buildView() {
        advertisement = (Advertisement) VaadinSession.getCurrent().getAttribute("selected_advert");
        controller = new SelectedAdvertController(this);

        addTitle();
        addMeta();
        addMainPicture();
        addOtherPictures();
        addPrice();
        addDescription();
        addMap();
        addOtherDetails();
        addPanel();
    }

    private void addPanel() {

        advertPanel = new Panel();
        advertPanel.setWidth("1000");
        advertPanel.setHeightUndefined();

        this.addComponent(advertPanel);
        this.setComponentAlignment(advertPanel, Alignment.TOP_CENTER);
        this.setSpacing(true);
        this.setMargin(true);
        this.setSizeFull();

        setContent();
        addComponentsToContent();
    }

    private void addTitle() {
        lblTitle = new Label(advertisement.getTitle());
        lblTitle.setStyleName(ADVERTTITLE.toString());
        lblTitle.setWidthUndefined();
        lblTitle.setStyleName("title");
    }

    private void addMeta() {
        hlMeta = new HorizontalLayout();
        hlMeta.setSpacing(true);

        lblAdvertiser = new Label("Hirdető: " + advertisement.getAdvertiserId().getName());
        lblAdvertiserPhoneNumber = new Label("Elérhetősége: " + advertisement.getAdvertiserId().getPhonenumber());
        lblRegDate = new Label("Feladás ideje: " + dateFormat.format(advertisement.getRegistrationDate()));

        hlMeta.addComponent(lblAdvertiser);
        hlMeta.addComponent(lblAdvertiserPhoneNumber);
        hlMeta.addComponent(lblRegDate);
    }

    private void addMainPicture() {
        controller.addMainPicture();
    }

    private void addOtherPictures() {
        hlPictures = new HorizontalLayout();
        hlPictures.setSpacing(true);
        controller.addOtherPictures(hlPictures);
    }

    private void addPrice() {
        lblPrice = new Label("Ár: " + advertisement.getPrice().toString() + " Ft");
        lblPrice.setWidthUndefined();
        lblPrice.setStyleName("title");
    }

    private void addDescription() {
        lblDescription = new Label(advertisement.getDescription());
        lblDescription.setWidth("640");
        lblDescription.setHeightUndefined();
    }

    private void addMap() {
        googleMap = new GoogleMap("apiKey", null, "Hungarian");
        googleMap.setSizeFull();
        googleMap.setCenter(new LatLon(46.080472, 18.211872));
        googleMap.setZoom(18);
        googleMap.addMarker("", new LatLon(46.080472, 18.211872), true, null);
    }

    private void addOtherDetails() {
        addContactLabel();
        addFields();
        addMessageArea();
        addSendButton();
    }

    private void addContactLabel() {
        lblConnection = new Label("További információk");
        lblConnection.setWidthUndefined();
    }

    private void addFields() {
        hlFields = new HorizontalLayout();
        hlFields.setWidth("640");
        hlFields.setSpacing(true);

        txtFldName = new TextField();
        txtFldName.setInputPrompt("Név");
        txtFldEmail = new TextField();
        txtFldEmail.setInputPrompt("E-mail cím");
        txtFldCustomerPhoneNumber = new TextField();
        txtFldCustomerPhoneNumber.setInputPrompt("Telefonszám");

        hlFields.addComponent(txtFldName);
        hlFields.addComponent(txtFldEmail);
        hlFields.addComponent(txtFldCustomerPhoneNumber);
    }

    private void addMessageArea() {
        txtAreaMessage = new TextArea();
        txtAreaMessage.setWidth("640");
        txtAreaMessage.setHeightUndefined();
        txtAreaMessage.setInputPrompt("Ide írja az üzenetét...");
    }

    private void addSendButton() {
        btnSendMessage = new Button("Elküldöm az üzenetet");
        btnSendMessage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.sendMail(advertisement);
            }
        });
    }

    private void setContent() {
        vlContent = new VerticalLayout();
        vlContent.setSizeFull();
        vlContent.setSpacing(true);
        vlContent.setMargin(true);
        vlContent.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }

    private void addComponentsToContent() {
        vlContent.addComponent(lblTitle);
        separeate();
        vlContent.addComponent(hlMeta);
        separeate();
        if(mainImage != null){
        vlContent.addComponent(mainImage);
        }
        vlContent.addComponent(hlPictures);
        vlContent.addComponent(lblPrice);
        separeate();
        vlContent.addComponent(lblDescription);
        separeate();
        vlContent.addComponent(googleMap);
        vlContent.addComponent(lblConnection);
        vlContent.addComponent(hlFields);
        vlContent.addComponent(txtAreaMessage);
        vlContent.addComponent(btnSendMessage);

        advertPanel.setContent(vlContent);
    }

    private void separeate() {
        vlContent.addComponent(new Label("<hr />", ContentMode.HTML));
    }

    public TextField getTxtFldName() {
        return txtFldName;
    }

    public TextField getTxtFldEmail() {
        return txtFldEmail;
    }

    public TextField getTxtFldCustomerPhoneNumber() {
        return txtFldCustomerPhoneNumber;
    }

    public TextArea getTxtAreaMessage() {
        return txtAreaMessage;
    }

    public LetterFacade getLetterFacade() {
        return letterFacade;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Embedded getMainImage() {
        return mainImage;
    }
}
