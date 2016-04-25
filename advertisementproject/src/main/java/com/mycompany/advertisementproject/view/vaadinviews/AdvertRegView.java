package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.mycompany.advertisementproject.control.AdvertChangeController;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.io.File;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTREG")
public class AdvertRegView extends VerticalLayout implements View {

    private static boolean availability = false;

    protected String modify;
    private String register;
    private String failedUpload;
    private String successUpload;
    protected String failedModification;
    protected String successModification;
    private String dropHere;
    private String removeButtonText;
    private String imageHeight;
    private String imageWidth;

    private ComboBox cmbbxCategory;
    private ComboBox cmbbxSubCategory;
    private ComboBox cmbbxAdvertType;
    private ComboBox cmbbxAdvertState;
    private ComboBox cmbbxCountry;
    private ComboBox cmbbxCity;

    private VerticalLayout pictureLayout;
    private VerticalLayout advertDataLayout;

    private Panel adverRegPanel;
    private Panel picturePanel;

    protected Button btnRegister;

    private Label lblAdvertDetails;
    private Label labelPictureUpload;

    private TextField txtFieldTitle;
    private TextField txtFldPrice;

    private TextArea txtAreaDescription;

    protected AdvertChangeController controller;

    private FormLayout regFormLayout;

    @Inject
    private MaincategoryFacade maincategoryFacade;
    @Inject
    private SubcategoryFacade subcategoryFacade;
    @Inject
    private AdverttypeFacade adverttypeFacade;
    @Inject
    private AdvertisementFacade advertisementFacade;
    @Inject
    private AdvertstateFacade advertstateFacade;
    @Inject
    private CountryFacade countryFacade;
    @Inject
    private CityFacade cityFacade;
    
    private I18Helper i18Helper;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            build();
        }
    }

    public void build() {
        try {
            i18Helper = new I18Helper(AppBundle.currentBundle());
            defaultSettings();
            addPictureUpload();
            addForm();
            updateStrings();
            controller.fillComboBoxes();
        } catch (Exception ex) {
            Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void defaultSettings() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        controller = new AdvertChangeController(this);
        setController();
    }

    private void addForm() {
        initRegForm();
        initFields();
        addFieldsToLayoout();
        initRegPanel();
        initDataLayout();
        adverRegPanel.setContent(advertDataLayout);
        addComponent(adverRegPanel);
        setComponentAlignment(adverRegPanel, Alignment.TOP_CENTER);
    }

    private void initRegForm() {
        regFormLayout = new FormLayout();
        regFormLayout.setSpacing(true);
        regFormLayout.setMargin(true);
        regFormLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }

    private void initFields() {
        txtFieldTitle = new TextField();
        txtAreaDescription = new TextArea();
        cmbbxCategory = new ComboBox();
        addCmbBxCategoryListener();
        cmbbxSubCategory = new ComboBox();
        cmbbxSubCategory.setEnabled(false);
        cmbbxAdvertType = new ComboBox();
        cmbbxAdvertState = new ComboBox();
        txtFldPrice = new TextField();
        cmbbxCountry = new ComboBox();
        addCmbBxCountryListener();
        cmbbxCity = new ComboBox();
        cmbbxCity.setEnabled(false);
        btnRegister = new Button();
        btnRegister.setCaption(register);
        addRegButtonListener();
    }

    private void addFieldsToLayoout() {
        regFormLayout.addComponent(txtFieldTitle);
        regFormLayout.addComponent(txtAreaDescription);
        regFormLayout.addComponent(cmbbxCategory);
        regFormLayout.addComponent(cmbbxSubCategory);
        regFormLayout.addComponent(cmbbxAdvertType);
        regFormLayout.addComponent(cmbbxAdvertState);
        regFormLayout.addComponent(txtFldPrice);
        regFormLayout.addComponent(cmbbxCountry);
        regFormLayout.addComponent(cmbbxCity);
        regFormLayout.setSizeFull();
    }

    private void initRegPanel() {
        adverRegPanel = new Panel();
        adverRegPanel.setHeightUndefined();
    }

    private void initDataLayout() {
        advertDataLayout = new VerticalLayout();
        advertDataLayout.setSpacing(true);
        advertDataLayout.setMargin(true);
        advertDataLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        lblAdvertDetails = new Label();
        advertDataLayout.addComponent(lblAdvertDetails);
        advertDataLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        advertDataLayout.addComponent(regFormLayout);
        advertDataLayout.addComponent(btnRegister);
    }

    public void showImage(File file) {
        Embedded image = imageToShow(file);
        HorizontalLayout innerPictureLayout = innerPicture(file);
        Button removeBtn = new Button(removeButtonText);
        innerPictureLayout.addComponent(removeBtn);
        addRemoveButtonToPicture(file, removeBtn, innerPictureLayout, image);
        pictureLayout.addComponent(innerPictureLayout);
    }

    private void addRemoveButtonToPicture(
            final File file,
            final Button button,
            final HorizontalLayout layout,
            final Embedded image) {

        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    layout.removeComponent(image);
                    layout.removeComponent(button);
                    pictureLayout.removeComponent(layout);
                    controller.removeFile(file);
                } catch (Exception ex) {
                    Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private Embedded imageToShow(File file) {
        Embedded imageToShow = new Embedded();
        imageToShow.setHeight(imageHeight);
        imageToShow.setWidth(imageWidth);
        imageToShow.setSource(new FileResource(file));
        return imageToShow;
    }

    private HorizontalLayout innerPicture(File file) {
        HorizontalLayout innerPictureLayout = new HorizontalLayout();
        innerPictureLayout.setSpacing(true);
        innerPictureLayout.addComponent(imageToShow(file));
        innerPictureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        return innerPictureLayout;
    }

    private void addPictureUpload() {
        try {
            picturePanel = new Panel();

            controller.setUpLoadField();

            pictureLayout = new VerticalLayout();
            pictureLayout.setSizeFull();
            pictureLayout.setSpacing(true);
            pictureLayout.setMargin(true);
            labelPictureUpload = new Label();
            pictureLayout.addComponent(labelPictureUpload);
            pictureLayout.addComponent(new Label("<hr />", ContentMode.HTML));

            pictureLayout.addComponent(controller.getMfu());

            pictureLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
            picturePanel.setContent(pictureLayout);

            addComponent(picturePanel);
            setComponentAlignment(picturePanel, Alignment.TOP_CENTER);
        } catch (Exception ex) {
            Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRegButtonListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.registerAdvert();
                    Notification.show(successUpload);
                } catch (Exception e) {
                    Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, e);
                    Notification.show(failedUpload);
                }
                getUI().getNavigator().navigateTo(USERPAGE.toString());
            }
        });
    }

    private void addCmbBxCategoryListener() {
        cmbbxCategory.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbbxCategory.getValue() != null) {
                    try {
                        controller.fillCmbBxSubCategory(cmbbxCategory.getValue());
                    } catch (Exception ex) {
                        Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    cmbbxSubCategory.setEnabled(false);
                }
            }
        });
    }

    private void addCmbBxCountryListener() {
        cmbbxCountry.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbbxCountry.getValue() != null) {
                    try {
                        controller.fillCmbBxCity(cmbbxCountry.getValue());
                    } catch (Exception ex) {
                        Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    cmbbxCity.setEnabled(false);
                }
            }
        });
    }

    public void updateStrings() {
        txtFieldTitle.setInputPrompt(i18Helper.getMessage("AdvertTitle"));
        txtFieldTitle.setWidth(i18Helper.getMessage("AdvertReg.TxtFldTitleWidth"));
        txtAreaDescription.setInputPrompt(i18Helper.getMessage("AdvertDescription"));
        txtAreaDescription.setWidth(i18Helper.getMessage("AdvertReg.taDesctriptionWidth"));
        txtAreaDescription.setHeight(i18Helper.getMessage("AdvertReg.taDesctriptionHeight"));
        cmbbxCategory.setInputPrompt(i18Helper.getMessage("Category"));
        cmbbxSubCategory.setInputPrompt(i18Helper.getMessage("SubCategory"));
        cmbbxAdvertType.setInputPrompt(i18Helper.getMessage("Type"));
        cmbbxAdvertState.setInputPrompt(i18Helper.getMessage("State"));
        cmbbxCity.setInputPrompt(i18Helper.getMessage("City"));
        cmbbxCountry.setInputPrompt(i18Helper.getMessage("Country"));
        txtFldPrice.setInputPrompt(i18Helper.getMessage("Price"));
        adverRegPanel.setWidth(i18Helper.getMessage("AdvertReg.advertPanelWidth"));
        lblAdvertDetails.setValue(i18Helper.getMessage("AdvertDetails"));
        imageWidth = i18Helper.getMessage("AdvertReg.imageWidth");
        imageHeight = i18Helper.getMessage("AdvertReg.imageHeight");
        removeButtonText = i18Helper.getMessage("Remove(X)");
        labelPictureUpload.setValue(i18Helper.getMessage("UploadPicture"));
        picturePanel.setWidth(i18Helper.getMessage("AdvertReg.PicturePanelWidth"));
        dropHere = i18Helper.getMessage("DropHere");
        failedUpload = i18Helper.getMessage("UpLoadFail");
        modify = i18Helper.getMessage("Modify");
        register = i18Helper.getMessage("Register");
        successUpload = i18Helper.getMessage("UpLoadSuccess");
        failedModification = i18Helper.getMessage("operationFailed");
        successModification = i18Helper.getMessage("operationSuccess");
        btnRegister.setCaption(register);
    }

    private void setController() {
        controller.setAdvertisementFacade(advertisementFacade);
        controller.setAdvertstateFacade(advertstateFacade);
        controller.setAdverttypeFacade(adverttypeFacade);
        controller.setCityFacade(cityFacade);
        controller.setCountryFacade(countryFacade);
        controller.setMaincategoryFacade(maincategoryFacade);
        controller.setSubcategoryFacade(subcategoryFacade);
    }

    public ComboBox getCmbbxCategory() {
        return cmbbxCategory;
    }

    public ComboBox getCmbbxSubCategory() {
        return cmbbxSubCategory;
    }

    public ComboBox getCmbbxAdvertType() {
        return cmbbxAdvertType;
    }

    public ComboBox getCmbbxAdvertState() {
        return cmbbxAdvertState;
    }

    public ComboBox getCmbbxCountry() {
        return cmbbxCountry;
    }

    public ComboBox getCmbbxCity() {
        return cmbbxCity;
    }

    public TextField getTxtFieldTitle() {
        return txtFieldTitle;
    }

    public TextArea getTxtAreaDescription() {
        return txtAreaDescription;
    }

    public TextField getTxtFldPrice() {
        return txtFldPrice;
    }

    public String getDropHere() {
        return dropHere;
    }

    public static void setAvailability(boolean availability) {
        AdvertRegView.availability = availability;
    }
}
