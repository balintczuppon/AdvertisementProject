package com.mycompany.advertisementproject.Layouts;

import static com.mycompany.advertisementproject.Enums.StyleNames.*;
import static com.mycompany.advertisementproject.Enums.Views.*;
import com.mycompany.advertisementproject.UIs.RootUI;
import com.mycompany.advertisementproject.UIs.Views.LogInView;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

//@DeclareRoles({"admin", "registeredUser", "visitor"})
public class AppLayout extends VerticalLayout implements ViewDisplay {

    private VerticalLayout header = new VerticalLayout();

    private String nodePath;

    private static Button btnNav1;
    private static Button btnNav2;
    private static Button btnNav3;
    private static Button btnNav4;
    private static Button btnNav5;
    private static Button btnNav6;

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
                    VaadinSession.getCurrent().setAttribute("AdvertToModify", null);
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

    @RolesAllowed({"admin", "registeredUer"})
    private void logout() {
        btnNav2.setVisible(true);
        btnNav3.setVisible(true);
        btnNav4.setVisible(false);
        btnNav5.setVisible(false);
        btnNav6.setVisible(false);
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("current_user", null);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    public static void login(Advertiser a) {
        btnNav2.setVisible(false);
        btnNav3.setVisible(false);
        btnNav4.setVisible(true);
        btnNav5.setVisible(true);
        btnNav6.setVisible(true);
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("current_user", a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
    }

    public static Button getBtnNav1() {
        return btnNav1;
    }

    public static Button getBtnNav2() {
        return btnNav2;
    }

    public static Button getBtnNav3() {
        return btnNav3;
    }

    public static Button getBtnNav4() {
        return btnNav4;
    }

    public static Button getBtnNav5() {
        return btnNav5;
    }

    public static Button getBtnNav6() {
        return btnNav6;
    }

}
