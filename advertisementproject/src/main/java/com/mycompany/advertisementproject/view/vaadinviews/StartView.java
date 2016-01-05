package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.enumz.Views;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("START")
public class StartView extends VerticalLayout implements View {

    @Inject
    AdvertisementFacade advertisementFacade;

    @PostConstruct
    public void initComponent() {
        buildView();
        addListeners();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    private Button btnSearch;
    private TextField txtFldSearch;

    public void buildView() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setMargin(true);
        txtFldSearch = new TextField();
        btnSearch = new Button("Keresés");
        hl.addComponent(txtFldSearch);
        hl.addComponent(btnSearch);
        this.addComponent(hl);
        this.setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
    }

    private void addListeners() {
        btnSearch.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(Views.ADVERTS.toString());
            }
        });
    }
}
