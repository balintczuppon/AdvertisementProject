package com.mycompany.advertisementproject.Layouts;

import static com.mycompany.advertisementproject.Enums.Views.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AppLayout extends VerticalLayout implements ViewDisplay, ViewChangeListener {

    private View view;

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout navigation = new HorizontalLayout();
    private VerticalLayout banner = new VerticalLayout();

    private String nodePath;
    private Button btnNav1;
    private Button btnNav2;
    private Button btnNav3;

    private Panel bannerPanel;

    private String bannerHeight = "120";

    public AppLayout() {
        super();
        buildNavigation();
        addBanner();
        addListeners();
    }

    @Override
    public void showView(View view) {
        this.view = view;
        showContent();
    }

    private void showContent() {
        content.removeAllComponents();
        content.addComponent((Component) view);
        addComponent(content);
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }

    private void buildNavigation() {
        btnNav1 = new Button("Home");
        btnNav1.setStyleName("navigationbutton");
        btnNav2 = new Button("Bejelentkezés");
        btnNav2.setStyleName("navigationbutton");
        btnNav3 = new Button("Regisztráció");
        btnNav3.setStyleName("navigationbutton");

        navigation.addComponent(btnNav1);
        navigation.addComponent(btnNav2);
        navigation.addComponent(btnNav3);

        navigation.setHeightUndefined();

        addComponent(navigation);
        this.setComponentAlignment(navigation, Alignment.TOP_CENTER);
    }

    private void addBanner() {
        bannerPanel = new Panel();
        bannerPanel.setSizeFull();
        bannerPanel.setHeight(bannerHeight);
        bannerPanel.setStyleName("bannerpanel");
        banner.addComponent(bannerPanel);
        addComponent(banner);
        this.setComponentAlignment(banner, Alignment.TOP_CENTER);
    }

    private void addListeners() {
        btnNav1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTS.toString();
                onMenuClick(nodePath);
            }
        });

        btnNav2.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = LOGIN.toString();
                onMenuClick(nodePath);
            }
        });

        btnNav3.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = REGISTRATION.toString();
                onMenuClick(nodePath);
            }
        });
    }

    private void onMenuClick(String nodePath) {
        UI.getCurrent().getNavigator().navigateTo(nodePath);
    }
}
