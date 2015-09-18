package com.mycompany.advertisementproject.UIs;

import static com.mycompany.advertisementproject.Enums.Views.*;
import com.mycompany.advertisementproject.Layouts.AppLayout;
import com.mycompany.advertisementproject.UIs.Views.AdvertRegistrationView;
import com.mycompany.advertisementproject.UIs.Views.AdvertView;
import com.mycompany.advertisementproject.UIs.Views.StartView;
import com.mycompany.advertisementproject.UIs.Views.LogInView;
import com.mycompany.advertisementproject.UIs.Views.RegistrationView;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
@Widgetset("com.mycompany.advertisementproject.MyAppWidgetset")
public class RootUI extends UI {

    Navigator navigator;
    AppLayout appLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initAppContent();
    }

    private void initAppContent() {
        setSizeFull();
        appLayout = new AppLayout();
        setContent(appLayout);
        initNavigator(appLayout);
        getNavigator().navigateTo(START.toString());
    }

    private void initNavigator(AppLayout mainLayout) {
        navigator = new Navigator(this, (ViewDisplay) mainLayout);

        navigator.addView(ADVERTS.toString(), new AdvertView());
        navigator.addView(REGISTRATION.toString(), new RegistrationView());
        navigator.addView(LOGIN.toString(), new LogInView());
        navigator.addView(ADVERTREG.toString(), new AdvertRegistrationView());

        navigator.setErrorView(new Navigator.EmptyView());
        navigator.addViewChangeListener(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RootUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
