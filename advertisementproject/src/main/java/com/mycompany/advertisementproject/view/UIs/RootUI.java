package com.mycompany.advertisementproject.view.UIs;

import com.mycompany.advertisementproject.view.layouts.AppLayout;
import com.mycompany.advertisementproject.view.vaadinviews.StartView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 *
 * @author Czuppon Balint Peter
 */
@Theme("mytheme")
@Widgetset("com.mycompany.advertisementproject.MyAppWidgetset")
@CDIUI("")
public class RootUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    private Navigator navigator;
    private AppLayout appLayout;

    @Override
    protected void init(VaadinRequest request) {

        appLayout = new AppLayout();
        VerticalLayout mainLayout = new VerticalLayout();

        navigator = new Navigator(this, mainLayout);
        
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new StartView());

        setContent(new VerticalLayout(appLayout, mainLayout));
    }

    public static RootUI getCurrent() {
        return (RootUI) UI.getCurrent();
    }

    public AppLayout getAppLayout() {
        return appLayout;
    }

    public CDIViewProvider getViewProvider() {
        return viewProvider;
    }
}
