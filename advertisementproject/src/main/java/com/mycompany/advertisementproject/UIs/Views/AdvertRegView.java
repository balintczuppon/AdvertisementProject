package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.Enums.control.AdvertRegController;
import com.mycompany.advertisementproject.Tools.XmlFileReader;
import com.mycompany.advertisementproject.entities.*;
import com.mycompany.advertisementproject.facades.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import java.util.logging.Logger;
import com.vaadin.ui.*;
import java.io.File;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTREG")
public class AdvertRegView extends VerticalLayout implements View {

    private AdvertRegController controller;

    private Panel adverRegPanel;
    private Panel picturePanel;

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

    private TextField txtFieldTitle;
    private TextArea txtAreaDescription;
    private ComboBox cmbbxCategory;
    private ComboBox cmbbxSubCategory;
    private ComboBox cmbbxAdvertType;
    private ComboBox cmbbxAdvertState;
    private ComboBox cmbbxCountry;
    private ComboBox cmbbxCity;

    private TextField txtFldPrice;

    private String btnText;
    private Button btnRegister;
    private Button removeBtn;

    private FormLayout regFormLayout;
    private HorizontalLayout innerPictureLayout;
    private VerticalLayout pictureLayout;
    private VerticalLayout advertDataLayout;
    private XmlFileReader xmlReader;

    private Label lblAdvertDetails;
    private Label labelPictureUpload;
    
    private String modify;
    private String register;
    private String failedUpload;
    private String successUpload;
    private String dropHere;
    private String removeButtonText;
    
    private Embedded image;
    private String imageHeight;
    private String imageWidth;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
        controller.checkSessionAttribute();
    }

    @PostConstruct
    public void initComponent() {
        defaultSettings();
        addPictureUpload();
        addForm();
        addLabelText();
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
                controller.modifyAdvert();
                getUI().getNavigator().navigateTo(USERPAGE.toString());
            }
        });
    }

    private void addRegListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.registerAdvert();
                } catch (Exception e) {
                    Notification.show(failedUpload);
                    e.printStackTrace();
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

    private void addLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setAdvertRegView(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
        } catch (Exception ex) {
            Logger.getLogger(AdvertRegView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Button getBtnRegister() {
        return btnRegister;
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

    public void setDropHere(String dropHere) {
        this.dropHere = dropHere;
    }

    public Panel getAdverRegPanel() {
        return adverRegPanel;
    }

    public Label getLblAdvertDetails() {
        return lblAdvertDetails;
    }

    public Label getLblPictureUpload() {
        return labelPictureUpload;
    }
    
    public Button getRemoveBtn() {
        return removeBtn;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public void setFailedUpload(String failedUpload) {
        this.failedUpload = failedUpload;
    }

    public void setSuccessUpload(String successUpload) {
        this.successUpload = successUpload;
    }

    public Panel getPicturePanel() {
        return picturePanel;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setRemoveButtonText(String removeButtonText) {
        this.removeButtonText = removeButtonText;
    }
    
}
