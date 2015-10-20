package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.facades.LocalityFacade;
import com.mycompany.advertisementproject.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.facades.PictureFacade;
import com.mycompany.advertisementproject.facades.SubcategoryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("USERPAGE")
public class AccountView extends VerticalLayout implements View {

    private List<Advertisement> myadverts = new ArrayList<>();

    private TabSheet tabsheet = new TabSheet();

    private VerticalLayout advertLayout;
    private VerticalLayout postBoxLayout;
    private VerticalLayout settingLayout;

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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        buildTabs();
        buildView();
        showContent();
    }

    private void buildView() {
        this.setSizeFull();
        this.addComponent(tabsheet);
        this.setComponentAlignment(tabsheet, Alignment.TOP_CENTER);
    }

    private void buildTabs() {
        tabsheet = new TabSheet();
        tabsheet.setWidthUndefined();
        tabsheet.addTab(addMyAdverts()).setCaption("Hirdetéseim");
        tabsheet.addTab(addPostBox()).setCaption("Postaláda");
        tabsheet.addTab(addSettings()).setCaption("Beállítások");
    }

    private VerticalLayout addMyAdverts() {
        advertLayout = new VerticalLayout();

        return advertLayout;
    }

    private VerticalLayout addPostBox() {
        postBoxLayout = new VerticalLayout();

        return postBoxLayout;

    }

    private VerticalLayout addSettings() {
        settingLayout = new VerticalLayout();

        return settingLayout;
    }

    private void showContent() {
    }

}
