package com.mycompany.advertisementproject.view.layouts;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.AUTHORIZATIONLEVEL;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.StyleNames.*;
import static com.mycompany.advertisementproject.enumz.Views.*;
import com.mycompany.advertisementproject.toolz.XmlFileReader;
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

    public static SimpleDateFormat formattedDate;
    public static String currency;

    private String btnAdvertsCaption;
    private String btnLoginCaption;
    private String btnRegistrationCaption;
    private String btnAdvertRegCaption;
    private String btnMyAccoutCaption;
    private String btnLogoutCaption;
    private String bannerHeight;
    private String dateformat;

    private static Button btnAdverts;
    private static Button btnLogin;
    private static Button btnRegistration;
    private static Button btnAdvertReg;
    private static Button btnMyAccout;
    private static Button btnLogout;

    private VerticalLayout header = new VerticalLayout();

    private String nodePath;
    private XmlFileReader xmlReader;

    public AppLayout() {
        addLabelText();
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
        addNavButtons();

        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addComponent(btnAdverts);
        navigation.addComponent(btnLogin);
        navigation.addComponent(btnRegistration);
        navigation.addComponent(btnAdvertReg);
        navigation.addComponent(btnMyAccout);
        navigation.addComponent(btnLogout);
        navigation.setHeightUndefined();

        header.addComponent(navigation);
        header.setComponentAlignment(navigation, Alignment.TOP_CENTER);
    }

    private void addNavButtons() {
        btnAdverts = new Button(btnAdvertsCaption);
        btnAdverts.setStyleName(NAVBUTTON.toString());
        btnLogin = new Button(btnLoginCaption);
        btnLogin.setStyleName(NAVBUTTON.toString());
        btnRegistration = new Button(btnRegistrationCaption);
        btnRegistration.setStyleName(NAVBUTTON.toString());
        btnAdvertReg = new Button(btnAdvertRegCaption);
        btnAdvertReg.setStyleName(NAVBUTTON.toString());
        btnAdvertReg.setVisible(false);
        btnMyAccout = new Button(btnMyAccoutCaption);
        btnMyAccout.setStyleName(NAVBUTTON.toString());
        btnMyAccout.setVisible(false);
        btnLogout = new Button(btnLogoutCaption);
        btnLogout.setStyleName(NAVBUTTON.toString());
        btnLogout.setVisible(false);
    }

    private void addBanner() {
        Panel bannerPanel = new Panel();
        bannerPanel.setHeight(bannerHeight);
        bannerPanel.setStyleName(BANNERPANEL.toString());

        VerticalLayout banner = new VerticalLayout();
        banner.addComponent(bannerPanel);
        banner.setHeightUndefined();

        header.addComponent(banner);
        header.setComponentAlignment(banner, Alignment.TOP_CENTER);
    }

    private void addListeners() {
        addBtnAdvertListener();
        addBtnLoginListener();
        addBtnRegistrationListener();
        addBtnAdvertRegListener();
        addBtnMyAccountListener();
        addBtnLogoutListener();
    }

    private void addBtnAdvertListener() {
        btnAdverts.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTS.toString();
                onMenuClick(nodePath);
            }
        });
    }

    private void addBtnLoginListener() {
        btnLogin.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = LOGIN.toString();
                onMenuClick(nodePath);
            }
        });
    }

    private void addBtnRegistrationListener() {
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = REGISTRATION.toString();
                onMenuClick(nodePath);
            }
        });
    }

    private void addBtnAdvertRegListener() {
        btnAdvertReg.addClickListener(new Button.ClickListener() {

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
    }

    private void addBtnMyAccountListener() {
        btnMyAccout.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = USERPAGE.toString();
                onMenuClick(nodePath);
            }
        });

    }

    private void addBtnLogoutListener() {
        btnLogout.addClickListener(new Button.ClickListener() {
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
        btnLogin.setVisible(true);
        btnRegistration.setVisible(true);
        btnAdvertReg.setVisible(false);
        btnMyAccout.setVisible(false);
        btnLogout.setVisible(false);
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
        btnLogin.setVisible(false);
        btnRegistration.setVisible(false);
        btnAdvertReg.setVisible(true);
        btnMyAccout.setVisible(true);
        btnLogout.setVisible(true);
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

    public void setBtnAdvertsCaption(String btnAdvertsCaption) {
        this.btnAdvertsCaption = btnAdvertsCaption;
    }

    public void setBtnRegistrationCaption(String btnRegistrationCaption) {
        this.btnRegistrationCaption = btnRegistrationCaption;
    }

    public void setBtnAdvertRegCaption(String btnAdvertRegCaption) {
        this.btnAdvertRegCaption = btnAdvertRegCaption;
    }

    public void setBtnMyAccoutCaption(String btnMyAccoutCaption) {
        this.btnMyAccoutCaption = btnMyAccoutCaption;
    }

    public void setBtnLogoutCaption(String btnLogoutCaption) {
        this.btnLogoutCaption = btnLogoutCaption;
    }

    public void setBannerHeight(String bannerHeight) {
        this.bannerHeight = bannerHeight;
    }

    public static void setBtnAdvertReg(Button btnAdvertReg) {
        AppLayout.btnAdvertReg = btnAdvertReg;
    }

    public static void setCurrency(String currency) {
        AppLayout.currency = currency;
    }

    public void setBtnLoginCaption(String btnLoginCaption) {
        this.btnLoginCaption = btnLoginCaption;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
        formattedDate = new SimpleDateFormat(dateformat);
    }
}
