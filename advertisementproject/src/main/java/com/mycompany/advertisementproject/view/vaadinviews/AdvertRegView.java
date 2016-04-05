package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.PictureFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import static com.mycompany.advertisementproject.enumz.Views.USERPAGE;
import com.mycompany.advertisementproject.control.AdvertRegController;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.io.File;
import java.util.ResourceBundle;
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
    private Button removeBtn;

    private Label lblAdvertDetails;
    private Label labelPictureUpload;

    private TextField txtFieldTitle;
    private TextField txtFldPrice;

    private TextArea txtAreaDescription;

    private AdvertRegController controller;

    private FormLayout regFormLayout;

    private HorizontalLayout innerPictureLayout;

    private Embedded image;

    @Inject
    private MaincategoryFacade maincategoryFacade;
    @Inject
    private SubcategoryFacade subcategoryFacade;
    @Inject
    private PictureFacade pictureFacade;
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
        getUI().focus();
        controller.checkSessionAttribute();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            bundle = AppBundle.currentBundle("");
            build();
        }
    }

    public void build() {
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
        image = new Embedded();
        image.setHeight(imageHeight);
        image.setWidth(imageWidth);
        image.setSource(new FileResource(file));

        innerPictureLayout = new HorizontalLayout();
        innerPictureLayout.setSpacing(true);
        innerPictureLayout.addComponent(image);

        removeBtn = new Button(removeButtonText);
        addRemoveButtonToPicture(file, image);
        innerPictureLayout.addComponent(removeBtn);
        innerPictureLayout.setComponentAlignment(removeBtn, Alignment.MIDDLE_LEFT);

        pictureLayout.addComponent(innerPictureLayout);
    }

    private void addRemoveButtonToPicture(final File file, final Embedded image) {
        removeBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.removeFile(file);
                innerPictureLayout.removeComponent(image);
                innerPictureLayout.removeComponent(removeBtn);
                pictureLayout.removeComponent(innerPictureLayout);
            }
        });
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
                } catch (Exception e) {
                    Notification.show(failedModification);
                }
                Notification.show(successModification);
                getUI().getNavigator().navigateTo(USERPAGE.toString());
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
                } catch (Exception e) {
                    Notification.show(failedUpload);
                }
                Notification.show(successUpload);
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
        failedModification = bundle.getString("ModificationSuccess");
        successModification = bundle.getString("ModificationFailed");
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

    public MaincategoryFacade getMaincategoryFacade() {
        return maincategoryFacade;
    }

    public PictureFacade getPictureFacade() {
        return pictureFacade;
    }

    public AdverttypeFacade getAdverttypeFacade() {
        return adverttypeFacade;
    }

    public AdvertisementFacade getAdvertisementFacade() {
        return advertisementFacade;
    }

    public AdvertstateFacade getAdvertstateFacade() {
        return advertstateFacade;
    }

    public CountryFacade getCountryFacade() {
        return countryFacade;
    }

    public CityFacade getCityFacade() {
        return cityFacade;
    }

    public SubcategoryFacade getSubcategoryFacade() {
        return subcategoryFacade;
    }

    public String getDropHere() {
        return dropHere;
    }

    public static void setAvailability(boolean availability) {
        AdvertRegView.availability = availability;
    }
}
