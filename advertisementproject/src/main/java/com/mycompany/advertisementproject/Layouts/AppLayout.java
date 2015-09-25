package com.mycompany.advertisementproject.Layouts;

import com.mycompany.advertisementproject.Enums.StyleNames;
import static com.mycompany.advertisementproject.Enums.StyleNames.BANNERPANEL;
import static com.mycompany.advertisementproject.Enums.StyleNames.NAVBUTTON;
import static com.mycompany.advertisementproject.Enums.Views.ADVERTREG;
import static com.mycompany.advertisementproject.Enums.Views.ADVERTS;
import static com.mycompany.advertisementproject.Enums.Views.LOGIN;
import static com.mycompany.advertisementproject.Enums.Views.REGISTRATION;
import static com.mycompany.advertisementproject.Enums.Views.USERPAGE;
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
    private VerticalLayout header = new VerticalLayout();

    private HorizontalLayout navigation = new HorizontalLayout();
    private VerticalLayout banner = new VerticalLayout();

    private String nodePath;

    private Button btnNav1;
    private Button btnNav2;
    private Button btnNav3;
    private Button btnNav4;
    private Button btnNav5;

    private Panel bannerPanel;

    private String bannerHeight = "120";

    public AppLayout() {
        super();
        buildHeader();
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
        content.setSizeFull();
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
    }

    private void buildHeader() {
        addNavigation();
        addBanner();
        header.setHeightUndefined();
        this.addComponent(header);
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
        bannerPanel = new Panel();
        bannerPanel.setHeight(bannerHeight);
        bannerPanel.setStyleName(BANNERPANEL.toString());

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
        UI.getCurrent().getNavigator().navigateTo(nodePath);
    }

    public Button getBtnNav1() {
        return btnNav1;
    }

    public Button getBtnNav2() {
        return btnNav2;
    }

    public Button getBtnNav3() {
        return btnNav3;
    }
}
