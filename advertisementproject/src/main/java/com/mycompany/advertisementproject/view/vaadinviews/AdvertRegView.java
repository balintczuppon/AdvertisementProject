package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.mycompany.advertisementproject.control.AdvertRegController;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.ADVERTTOMODIFY;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
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

    private ResourceBundle bundle;

    private static boolean availability = false;

    private String modify;
    private String register;
    private String failedUpload;
    private String successUpload;
    private String failedModification;
    private String successModification;
    private String dropHere;
    private String removeButtonText;
    private String imageHeight;
    private String imageWidth;
    private String btnText;

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

    private Button btnRegister;

    private Label lblAdvertDetails;
    private Label labelPictureUpload;

    private TextField txtFieldTitle;
    private TextField txtFldPrice;

    private TextArea txtAreaDescription;

    private AdvertRegController controller;

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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        controller.checkSessionAttribute();
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            build();
        }
    } 
    
    public void build() {
        bundle = AppBundle.currentBundle();
        defaultSettings();
        addPictureUpload();
        addForm();
        updateStrings();
        controller.fillComboBoxes();
    }

    private void defaultSettings() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        controller = new AdvertRegController(this);
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
                layout.removeComponent(image);
                layout.removeComponent(button);
                pictureLayout.removeComponent(layout);
                controller.removeFile(file);
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
    }

    public void prepareForModification(Advertisement ad) {
        btnText = modify;
        btnRegister.setCaption(btnText);
        controller.linkDataToFields(ad);
        addModListener();
    }

    public void prepareForRegistration() {
        btnText = register;
        btnRegister.setCaption(btnText);
        addRegListener();
    }

    private void addModListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyAdvert();
                    Notification.show(successModification);
                } catch (Exception e) {
                    Notification.show(failedModification);
                    Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, e);
                }
                getUI().getNavigator().navigateTo(USERPAGE.toString());
                clearSessionAtribute();
            }
        }
        );
    }

    private void addRegListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.registerAdvert();
                    Notification.show(successUpload);
                } catch (Exception e) {
                    Notification.show(failedUpload);
                    Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, e);
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
                    controller.fillCmbBxSubCategory(cmbbxCategory.getValue());
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
                    controller.fillCmbBxCity(cmbbxCountry.getValue());
                } else {
                    cmbbxCity.setEnabled(false);
                }
            }
        });
    }

    public void updateStrings() {
        txtFieldTitle.setInputPrompt(bundle.getString("AdvertTitle"));
        txtFieldTitle.setWidth(bundle.getString("AdvertReg.TxtFldTitleWidth"));
        txtAreaDescription.setInputPrompt(bundle.getString("AdvertDescription"));
        txtAreaDescription.setWidth(bundle.getString("AdvertReg.taDesctriptionWidth"));
        txtAreaDescription.setHeight(bundle.getString("AdvertReg.taDesctriptionHeight"));
        cmbbxCategory.setInputPrompt(bundle.getString("Category"));
        cmbbxSubCategory.setInputPrompt(bundle.getString("SubCategory"));
        cmbbxAdvertType.setInputPrompt(bundle.getString("Type"));
        cmbbxAdvertState.setInputPrompt(bundle.getString("State"));
        cmbbxCity.setInputPrompt(bundle.getString("City"));
        cmbbxCountry.setInputPrompt(bundle.getString("Country"));
        txtFldPrice.setInputPrompt(bundle.getString("Price"));
        adverRegPanel.setWidth(bundle.getString("AdvertReg.advertPanelWidth"));
        lblAdvertDetails.setValue(bundle.getString("AdvertDetails"));
        imageWidth = bundle.getString("AdvertReg.imageWidth");
        imageHeight = bundle.getString("AdvertReg.imageHeight");
        removeButtonText = bundle.getString("Remove(X)");
        labelPictureUpload.setValue(bundle.getString("UploadPicture"));
        picturePanel.setWidth(bundle.getString("AdvertReg.PicturePanelWidth"));
        dropHere = bundle.getString("DropHere");
        failedUpload = bundle.getString("UpLoadFail");
        modify = bundle.getString("Modify");
        register = bundle.getString("Register");
        successUpload = bundle.getString("UpLoadSuccess");
        failedModification = bundle.getString("operationSuccess");
        successModification = bundle.getString("operationFailed");
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

    private void clearSessionAtribute() {
//        try {
//            VaadinSession.getCurrent().getLockInstance().lock();
//            VaadinSession.getCurrent().setAttribute(ADVERTTOMODIFY.toString(), null);
//        } finally {
//            VaadinSession.getCurrent().getLockInstance().unlock();
//        
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
