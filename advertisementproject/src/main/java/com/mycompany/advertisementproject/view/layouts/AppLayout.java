package com.mycompany.advertisementproject.view.layouts;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTLOCALE;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.StyleNames.*;
import static com.mycompany.advertisementproject.enumz.Views.*;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
import com.mycompany.advertisementproject.view.UIs.RootUI;
import com.mycompany.advertisementproject.view.vaadinviews.AccountView;
import com.mycompany.advertisementproject.view.vaadinviews.AdminView;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertListView;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertRegView;
import com.mycompany.advertisementproject.view.vaadinviews.LetterView;
import com.mycompany.advertisementproject.view.vaadinviews.LogInView;
import com.mycompany.advertisementproject.view.vaadinviews.RegistrationView;
import com.mycompany.advertisementproject.view.vaadinviews.SelectedAdvertView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLayout extends VerticalLayout implements ViewDisplay {

    private LogInView loginView;
    private RegistrationView regView;
    private AccountView accView;
    private AdminView adminView;
    private AdvertListView advListView;
    private AdvertRegView advRegView;

    private LetterView letterView;
    private SelectedAdvertView selectedView;

    private ResourceBundle bundle;

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
        bundle = AppBundle.currentBundle();
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
        HorizontalLayout languages = new HorizontalLayout();
        navigation.addComponents(btnAdverts, btnLogin, btnRegistration, btnAdvertReg, btnMyAccout, btnAdminAccount, btnLogout);
        languages.addComponents(btnHun, btnEng);

        HorizontalLayout headersplitter = new HorizontalLayout();
        headersplitter.addComponents(navigation, languages);
        headersplitter.setWidth(bundle.getString("size_100"));
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
        btnHun = new Button(bundle.getString("locale_hu"));
        btnHun.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    VaadinSession.getCurrent().getLockInstance().lock();
                    VaadinSession.getCurrent().setAttribute(CURRENTLOCALE.toString(), (Locale) Global.Locale_HU);
                } finally {
                    VaadinSession.getCurrent().getLockInstance().unlock();
                }
                bundle = AppBundle.currentBundle();
                updateStrings();
            }
        });
    }

    private void addEnglishButton() {
        btnEng = new Button(bundle.getString("locale_en"));
        btnEng.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    VaadinSession.getCurrent().getLockInstance().lock();
                    VaadinSession.getCurrent().setAttribute(CURRENTLOCALE.toString(), (Locale) Global.Locale_EN);
                } finally {
                    VaadinSession.getCurrent().getLockInstance().unlock();
                }
                bundle = AppBundle.currentBundle();
                updateStrings();
            }
        });
    }

    private void addNavButtons() {
        btnAdverts = navButton(bundle.getString("Adverts"));
        btnLogin = navButton(bundle.getString("Login"));
        btnRegistration = navButton(bundle.getString("Registration"));
        btnAdvertReg = navButton(bundle.getString("AdvertRegistration"));
        btnMyAccout = navButton(bundle.getString("Account"));
        btnLogout = navButton(bundle.getString("Logout"));
        btnAdminAccount = navButton(bundle.getString("AdminAccount"));
        hidebuttons();
    }

    private void updateStrings() {
        btnAdverts.setCaption(bundle.getString("Adverts"));
        btnLogin.setCaption(bundle.getString("Login"));
        btnRegistration.setCaption(bundle.getString("Registration"));
        btnAdvertReg.setCaption(bundle.getString("AdvertRegistration"));
        btnMyAccout.setCaption(bundle.getString("Account"));
        btnLogout.setCaption(bundle.getString("Logout"));
        btnAdminAccount.setCaption(bundle.getString("AdminAccount"));
        checkViews();
        reloadWindow();
    }

    private void checkViews() {
        if (loginView != null) {
            loginView.updateStrings();
        }
        if (regView != null) {
            regView.updateStrings();
        }
        if (accView != null) {
            accView.removeAllComponents();
            accView.build();
        }
        if (adminView != null) {
            adminView.removeAllComponents();
            adminView.buildView();
        }
        if (advListView != null) {
            advListView.removeAllComponents();
            advListView.build();
        }
        if (advRegView != null) {
            advRegView.updateStrings();
        }
//        selectedView = (SelectedAdvertView) RootUI.getCurrent().getViewProvider().getView("SELECTED");
//        if (selectedView != null) {
//            selectedView.removeAllComponents();
//            selectedView.build();
//        }
//        letterView = (LetterView) RootUI.getCurrent().getViewProvider().getView("LETTER");
//        if (letterView != null) {
//            letterView.removeAllComponents();
//            letterView.build();
//        }
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
            VaadinSession.getCurrent().setAttribute(CURRENTUSER.toString(), null);
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
        btnAdminAccount.setVisible(true);
        btnLogin.setVisible(false);
        btnRegistration.setVisible(false);
        btnAdvertReg.setVisible(true);
        btnMyAccout.setVisible(true);
        btnLogout.setVisible(true);
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
                advListView = (AdvertListView) RootUI.getCurrent().getViewProvider().getView("ADVERTS");
            }
        });
    }

    private void addBtnLoginListener() {
        btnLogin.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = LOGIN.toString();
                jump(nodePath);
                loginView = (LogInView) RootUI.getCurrent().getViewProvider().getView("LOGIN");
            }
        });
    }

    private void addBtnRegistrationListener() {
        btnRegistration.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = REGISTRATION.toString();
                jump(nodePath);
                regView = (RegistrationView) RootUI.getCurrent().getViewProvider().getView("REGISTRATION");
            }
        });
    }

    private void addBtnAdvertRegListener() {
        btnAdvertReg.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = ADVERTREG.toString();
                jump(nodePath);
                advRegView = (AdvertRegView) RootUI.getCurrent().getViewProvider().getView("ADVERTREG");
            }
        });
    }

    private void addBtnMyAccountListener() {
        btnMyAccout.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                nodePath = USERPAGE.toString();
                jump(nodePath);
                accView = (AccountView) RootUI.getCurrent().getViewProvider().getView("USERPAGE");
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
                adminView = (AdminView) RootUI.getCurrent().getViewProvider().getView("ADMINPAGE");
            }
        });
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
