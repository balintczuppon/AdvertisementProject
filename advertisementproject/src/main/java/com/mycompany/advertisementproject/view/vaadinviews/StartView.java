package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.enumz.Views;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.mycompany.advertisementproject.view.UIs.RootUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

@CDIView("")
public class StartView extends VerticalLayout implements View {

    private Button btnSearch;
    private Button btnForward;
    private TextField txtFldSearch;
    private AdvertListView advListView;
    private I18Helper i18Helper;

    @PostConstruct
    public void initComponent() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        buildView();
        addListeners();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    public void buildView() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setMargin(true);
        txtFldSearch = new TextField();
        btnSearch = new Button(i18Helper.getMessage("Search"));
        btnForward = new Button(i18Helper.getMessage("Start.Forward"));
        hl.addComponent(txtFldSearch);
        hl.addComponent(btnSearch);
        hl.addComponent(btnForward);
        this.addComponent(hl);
        this.setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
    }

    private void addListeners() {
        btnSearch.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                getUI().getNavigator().navigateTo(Views.ADVERTS.toString());
                try {
                    advListView = (AdvertListView) RootUI.getCurrent().getViewProvider().getView("ADVERTS");
                    advListView.getController().searchAdverts(txtFldSearch.getValue());
                } catch (Exception ex) {
                    Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnForward.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(Views.ADVERTS.toString());
            }
        });
    }
}
