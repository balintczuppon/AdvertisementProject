package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.SELECTEDADVERT;
import static com.mycompany.advertisementproject.enumz.StyleNames.TITLE;
import com.mycompany.advertisementproject.control.SelectedAdvertController;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@CDIView("SELECTED")
public class SelectedAdvertView extends HorizontalLayout implements View {

    @Inject
    private SelectedAdvertController controller;

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
    private String generatedMessage;
    private String emailSendFailed;

    private Label lblTitle;
    private Label lblAdvertiser;
    private Label lblRegDate;
    private Label lblPrice;
    private Label lblDescription;
    private Label lblConnection;
    private Label lblAdvertiserPhoneNumber;

    private HorizontalLayout hlMeta;
    private HorizontalLayout hlPictures;
    private VerticalLayout vlFields;

    private TextField txtFldName;
    private TextField txtFldEmail;
    private TextField txtFldCustomerPhoneNumber;

    private TextArea txtAreaMessage;

    private Advertisement advertisement;

    private VerticalLayout vlContent;

    private GoogleMap googleMap;

    private Panel advertPanel;

    private Embedded mainImage;

    private Button btnSendMessage;

    private I18Helper i18Helper;

    @PostConstruct
    public void initComponent() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        buildView();
        updateView();
        getUI().focus();
    }

    private void buildView() {
        initAdvertisement();
        i18Helper = new I18Helper(AppBundle.currentBundle());
        controller.setView(this);
        build();
    }

    private void initAdvertisement() {
        advertisement = (Advertisement) VaadinSession.getCurrent().getAttribute(SELECTEDADVERT.toString());
        if (advertisement == null) {
            getUI().getNavigator().navigateTo("");
        }
    }

    public void build() {
        try {
            this.removeAllComponents();
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
            addComponentsToContent();
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
    }

    private void addTitle() {
        lblTitle = new Label();
        lblTitle.setStyleName(TITLE.toString());
        lblTitle.setWidthUndefined();
    }

    private void addMeta() {
        hlMeta = new HorizontalLayout();
        hlMeta.setSpacing(true);

        lblAdvertiser = new Label();
        lblAdvertiserPhoneNumber = new Label();
        lblRegDate = new Label();

        hlMeta.addComponent(lblAdvertiser);
        hlMeta.addComponent(lblAdvertiserPhoneNumber);
        hlMeta.addComponent(lblRegDate);
    }

    private void addMainPicture() throws Exception {
        controller.addMainPicture();
    }

    private void addOtherPictures() {
        try {
            hlPictures = new HorizontalLayout();
            hlPictures.setSpacing(true);
            controller.addOtherPictures(hlPictures);
        } catch (Exception ex) {
            Logger.getLogger(SelectedAdvertView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPrice() {
        if (advertisement.getPrice() != null) {
            lblPrice = new Label();
            lblPrice.setWidthUndefined();
            lblPrice.setStyleName(TITLE.toString());
        }
    }

    private void addDescription() {
        lblDescription = new Label();
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
        vlFields = new VerticalLayout();
        vlFields.setWidth(hlFieldsWidth);
        vlFields.setSpacing(true);

        txtFldName = new TextField();
        txtFldName.setInputPrompt(txtFldNameCaption);
        txtFldEmail = new TextField();
        txtFldEmail.setInputPrompt(txtFldEmailCaption);
        txtFldCustomerPhoneNumber = new TextField();
        txtFldCustomerPhoneNumber.setInputPrompt(txtFldPhoneCaption);

        vlFields.addComponent(txtFldName);
        vlFields.addComponent(txtFldEmail);
        vlFields.addComponent(txtFldCustomerPhoneNumber);
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
                    Notification.show(commitMessage);
                } catch (InvalidValueException ex) {
                    Notification.show(ex.getMessage());
                } catch (Exception ex) {
                    Notification.show(emailSendFailed);
                    clearFields();
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
        separeate();
        vlContent.addComponent(lblPrice);
        separeate();
        vlContent.addComponent(lblDescription);
        separeate();
        if (googleMap != null) {
            vlContent.addComponent(googleMap);
        }
        separeate();
        vlContent.addComponent(lblConnection);
        vlContent.addComponent(vlFields);
        vlContent.addComponent(txtAreaMessage);
        vlContent.addComponent(btnSendMessage);
        advertPanel.setContent(vlContent);
    }

    private void separeate() {
        vlContent.addComponent(new Label("<hr />", ContentMode.HTML));
    }

    private void updateView() {
        try {
            lblTitle.setValue(StringUtils.defaultString(advertisement.getTitle()));
            lblAdvertiser.setValue(lblAdvertiserCaption + ": " + StringUtils.defaultString(advertisement.getAdvertiserId().getName()));
            lblAdvertiserPhoneNumber.setValue(lblAdvertiserPhoneCaption + ": " + StringUtils.defaultString(advertisement.getAdvertiserId().getPhonenumber()));
            lblRegDate.setValue(lblRegDateCaption + ": " + StringUtils.defaultString(Global.DATEFORMAT.format(advertisement.getRegistrationDate())));
            lblPrice.setValue(lblPriceCaption + ": " + StringUtils.defaultString(Global.CURRENCY.format(Global.exchange_huf_to_gbp(advertisement.getPrice()))));
            lblDescription.setValue(StringUtils.defaultString(advertisement.getDescription()));
        } catch (Exception ex) {
            Logger.getLogger(SelectedAdvertView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStrings() {
        advertPanelWidth = i18Helper.getMessage("Selected.AdvertPanelWidth");
        lblAdvertiserCaption = i18Helper.getMessage("Advertiser");
        lblAdvertiserPhoneCaption = i18Helper.getMessage("Attainability");
        lblRegDateCaption = i18Helper.getMessage("UploadDate");
        lblPriceCaption = i18Helper.getMessage("Price");
        lblDescriptionWidth = i18Helper.getMessage("Selected.DescriptionLabelWidth");
        lblConnectionCaption = i18Helper.getMessage("OtherDetails");
        hlFieldsWidth = i18Helper.getMessage("Selected.FieldsLayoutWidth");
        txtFldNameCaption = i18Helper.getMessage("TfName");
        txtFldEmailCaption = i18Helper.getMessage("TfEmail");
        txtFldPhoneCaption = i18Helper.getMessage("TfPhone");
        txtAreaMessageWidth = i18Helper.getMessage("Selected.TaMessageWidth");
        txtAreaMessagePrompt = i18Helper.getMessage("TaMessage");
        btnSendMessageCaption = i18Helper.getMessage("Send");
        googlemaplocal = i18Helper.getMessage("GoogleMapLocal");
        googlemapzoom = i18Helper.getMessage("GoogleMapZoom");
        commitMessage = i18Helper.getMessage("CommitMessage");
        letterSubject = i18Helper.getMessage("Selected.LetterSubject");
        imageWidth = i18Helper.getMessage("Selected.imageWidth");
        imageHeight = i18Helper.getMessage("Selected.imageHeight");
        mainImageHeight = i18Helper.getMessage("Selected.mainImageHeight");
        mainImageWidth = i18Helper.getMessage("Selected.mainImageWidth");
        validatorMessage = i18Helper.getMessage("Selected.vaildatorMessage");
        pageLink = i18Helper.getMessage("Pagelink");
        viewName = i18Helper.getMessage("Selected.ViewName");
        linkText = i18Helper.getMessage("Selected.LinkText");
        greetingText = i18Helper.getMessage("Selected.GreetingText");
        messageText1 = i18Helper.getMessage("Selected.MessageText1");
        messageText2 = i18Helper.getMessage("Selected.MessageText2");
        goodbyeText = i18Helper.getMessage("Selected.GoodbyeText");
        senderName = i18Helper.getMessage("Selected.SenderName");
        generatedMessage = i18Helper.getMessage("GeneratedMessage");
        emailSendFailed = i18Helper.getMessage("EmailSendFailed");
    }

    public String getGeneratedMessage() {
        return generatedMessage;
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
