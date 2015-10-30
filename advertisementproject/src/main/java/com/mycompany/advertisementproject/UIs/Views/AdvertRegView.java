package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.UIs.RootUI;
import com.mycompany.advertisementproject.entities.*;
import com.mycompany.advertisementproject.facades.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.easyuploads.FileBuffer;
import org.vaadin.easyuploads.MultiFileUpload;

@CDIView("ADVERTREG")
public class AdvertRegView extends HorizontalLayout implements View {

    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();

    private List<File> files = new ArrayList<>();
    private List<Picture> pictureCollection = new ArrayList<>();

    @Inject
    MaincategoryFacade maincategoryFacade;
    @Inject
    SubcategoryFacade subcategoryFacade;
    @Inject
    PictureFacade pictureFacade;
    @Inject
    AdverttypeFacade adverttypeFacade;
    @Inject
    LocalityFacade localityFacade;
    @Inject
    AdvertisementFacade advertisementFacade;
    @Inject
    AdvertstateFacade advertstateFacade;

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
    private MultiFileUpload mfu;
    private Button btnRegister;

    private VerticalLayout pictureLayout;

    @PostConstruct
    public void initComponent() {
        addForm();
        addPictureLayout();
        addListeners();
        fillComboBoxes();

    }

    public void addForm() {
        setSizeFull();
        FormLayout formLayout = new FormLayout();

        txtFieldTitle = new TextField("Hirdetés címe");
        txtAreaDescription = new TextArea("Hirdetés leírása");
        txtAreaDescription.setWidth("300");
        txtAreaDescription.setHeight("100");
        cmbbxCategory = new ComboBox("Kategória");
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
        cmbbxSubCategory = new ComboBox("Alkategória");
        cmbbxSubCategory.setEnabled(false);
        cmbbxAdvertType = new ComboBox("Hirdetés típusa");
        cmbbxAdvertState = new ComboBox("Állapot");
        txtFldPrice = new TextField("Ár");
        cmbbxCountry = new ComboBox("Megye");
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
        cmbbxCity = new ComboBox("Város");
        cmbbxCity.setEnabled(false);
        btnRegister = new Button("Feladás");

        formLayout.addComponent(txtFieldTitle);
        formLayout.addComponent(txtAreaDescription);
        formLayout.addComponent(cmbbxCategory);
        formLayout.addComponent(cmbbxSubCategory);
        formLayout.addComponent(cmbbxAdvertType);
        formLayout.addComponent(cmbbxAdvertState);
        formLayout.addComponent(txtFldPrice);
        formLayout.addComponent(cmbbxCountry);
        formLayout.addComponent(cmbbxCity);
        formLayout.addComponent(btnRegister);
        formLayout.setWidthUndefined();

        addComponent(formLayout);
        setComponentAlignment(formLayout, Alignment.TOP_RIGHT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
        getUI().focus();
    }

    private void addListeners() {
        btnRegister.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                registerAdvert();
            }
        });
    }

    private void registerAdvert() {
        Advertiser advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        Advertisement advertisement = new Advertisement();

        advertisement.setAdvertiserId(advertiser);
        advertisement.setAdvertStateId(selectedState());
        advertisement.setAdvertTypeId(selectedType());
        advertisement.setDescription(txtAreaDescription.getValue());
        advertisement.setLocalityId(selectedLocality());
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
        advertisementFacade.create(advertisement);
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

    private Locality selectedLocality() {
        List<Locality> localites = localityFacade.findAll();
        for (Locality locality : localites) {
            if (locality.getCountry().equals(cmbbxCountry.getValue())) {
                if (locality.getStationname().equals(cmbbxCity.getValue())) {
                    return locality;
                }
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

    private int selectedSubCategory() {
        List<Subcategory> categoires = subcategoryFacade.findAll();
        for (Subcategory category : categoires) {
            if (category.getName().equals(cmbbxSubCategory.getValue())) {
                return category.getId();
            }
        }
        return 0;
    }

    private Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
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
            for (Locality l : localityFacade.findAll()) {
                cmbbxCountry.addItem(l.getCountry());
            }
            filled = true;
        }
    }

    private void fillCmbBxSubCategory(Object value) {
        cmbbxSubCategory.removeAllItems();
        cmbbxSubCategory.setEnabled(true);
        for (Maincategory mcat : maincategoryFacade.findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : subcategoryFacade.findByMainCategoryId(mcat)) {
                    cmbbxSubCategory.addItem(s.getName());
                }
            }
        }
    }

    private void fillCmbBxCity(Object value) {
        cmbbxCity.removeAllItems();
        cmbbxCity.setEnabled(true);
        for (Locality loc : localityFacade.findAll()) {
            if (loc.getCountry().equals(value)) {
                cmbbxCity.addItem(loc.getStationname());
            }
        }
    }

    private void setUpLoadField() {
        mfu = new MultiFileUpload() {
            @Override
            protected void handleFile(final File file, String fileName, String mimeType, long length) {
                files.add(file);

                final HorizontalLayout innerPictureLayout = new HorizontalLayout();
                innerPictureLayout.setSpacing(true);

                final Embedded image = new Embedded();
                image.setHeight("100");
                image.setWidth("100");
                image.setSource(new FileResource(file));

                innerPictureLayout.addComponent(image);

                final Button btn = new Button("X");
                btn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        file.delete();
                        files.remove(file);
                        innerPictureLayout.removeComponent(image);
                        innerPictureLayout.removeComponent(btn);
                        pictureLayout.removeComponent(innerPictureLayout);
                    }
                });
                innerPictureLayout.addComponent(btn);
                innerPictureLayout.setComponentAlignment(btn, Alignment.MIDDLE_LEFT);
                pictureLayout.addComponent(innerPictureLayout);
            }

            @Override
            protected FileBuffer createReceiver() {
                FileBuffer receiver = super.createReceiver();
                receiver.setDeleteFiles(false);
                return receiver;
            }
        };
    }

    private void addPictureLayout() {
        pictureLayout = new VerticalLayout();
        setUpLoadField();
        pictureLayout.setSpacing(true);
        pictureLayout.setMargin(true);
        mfu.setRootDirectory(TEMP_FILE_DIR);
        mfu.setCaption("Képek feltöltése");
        pictureLayout.addComponent(mfu);
        addComponent(pictureLayout);
        setComponentAlignment(pictureLayout, Alignment.TOP_LEFT);
    }
}
