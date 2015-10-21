package com.mycompany.advertisementproject.UIs;

import static com.mycompany.advertisementproject.Enums.Views.*;
import com.mycompany.advertisementproject.Layouts.AppLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.Resource;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.authorization.memory.MemoryPermissionManager;

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
//        Permissions.initialize(this, new MemoryPermissionManager());
//        
//        Role visitors = getVisitorRole();
//        Role regUsers = getRegisteredUsersRoles();
        
//        Resource accountView = getAccountView();
//        
//        Permissions.allow(regUsers,"design",acountView);

        appLayout = new AppLayout();
        VerticalLayout mainLayout = new VerticalLayout();

        navigator = new Navigator(this, mainLayout);
        navigator.addProvider(viewProvider);

        setContent(new VerticalLayout(appLayout, mainLayout));
        navigator.navigateTo(START.toString());
    }

    public AppLayout getAppLayout() {
        return appLayout;
    }

//    private Role getVisitorRole() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    private Role getRegisteredUsersRoles() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
