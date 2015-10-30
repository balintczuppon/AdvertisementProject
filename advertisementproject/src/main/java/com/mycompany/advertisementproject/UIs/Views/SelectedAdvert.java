package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("SELECTED")
public class SelectedAdvert extends HorizontalLayout implements View {

    private static Advertisement advertisement;

    private VerticalLayout vl;

    private Panel advertPanel;

    private List<Picture> pictures;

    private Embedded mainImage;

    private File file;
    private Label lblTitle;
    private Label lblAdvertiser;
    private Label lblRegDate;
    private Label lblMainPicture;
    private Label lblPrice;
    private TextArea txtAreaDescription;
    private Label lblConnection;
    private Label lblAdvertiserPhoneNumber;
    private TextField txtFldName;
    private TextField txtFldEmail;
    private TextField txtFldCustomerPhoneNumber;
    private TextArea txtAreaMessage;
    private Button btnSendMessage;

    @Inject
    AdvertisementFacade advertisementFacade;

    @PostConstruct
    public void initComponent() {
        buildView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    private void buildView() {
        advertPanel = new Panel();
        advertPanel.setWidth("1000");
        advertPanel.setHeightUndefined();

        this.setSizeFull();
        vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setSpacing(true);
        vl.setMargin(true);

        lblTitle = new Label(advertisement.getTitle());
        lblTitle.setWidthUndefined();
        vl.addComponent(lblTitle);
        vl.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);

        HorizontalLayout metaVl = new HorizontalLayout();
//        metaVl.setWidthUndefined();
        lblAdvertiser = new Label(advertisement.getAdvertiserId().getName() + "");
        metaVl.addComponent(lblAdvertiser);
        lblRegDate = new Label(advertisement.getRegistrationDate() + "");
        metaVl.addComponent(lblRegDate);
        vl.addComponent(metaVl);
        vl.setComponentAlignment(metaVl, Alignment.TOP_CENTER);

        pictures = (List<Picture>) advertisement.getPictureCollection();
        if (!pictures.isEmpty()) {
            file = new File(pictures.get(0).getAccessPath());
            FileResource source = new FileResource(file);
            final Embedded img = new Embedded();
            img.setSource(source);
            loadMainImage(img);
            vl.addComponent(mainImage);
            vl.setComponentAlignment(mainImage, Alignment.TOP_CENTER);
        }

        HorizontalLayout hlPictures = new HorizontalLayout();
        hlPictures.setSpacing(true);
        if (!pictures.isEmpty()) {
            for (int i = 0; i < pictures.size(); i++) {
                file = new File(pictures.get(i).getAccessPath());
                final Embedded image = new Embedded();
                image.setWidth("128");
                image.setHeight("96");
                image.setSource(new FileResource(file));
                hlPictures.addComponent(image);
                image.addClickListener(new MouseEvents.ClickListener() {
                    @Override
                    public void click(MouseEvents.ClickEvent event) {
                        setAsMainImage(image);
                    }
                });
            }
        }
        vl.addComponent(hlPictures);
        vl.setComponentAlignment(hlPictures, Alignment.TOP_CENTER);

        advertPanel.setContent(vl);
        this.addComponent(advertPanel);
        this.setComponentAlignment(advertPanel, Alignment.TOP_CENTER);
        this.setSpacing(true);
        this.setMargin(true);
    }

    private void setAsMainImage(Embedded image) {
        mainImage.setSource(image.getSource());
        loadMainImage(mainImage);
    }

    private void loadMainImage(Embedded image) {
        if (!pictures.isEmpty()) {
            mainImage = image;
            mainImage.setWidth("640");
            mainImage.setHeight("480");
        }
    }

    public static void setAdvertisement(Advertisement Advertisement) {
        SelectedAdvert.advertisement = Advertisement;
    }
}
