package com.mycompany.advertisementproject.view.layouts;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTLOCALE;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.StyleNames.*;
import static com.mycompany.advertisementproject.enumz.Views.*;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.mycompany.advertisementproject.view.vaadinviews.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppLayout extends VerticalLayout implements ViewDisplay {

    private I18Helper i18Helper;

    public static SimpleDateFormat formattedDate = Global.DATEFORMAT;

    private static Button btnAdverts;
    private static Button btnLogin;
    private static Button btnRegistration;
    private static Button btnAdvertReg;
    private static Button btnMyAccout;
    private static Button btnLogout;
    private static Button btnAdminAccount;

    private Button btnHun;
    private Button btnEng;

    private VerticalLayout header;

    private String nodePath;

    public AppLayout() {
        super();
        localeSetting(Global.Locale_HU);
        i18Helper = new I18Helper(AppBundle.currentBundle());
        buildHeader();
        addListeners();
    }

    private void buildHeader() {
        header = new VerticalLayout();
        header.setHeightUndefined();
        addLangButtons();
        addNavButtons();
        addNavigation();
        addBanner();
        addComponent(header);
    }

    private void addNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addComponents(btnAdverts, btnLogin,
                btnRegistration, btnAdvertReg,
                btnMyAccout, btnAdminAccount, btnLogout);

        HorizontalLayout languages = new HorizontalLayout();
        languages.addComponents(btnHun, btnEng);

        HorizontalLayout headersplitter = new HorizontalLayout();
        headersplitter.addComponents(navigation, languages);
        headersplitter.setWidth(i18Helper.getMessage("size_100"));
        headersplitter.setComponentAlignment(navigation, Alignment.TOP_RIGHT);
        headersplitter.setComponentAlignment(languages, Alignment.TOP_RIGHT);

        header.addComponent(headersplitter);
        header.setComponentAlignment(headersplitter, Alignment.TOP_CENTER);
    }

    private void addLangButtons() {
        addHungarianButton();
        addEnglishButton();
    }

    private void addHungarianButton() {
        btnHun = new Button(i18Helper.getMessage("locale_hu"));
        btnHun.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                localeSetting(Global.Locale_HU);
                i18Helper = new I18Helper(AppBundle.currentBundle());
                updateStrings();
            }
        });
    }

    private void addEnglishButton() {
        btnEng = new Button(i18Helper.getMessage("locale_en"));
        btnEng.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                localeSetting(Global.Locale_EN);
                i18Helper = new I18Helper(AppBundle.currentBundle());
                updateStrings();
            }
        });
    }

    public void localeSetting(Locale locale) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(CURRENTLOCALE.toString(), locale);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    private void hidebuttons() {
        btnAdvertReg.setVisible(false);
        btnMyAccout.setVisible(false);
        btnLogout.setVisible(false);
        btnAdminAccount.setVisible(false);
    }

    private void addBanner() {
        Panel bannerPanel = new Panel();
        bannerPanel.setHeight(Global.APP_BANNER_HEIGHT);
        bannerPanel.setStyleName(BANNERPANEL.toString());

        VerticalLayout banner = new VerticalLayout();
        banner.addComponent(bannerPanel);
        banner.setHeightUndefined();

        header.addComponent(banner);
        header.setComponentAlignment(banner, Alignment.TOP_CENTER);
    }

    private void logout() {
        btnLogin.setVisible(true);
        btnRegistration.setVisible(true);
        btnAdvertReg.setVisible(false);
        btnMyAccout.setVisible(false);
        btnLogout.setVisible(false);
        btnAdminAccount.setVisible(false);

        AccountView.setAvailability(false);
        AdvertRegView.setAvailability(false);
        LetterView.setAvailability(false);
        AdminView.setAvailability(false);

        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(
                    CURRENTUSER.toString(), null);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    public static void userLogin() {
        btnLogin.setVisible(false);
        btnRegistration.setVisible(false);
        btnAdvertReg.setVisible(true);
        btnMyAccout.setVisible(true);
        btnLogout.setVisible(true);
    }

    public static void adminLogin() {
        userLogin();
        btnAdminAccount.setVisible(true);
    }

    private Button navButton(String caption) {
        Button button = new Button(caption);
        button.setStyleName(NAVBUTTON.toString());
        return button;
    }

    private void addListeners() {
        addBtnAdvertListener();
        addBtnLoginListener();
        addBtnRegistrationListener();
        addBtnAdvertRegListener();
        addBtnMyAccountListener();
        addBtnLogoutListener();
        addBtnAdminAccListener();
    }

    private void addBtnAdvertListener() {
        btnAdverts.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTS.toString();
                jump(nodePath);
            }
        });
    }

    private void addBtnLoginListener() {
        btnLogin.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = LOGIN.toString();
                jump(nodePath);
            }
        });
    }

    private void addBtnRegistrationListener() {
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = REGISTRATION.toString();
                jump(nodePath);
            }
        });
    }

    private void addBtnAdvertRegListener() {
        btnAdvertReg.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTREG.toString();
                jump(nodePath);
            }
        });
    }

    private void addBtnMyAccountListener() {
        btnMyAccout.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = USERPAGE.toString();
                jump(nodePath);
            }
        });

    }

    private void addBtnLogoutListener() {
        btnLogout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout();
                nodePath = ADVERTS.toString();
                jump(nodePath);
            }
        });
    }

    private void addBtnAdminAccListener() {
        btnAdminAccount.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADMINPAGE.toString();
                jump(nodePath);
            }
        });
    }

    private void addNavButtons() {
        btnAdverts = navButton(i18Helper.getMessage("Adverts"));
        btnLogin = navButton(i18Helper.getMessage("Login"));
        btnRegistration = navButton(i18Helper.getMessage("Registration"));
        btnAdvertReg = navButton(i18Helper.getMessage("AdvertRegistration"));
        btnMyAccout = navButton(i18Helper.getMessage("Account"));
        btnLogout = navButton(i18Helper.getMessage("Logout"));
        btnAdminAccount = navButton(i18Helper.getMessage("AdminAccount"));
        hidebuttons();
    }

    private void updateStrings() {
        btnAdverts.setCaption(i18Helper.getMessage("Adverts"));
        btnLogin.setCaption(i18Helper.getMessage("Login"));
        btnRegistration.setCaption(i18Helper.getMessage("Registration"));
        btnAdvertReg.setCaption(i18Helper.getMessage("AdvertRegistration"));
        btnMyAccout.setCaption(i18Helper.getMessage("Account"));
        btnLogout.setCaption(i18Helper.getMessage("Logout"));
        btnAdminAccount.setCaption(i18Helper.getMessage("AdminAccount"));
        reloadWindow();
    }

    private void jump(String nodePath) {
        getUI().getNavigator().navigateTo(nodePath);
    }

    @Override
    public void showView(View view) {
    }

    private void reloadWindow() {
        JavaScript.getCurrent().execute("window.location.reload();");
    }
}
