package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.entities.*;
import com.mycompany.advertisementproject.facades.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
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
    private TextField txtFldPrice;
    private TextField txtFldPostalCode;
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
        cmbbxSubCategory = new ComboBox("Alkategória");
        cmbbxAdvertType = new ComboBox("Hirdetés típusa");
        cmbbxAdvertState = new ComboBox("Állapot");
        txtFldPrice = new TextField("Ár");
        txtFldPostalCode = new TextField("Irányítószám");
        btnRegister = new Button("Feladás");

        formLayout.addComponent(txtFieldTitle);
        formLayout.addComponent(txtAreaDescription);
        formLayout.addComponent(cmbbxCategory);
        formLayout.addComponent(cmbbxSubCategory);
        formLayout.addComponent(cmbbxAdvertType);
        formLayout.addComponent(cmbbxAdvertState);
        formLayout.addComponent(txtFldPrice);
        formLayout.addComponent(txtFldPostalCode);
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
        Advertisement advertisement = new Advertisement();

        advertisement.setAdvertStateId(selectedState());
        advertisement.setAdvertTypeId(selectedType());
        advertisement.setAdvertiserId(null);
        advertisement.setDescription(txtAreaDescription.getValue());
        advertisement.setLocalityId(selectedLocality());
        advertisement.setMainCategoryId(selectedMainCategory());
        advertisement.setPrice(Integer.valueOf(txtFldPrice.getValue()));
        advertisement.setRegistrationDate(currentDate());
        advertisement.setTitle(txtFieldTitle.getValue());

        advertisementFacade.create(advertisement);

        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advertisement);
            pictureCollection.add(picture);
            pictureFacade.create(picture);
        }

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
            if (locality.getPostalCode().equals(txtFldPostalCode.getValue())) {
                return locality;
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
            } else {
                return category;
            }
        }
        return null;
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
            for (Subcategory s : subcategoryFacade.findAll()) {
                cmbbxSubCategory.addItem(s.getName());
            }
            filled = true;
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
