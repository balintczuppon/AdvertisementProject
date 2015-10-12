package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.facades.LocalityFacade;
import com.mycompany.advertisementproject.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.facades.PictureFacade;
import com.mycompany.advertisementproject.facades.SubcategoryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
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

    private TextField txtFieldTitle;
    private TextArea txtAreaDescription;
    private ComboBox cmbbxCategory;
    private ComboBox cmbbxSubCategory;
    private ComboBox cmbbxAdvertType;
    private ComboBox cmbbxAdvertState;
    private TextField txtFldPrice;
    private TextField txtFldPostalCode;
    private Label lblPicture;
    private MultiFileUpload mfu;
    private Button btnRegister;
    private Button btnDelPicture;

    private VerticalLayout pictureLayout;

    @PostConstruct
    public void initComponent() {
        addForm();
        addPictureLayout();
        addListeners();
    }

    public void addForm() {
        setSizeFull();
        FormLayout formLayout = new FormLayout();

        txtFieldTitle = new TextField("Hirdetés címe");
        txtAreaDescription = new TextArea("Hirdetés leírása");
        cmbbxCategory = new ComboBox("Kategória");
        cmbbxSubCategory = new ComboBox("Alkategória");
        cmbbxAdvertType = new ComboBox("Hirdetés típusa");
        cmbbxAdvertState = new ComboBox("Állapot");
        txtFldPrice = new TextField("Ár");
        txtFldPostalCode = new TextField("Irányítószám");
        lblPicture = new Label("Kép feltöltése:");
        btnRegister = new Button("Feladás");

        formLayout.addComponent(txtFieldTitle);
        formLayout.addComponent(txtAreaDescription);
        formLayout.addComponent(cmbbxCategory);
        formLayout.addComponent(cmbbxSubCategory);
        formLayout.addComponent(cmbbxAdvertType);
        formLayout.addComponent(cmbbxAdvertState);
        formLayout.addComponent(txtFldPrice);
        formLayout.addComponent(txtFldPostalCode);
        formLayout.setWidthUndefined();

        addComponent(formLayout);
        setComponentAlignment(formLayout, Alignment.TOP_RIGHT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
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
