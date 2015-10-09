package com.mycompany.advertisementproject.Layouts;

import static com.mycompany.advertisementproject.Enums.StyleNames.*;
import static com.mycompany.advertisementproject.Enums.Views.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class AppLayout extends VerticalLayout implements ViewDisplay {

    private VerticalLayout header = new VerticalLayout();

    private String nodePath;

    private Button btnNav1;
    private Button btnNav2;
    private Button btnNav3;
    private Button btnNav4;
    private Button btnNav5;

    public AppLayout() {
        buildHeader();
        addListeners();
    }

    private void buildHeader() {
        addNavigation();
        addBanner();
        header.setHeightUndefined();
        addComponent(header);
    }

    private void addNavigation() {
        btnNav1 = new Button("Hirdetések");
        btnNav1.setStyleName(NAVBUTTON.toString());
        btnNav2 = new Button("Bejelentkezés");
        btnNav2.setStyleName(NAVBUTTON.toString());
        btnNav3 = new Button("Regisztráció");
        btnNav3.setStyleName(NAVBUTTON.toString());
        btnNav4 = new Button("Hirdetés feladás");
        btnNav4.setStyleName(NAVBUTTON.toString());
        btnNav5 = new Button("Fiókom");
        btnNav5.setStyleName(NAVBUTTON.toString());

        HorizontalLayout navigation = new HorizontalLayout();

        navigation.addComponent(btnNav1);
        navigation.addComponent(btnNav2);
        navigation.addComponent(btnNav3);
        navigation.addComponent(btnNav4);
        navigation.addComponent(btnNav5);

        navigation.setHeightUndefined();

        header.addComponent(navigation);
        header.setComponentAlignment(navigation, Alignment.TOP_CENTER);
    }

    private void addBanner() {
        Panel bannerPanel = new Panel();
        bannerPanel.setHeight("120");
        bannerPanel.setStyleName(BANNERPANEL.toString());

        VerticalLayout banner = new VerticalLayout();

        banner.addComponent(bannerPanel);
        banner.setHeightUndefined();

        header.addComponent(banner);
        header.setComponentAlignment(banner, Alignment.TOP_CENTER);
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
        btnNav4.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTREG.toString();
                onMenuClick(nodePath);
            }
        });
        btnNav5.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = USERPAGE.toString();
                onMenuClick(nodePath);
            }
        });
    }

    private void onMenuClick(String nodePath) {
        getUI().getNavigator().navigateTo(nodePath);
    }

    @Override
    public void showView(View view) {
    }
}
