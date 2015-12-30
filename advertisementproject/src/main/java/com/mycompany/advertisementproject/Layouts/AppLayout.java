package com.mycompany.advertisementproject.Layouts;

import static com.mycompany.advertisementproject.Enums.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.Enums.SessionAttributes.AUTHORIZATIONLEVEL;
import static com.mycompany.advertisementproject.Enums.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.Enums.StyleNames.*;
import static com.mycompany.advertisementproject.Enums.Views.*;
import com.mycompany.advertisementproject.Tools.XmlFileReader;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLayout extends VerticalLayout implements ViewDisplay {

    public static final String fileSource = "/Users/balin/data.xml";
    public static final SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy MMMM dd");
    public static final String currency = "Huf";

    private static Button btnNav1;
    private static Button btnNav2;
    private static Button btnNav3;
    private static Button btnNav4;
    private static Button btnNav5;
    private static Button btnNav6;

    private VerticalLayout header = new VerticalLayout();

    private String nodePath;
    private XmlFileReader xmlReader;

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
        btnNav4.setVisible(false);
        btnNav5 = new Button("Fiókom");
        btnNav5.setStyleName(NAVBUTTON.toString());
        btnNav5.setVisible(false);
        btnNav6 = new Button("Kilépés");
        btnNav6.setStyleName(NAVBUTTON.toString());
        btnNav6.setVisible(false);

        HorizontalLayout navigation = new HorizontalLayout();

        navigation.addComponent(btnNav1);
        navigation.addComponent(btnNav2);
        navigation.addComponent(btnNav3);
        navigation.addComponent(btnNav4);
        navigation.addComponent(btnNav5);
        navigation.addComponent(btnNav6);

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
                try {
                    VaadinSession.getCurrent().getLockInstance().lock();
                    VaadinSession.getCurrent().setAttribute(ADVERTTOMODIFY.toString(), null);
                } finally {
                    VaadinSession.getCurrent().getLockInstance().unlock();
                }
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
        btnNav6.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout();
                nodePath = ADVERTS.toString();
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

    private void logout() {
        btnNav2.setVisible(true);
        btnNav3.setVisible(true);
        btnNav4.setVisible(false);
        btnNav5.setVisible(false);
        btnNav6.setVisible(false);
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(CURRENTUSER.toString(), null);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(AUTHORIZATIONLEVEL.toString(), null);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    public static void login() {
        btnNav2.setVisible(false);
        btnNav3.setVisible(false);
        btnNav4.setVisible(true);
        btnNav5.setVisible(true);
        btnNav6.setVisible(true);
    }

    public static java.sql.Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    private void addLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setAppLayout(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
        } catch (Exception ex) {
            Logger.getLogger(AppLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
