package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.SELECTEDADVERT;
import static com.mycompany.advertisementproject.enumz.StyleNames.TITLE;
import com.mycompany.advertisementproject.control.SelectedAdvertController;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Validator.InvalidValueException;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("SELECTED")
public class SelectedAdvertView extends HorizontalLayout implements View {

    private ResourceBundle bundle;

    private String advertPanelWidth;
    private String lblAdvertiserCaption;
    private String lblAdvertiserPhoneCaption;
    private String lblRegDateCaption;
    private String lblPriceCaption;
    private String lblDescriptionWidth;
    private String lblConnectionCaption;
    private String hlFieldsWidth;
    private String txtFldNameCaption;
    private String txtFldEmailCaption;
    private String txtFldPhoneCaption;
    private String txtAreaMessageWidth;
    private String txtAreaMessagePrompt;
    private String btnSendMessageCaption;
    private String googlemaplocal;
    private String googlemapzoom;
    private String commitMessage;
    private String letterSubject;
    private String imageWidth;
    private String imageHeight;
    private String mainImageWidth;
    private String mainImageHeight;
    private String validatorMessage;
    private String pageLink;
    private String viewName;
    private String linkText;
    private String greetingText;
    private String messageText1;
    private String messageText2;
    private String goodbyeText;
    private String senderName;

    private Label lblTitle;
    private Label lblAdvertiser;
    private Label lblRegDate;
    private Label lblPrice;
    private Label lblDescription;
    private Label lblConnection;
    private Label lblAdvertiserPhoneNumber;

    private HorizontalLayout hlMeta;
    private HorizontalLayout hlPictures;
    private HorizontalLayout hlFields;

    private TextField txtFldName;
    private TextField txtFldEmail;
    private TextField txtFldCustomerPhoneNumber;

    private TextArea txtAreaMessage;

    private Advertisement advertisement;

    private SelectedAdvertController controller;

    private VerticalLayout vlContent;

    private GoogleMap googleMap;

    private Panel advertPanel;

    private Embedded mainImage;

    private Button btnSendMessage;

    @Inject
    private LetterFacade letterFacade;

    @PostConstruct
    public void initComponent() {
        bundle = AppBundle.currentBundle();
        buildView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    private void buildView() {
        advertisement = (Advertisement) VaadinSession.getCurrent().getAttribute(SELECTEDADVERT.toString());
        controller = new SelectedAdvertController(this);
        controller.setLetterFacade(letterFacade);
        build();
    }

    public void build() {
        try {
            updateStrings();
            addTitle();
            addMeta();
            addMainPicture();
            addOtherPictures();
            addPrice();
            addDescription();
            addMap();
            addOtherDetails();
            addPanel();
        } catch (Exception ex) {
            Logger.getLogger(SelectedAdvertView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPanel() {
        advertPanel = new Panel();
        advertPanel.setWidth(advertPanelWidth);
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
        lblTitle.setStyleName(TITLE.toString());
        lblTitle.setWidthUndefined();
    }

    private void addMeta() {
        hlMeta = new HorizontalLayout();
        hlMeta.setSpacing(true);

        lblAdvertiser = new Label(lblAdvertiserCaption + advertisement.getAdvertiserId().getName());
        lblAdvertiserPhoneNumber = new Label(lblAdvertiserPhoneCaption + advertisement.getAdvertiserId().getPhonenumber());
        lblRegDate = new Label(lblRegDateCaption + Global.DATEFORMAT.format(advertisement.getRegistrationDate()));

        hlMeta.addComponent(lblAdvertiser);
        hlMeta.addComponent(lblAdvertiserPhoneNumber);
        hlMeta.addComponent(lblRegDate);
    }

    private void addMainPicture() throws Exception {
        controller.addMainPicture();
    }

    private void addOtherPictures() throws Exception {
        hlPictures = new HorizontalLayout();
        hlPictures.setSpacing(true);
        controller.addOtherPictures(hlPictures);
    }

    private void addPrice() {
        lblPrice = new Label(lblPriceCaption + advertisement.getPrice().toString() + Global.CURRENCY);
        lblPrice.setWidthUndefined();
        lblPrice.setStyleName(TITLE.toString());
    }

    private void addDescription() {
        lblDescription = new Label(advertisement.getDescription());
        lblDescription.setWidth(lblDescriptionWidth);
        lblDescription.setHeightUndefined();
    }

    private void addMap() {
        if (advertisement.getMapId() != null) {
            googleMap = new GoogleMap("apiKey", null, googlemaplocal);
            googleMap.setSizeFull();
            googleMap.setCenter(
                    new LatLon(advertisement.getMapId().getCordx(), advertisement.getMapId().getCordy())
            );
            googleMap.setZoom(Integer.valueOf(googlemapzoom));
            googleMap.addMarker(
                    "",
                    new LatLon(advertisement.getMapId().getCordx(), advertisement.getMapId().getCordy()),
                    true,
                    null
            );
        }
    }

    private void addOtherDetails() {
        addContactLabel();
        addFields();
        addMessageArea();
        addSendButton();
    }

    private void addContactLabel() {
        lblConnection = new Label(lblConnectionCaption);
        lblConnection.setWidthUndefined();
    }

    private void addFields() {
        hlFields = new HorizontalLayout();
        hlFields.setWidth(hlFieldsWidth);
        hlFields.setSpacing(true);

        txtFldName = new TextField();
        txtFldName.setInputPrompt(txtFldNameCaption);
        txtFldEmail = new TextField();
        txtFldEmail.setInputPrompt(txtFldEmailCaption);
        txtFldCustomerPhoneNumber = new TextField();
        txtFldCustomerPhoneNumber.setInputPrompt(txtFldPhoneCaption);

        hlFields.addComponent(txtFldName);
        hlFields.addComponent(txtFldEmail);
        hlFields.addComponent(txtFldCustomerPhoneNumber);
    }

    private void addMessageArea() {
        txtAreaMessage = new TextArea();
        txtAreaMessage.setWidth(txtAreaMessageWidth);
        txtAreaMessage.setHeightUndefined();
        txtAreaMessage.setInputPrompt(txtAreaMessagePrompt);
    }

    private void addSendButton() {
        btnSendMessage = new Button(btnSendMessageCaption);
        btnSendMessage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.validateEmail(txtFldEmail.getValue());
                    controller.sendMail(advertisement);
                    clearFields();
                    commitMessageSend();
                } catch (InvalidValueException ex) {
                    Notification.show(ex.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(SelectedAdvertView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void clearFields() {
        txtFldEmail.clear();
        txtFldName.clear();
        txtFldCustomerPhoneNumber.clear();
        txtAreaMessage.clear();
    }

    private void commitMessageSend() {
        Notification.show(commitMessage);
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
        if (mainImage != null) {
            vlContent.addComponent(mainImage);
        }
        vlContent.addComponent(hlPictures);
        vlContent.addComponent(lblPrice);
        separeate();
        vlContent.addComponent(lblDescription);
        separeate();
        if (googleMap != null) {
            vlContent.addComponent(googleMap);
        }
        vlContent.addComponent(lblConnection);
        vlContent.addComponent(hlFields);
        vlContent.addComponent(txtAreaMessage);
        vlContent.addComponent(btnSendMessage);
        advertPanel.setContent(vlContent);
    }

    private void separeate() {
        vlContent.addComponent(new Label("<hr />", ContentMode.HTML));
    }

    public void updateStrings() {
        advertPanelWidth = bundle.getString("Selected.AdvertPanelWidth");
        lblAdvertiserCaption = bundle.getString("Advertiser");
        lblAdvertiserPhoneCaption = bundle.getString("Attainability");
        lblRegDateCaption = bundle.getString("UploadDate");
        lblPriceCaption = bundle.getString("Price");
        lblDescriptionWidth = bundle.getString("Selected.DescriptionLabelWidth");
        lblConnectionCaption = bundle.getString("OtherDetails");
        hlFieldsWidth = bundle.getString("Selected.FieldsLayoutWidth");
        txtFldNameCaption = bundle.getString("TfName");
        txtFldEmailCaption = bundle.getString("TfEmail");
        txtFldPhoneCaption = bundle.getString("TfPhone");
        txtAreaMessageWidth = bundle.getString("Selected.TaMessageWidth");
        txtAreaMessagePrompt = bundle.getString("TaMessage");
        btnSendMessageCaption = bundle.getString("Send");
        googlemaplocal = bundle.getString("GoogleMapLocal");
        googlemapzoom = bundle.getString("GoogleMapZoom");
        commitMessage = bundle.getString("CommitMessage");
        letterSubject = bundle.getString("Selected.LetterSubject");
        imageWidth = bundle.getString("Selected.imageWidth");
        imageHeight = bundle.getString("Selected.imageHeight");
        mainImageHeight = bundle.getString("Selected.mainImageHeight");
        mainImageWidth = bundle.getString("Selected.mainImageWidth");
        validatorMessage = bundle.getString("Selected.vaildatorMessage");
        pageLink = bundle.getString("Selected.Pagelink");
        viewName = bundle.getString("Selected.ViewName");
        linkText = bundle.getString("Selected.LinkText");
        greetingText = bundle.getString("Selected.GreetingText");
        messageText1 = bundle.getString("Selected.MessageText1");
        messageText2 = bundle.getString("Selected.MessageText2");
        goodbyeText = bundle.getString("Selected.GoodbyeText");
        senderName = bundle.getString("Selected.SenderName");
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

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Embedded getMainImage() {
        return mainImage;
    }

    public void setMainImage(Embedded mainImage) {
        this.mainImage = mainImage;
    }

    public String getLetterSubject() {
        return letterSubject;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getMainImageWidth() {
        return mainImageWidth;
    }

    public String getMainImageHeight() {
        return mainImageHeight;
    }

    public String getValidatorMessage() {
        return validatorMessage;
    }

    public String getPageLink() {
        return pageLink;
    }

    public String getViewName() {
        return viewName;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getGreetingText() {
        return greetingText;
    }

    public String getMessageText1() {
        return messageText1;
    }

    public String getMessageText2() {
        return messageText2;
    }

    public String getGoodbyeText() {
        return goodbyeText;
    }

    public String getSenderName() {
        return senderName;
    }
}
