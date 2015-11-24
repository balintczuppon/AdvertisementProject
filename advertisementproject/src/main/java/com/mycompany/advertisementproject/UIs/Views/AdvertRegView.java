package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Tools.MyMultiFileUpload;
import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
import com.mycompany.advertisementproject.entities.*;
import com.mycompany.advertisementproject.facades.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.easyuploads.FileBuffer;

@CDIView("ADVERTREG")
public class AdvertRegView extends VerticalLayout implements View {

    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();

    private List<File> files = new ArrayList<>();
    private List<Picture> pictureCollection = new ArrayList<>();

    private FormLayout regFormLayout;

    private Panel adverRegPanel;
    private Panel picturePanel;

    @Inject
    MaincategoryFacade maincategoryFacade;
    @Inject
    SubcategoryFacade subcategoryFacade;
    @Inject
    PictureFacade pictureFacade;
    @Inject
    AdverttypeFacade adverttypeFacade;
    @Inject
    AdvertisementFacade advertisementFacade;
    @Inject
    AdvertstateFacade advertstateFacade;
    @Inject
    CountryFacade countryFacade;
    @Inject
    CityFacade cityFacade;

    private Picture picture;

    private boolean filled = false;

    private TextField txtFieldTitle;
    private TextArea txtAreaDescription;
    private ComboBox cmbbxCategory;
    private ComboBox cmbbxSubCategory;
    private ComboBox cmbbxAdvertType;
    private ComboBox cmbbxAdvertState;
    private ComboBox cmbbxCountry;
    private ComboBox cmbbxCity;

    private TextField txtFldPrice;
    private MyMultiFileUpload mfu;

    private String btnText;
    private Button btnRegister;

    private VerticalLayout pictureLayout;
    private VerticalLayout advertDataLayout;

    @PostConstruct
    public void initComponent() {
        addPictureUpload();
        addForm();
        fillComboBoxes();
    }

    public void addForm() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        regFormLayout = new FormLayout();
        regFormLayout.setSpacing(true);
        regFormLayout.setMargin(true);

        txtFieldTitle = new TextField();
        txtFieldTitle.setWidth("500");
        txtFieldTitle.setInputPrompt("Hirdetés címe");

        txtAreaDescription = new TextArea();
        txtAreaDescription.setWidth("500");
        txtAreaDescription.setHeight("200");
        txtAreaDescription.setInputPrompt("Hirdetés leírása");

        cmbbxCategory = new ComboBox();
        cmbbxCategory.setInputPrompt("Kategória");
        cmbbxCategory.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbbxCategory.getValue() != null) {
                    fillCmbBxSubCategory(cmbbxCategory.getValue());
                } else {
                    cmbbxSubCategory.setEnabled(false);
                }
            }
        });
        cmbbxSubCategory = new ComboBox();
        cmbbxSubCategory.setInputPrompt("Alkategória");
        cmbbxSubCategory.setEnabled(false);
        cmbbxAdvertType = new ComboBox();
        cmbbxAdvertType.setInputPrompt("Hirdetés típusa");
        cmbbxAdvertState = new ComboBox();
        cmbbxAdvertState.setInputPrompt("Állapot");
        txtFldPrice = new TextField();
        txtFldPrice.setInputPrompt("Ár");
        cmbbxCountry = new ComboBox();
        cmbbxCountry.setInputPrompt("Megye");
        cmbbxCountry.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbbxCountry.getValue() != null) {
                    fillCmbBxCity(cmbbxCountry.getValue());
                } else {
                    cmbbxCity.setEnabled(false);
                }
            }
        });
        cmbbxCity = new ComboBox();
        cmbbxCity.setInputPrompt("Város");
        cmbbxCity.setEnabled(false);

        btnRegister = new Button();

        regFormLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

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

        adverRegPanel = new Panel();
        adverRegPanel.setHeightUndefined();
        adverRegPanel.setWidth("600");

        advertDataLayout = new VerticalLayout();
        advertDataLayout.setSpacing(true);
        advertDataLayout.setMargin(true);
        advertDataLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        advertDataLayout.addComponent(new Label("Hirdetés adatainak megadása"));
        advertDataLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        advertDataLayout.addComponent(regFormLayout);
        advertDataLayout.addComponent(btnRegister);

        adverRegPanel.setContent(advertDataLayout);

        addComponent(adverRegPanel);
        setComponentAlignment(adverRegPanel, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
        getUI().focus();
        checkSessionAttribute();
    }

    private Advertisement handleAdvert() {
        Advertiser advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        Advertisement advertisement = new Advertisement();

        advertisement.setAdvertiserId(advertiser);
        advertisement.setAdvertStateId(selectedState());
        advertisement.setAdvertTypeId(selectedType());
        advertisement.setDescription(txtAreaDescription.getValue());
        advertisement.setCityId(selectedCity());
        advertisement.setCountryId(selectedCountry());
        advertisement.setMainCategoryId(selectedMainCategory());
        advertisement.setSubCategoryId(selectedSubCategory());
        advertisement.setPrice(Integer.valueOf(txtFldPrice.getValue()));
        advertisement.setRegistrationDate(currentDate());
        advertisement.setTitle(txtFieldTitle.getValue());

        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advertisement);
            pictureCollection.add(picture);
        }
        advertisement.setPictureCollection(pictureCollection);
        return advertisement;
    }

    private Advertstate selectedState() {
        List<Advertstate> states = advertstateFacade.findAll();
        for (Advertstate state : states) {
            if (state.getName().equals(cmbbxAdvertState.getValue())) {
                return state;
            }
        }
        return null;
    }

    private Adverttype selectedType() {
        List<Adverttype> types = adverttypeFacade.findAll();
        for (Adverttype type : types) {
            if (type.getName().equals(cmbbxAdvertType.getValue())) {
                return type;
            }
        }
        return null;
    }

    private Country selectedCountry() {
        List<Country> countries = countryFacade.findAll();
        for (Country country : countries) {
            if (country.getCountryName().equals(cmbbxCountry.getValue())) {
                return country;
            }
        }
        return null;
    }

    private City selectedCity() {
        List<City> cities = cityFacade.findAll();
        for (City city : cities) {
            if (city.getCityName().equals(cmbbxCity.getValue())) {
                return city;
            }
        }
        return null;
    }

    private Maincategory selectedMainCategory() {
        List<Maincategory> categoires = maincategoryFacade.findAll();
        for (Maincategory category : categoires) {
            if (category.getName().equals(cmbbxCategory.getValue())) {
                return category;
            }
        }
        return null;
    }

    private Subcategory selectedSubCategory() {
        List<Subcategory> categoires = subcategoryFacade.findAll();
        for (Subcategory category : categoires) {
            if (category.getName().equals(cmbbxSubCategory.getValue())) {
                return category;
            }
        }
        return null;
    }

    private Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        return sqlDate;
    }

    private void fillComboBoxes() {
        if (!filled) {
            for (Maincategory m : maincategoryFacade.findAll()) {
                cmbbxCategory.addItem(m.getName());
            }
            for (Advertstate a : advertstateFacade.findAll()) {
                cmbbxAdvertState.addItem(a.getName());
            }
            for (Adverttype a : adverttypeFacade.findAll()) {
                cmbbxAdvertType.addItem(a.getName());
            }
            for (Country c : countryFacade.findAll()) {
                cmbbxCountry.addItem(c.getCountryName());
            }
            filled = true;
        }
    }

    private void fillCmbBxSubCategory(Object value) {
        cmbbxSubCategory.removeAllItems();
        cmbbxSubCategory.setEnabled(true);
        for (Maincategory mcat : maincategoryFacade.findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : mcat.getSubcategoryCollection()) {
                    cmbbxSubCategory.addItem(s.getName());
                }
            }
        }
    }

    private void fillCmbBxCity(Object value) {
        cmbbxCity.removeAllItems();
        cmbbxCity.setEnabled(true);
        for (City c : cityFacade.findAll()) {
            if (c.getCountryId().equals(value)) {
                cmbbxCity.addItem(c.getCityName());
            }
        }
    }

    private Button removeBtn;
    private HorizontalLayout innerPictureLayout;

    private void setUpLoadField() {
        mfu = new MyMultiFileUpload() {
            @Override
            protected void handleFile(final File file, String fileName, String mimeType, long length) {
                files.add(file);
                showImage(file);
            }

            @Override
            protected FileBuffer createReceiver() {
                FileBuffer receiver = super.createReceiver();
                receiver.setDeleteFiles(false);
                return receiver;
            }
        };
        mfu.setRootDirectory(TEMP_FILE_DIR);
        mfu.setCaption("Dobd ide a képet!");
    }

    private void showImage(File file) {
        final Embedded image = new Embedded();
        image.setHeight("96");
        image.setWidth("128");
        image.setSource(new FileResource(file));

        innerPictureLayout = new HorizontalLayout();
        innerPictureLayout.setSpacing(true);

        innerPictureLayout.addComponent(image);
        removeBtn = new Button("X");
        addRemoveButtonToPicture(file, image);

        innerPictureLayout.addComponent(removeBtn);
        innerPictureLayout.setComponentAlignment(removeBtn, Alignment.MIDDLE_LEFT);
        pictureLayout.addComponent(innerPictureLayout);
    }

    private void addRemoveButtonToPicture(final File file, final Embedded image) {
        removeBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                file.delete();
                files.remove(file);
                innerPictureLayout.removeComponent(image);
                innerPictureLayout.removeComponent(removeBtn);
                pictureLayout.removeComponent(innerPictureLayout);
            }
        });
    }

    private void addPictureUpload() {
        picturePanel = new Panel();
        picturePanel.setWidth("600");

        pictureLayout = new VerticalLayout();
        pictureLayout.setSizeFull();
        setUpLoadField();
        pictureLayout.setSpacing(true);
        pictureLayout.setMargin(true);
        mfu.setRootDirectory(TEMP_FILE_DIR);
        pictureLayout.addComponent(new Label("Képek feltöltése"));
        pictureLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        pictureLayout.addComponent(mfu);
        pictureLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        picturePanel.setContent(pictureLayout);

        addComponent(picturePanel);
        setComponentAlignment(picturePanel, Alignment.TOP_CENTER);
    }

    private void checkSessionAttribute() {
        try {
            Advertisement advertisement = (Advertisement) VaadinSession.getCurrent().getAttribute("AdvertToModify");
            if (advertisement != null) {
                prepareForModification(advertisement);
            } else {
                prepareForRegistration();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void prepareForModification(Advertisement ad) {
        btnText = "Módosít";
        btnRegister.setCaption(btnText);
        linkDataToFields(ad);
        addModListener();
    }

    private void prepareForRegistration() {
        btnText = "Regisztrál";
        btnRegister.setCaption(btnText);
        addRegListener();
    }

    private void addModListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                modifyAdvert(handleAdvert());
                getUI().getNavigator().navigateTo(USERPAGE.toString());
            }
        });
    }

    private void addRegListener() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                registerAdvert(handleAdvert());
            }
        });
    }

    private void linkDataToFields(Advertisement ad) {
        txtFieldTitle.setValue(ad.getTitle());
        txtAreaDescription.setValue(ad.getDescription());
        txtFldPrice.setValue(String.valueOf(ad.getPrice()));
        cmbbxCategory.select(ad.getMainCategoryId().getName());

        fillCmbBxSubCategory(ad.getMainCategoryId().getName());
        for (Subcategory s : ad.getMainCategoryId().getSubcategoryCollection()) {
            if (s.getId().equals(ad.getSubCategoryId())) {
                cmbbxSubCategory.select(s.getName());
            }
        }
        cmbbxSubCategory.select(ad.getSubCategoryId());
        cmbbxAdvertType.select(ad.getAdvertTypeId().getName());
        cmbbxAdvertState.select(ad.getAdvertStateId().getName());
        cmbbxCountry.select(ad.getCountryId().getCountryName());

        cmbbxCity.select(ad.getCityId().getCityName());
        fillCmbBxCity(ad.getCountryId().getCountryName());
        for (City c : ad.getCountryId().getCityCollection()) {
            if (c.getId().equals(ad.getCityId())) {
                cmbbxSubCategory.select(c.getCityName());
            }
        }
        for (Picture p : ad.getPictureCollection()) {
            File file = new File(p.getAccessPath());
            files.add(file);
            showImage(file);
        }
    }

    private void registerAdvert(Advertisement a) {
        advertisementFacade.create(a);
    }

    private void modifyAdvert(Advertisement a) {
        Advertisement ad = (Advertisement) VaadinSession.getCurrent().getAttribute("AdvertToModify");
        advertisementFacade.edit(a);
        advertisementFacade.remove(ad);
    }
}
