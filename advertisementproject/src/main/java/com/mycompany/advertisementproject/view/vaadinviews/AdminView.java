package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.control.AdminController;
import static com.mycompany.advertisementproject.enumz.Fields.*;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADMINPAGE")
public class AdminView extends VerticalLayout implements View {

    private AdminController controller;

    private Accordion accordion;
    private TabSheet tabSheet;

    private VerticalLayout accordionLayout;

    private HorizontalLayout tabsheetLayoutLeft;
    private HorizontalLayout tabsheetLayoutRight;

    private Button btnCreate;
    private Button btnDelete;
    private Button btnModify;

    private TextField txtFldCreateCountry;
    private TextField txtFldModifyCountry;
    private ComboBox cmbBxModifyCountry;

    private TextField txtFldCreateCity;
    private TextField txtFldModifyCity;
    private ComboBox cmbBxCreateCity;
    private ComboBox cmbBxModifyCity;

    private TextField txtFldCreateCategory;
    private TextField txtFldModifyCategory;
    private ComboBox cmbBxModifyCategory;

    private TextField txtFldCreateSubCategory;
    private TextField txtFldModifySubCategory;
    private ComboBox cmbBxCreateSubCategory;
    private ComboBox cmbBxModifySubCategory;

    private TextField txtFldCreateState;
    private TextField txtFldModifyState;
    private ComboBox cmbBxModifyState;

    private TextField txtFldCreateType;
    private TextField txtFldModifyType;
    private ComboBox cmbBxModifyType;

    @Inject
    private CountryFacade countryFacade;
    @Inject
    private CityFacade cityFacade;

    private static boolean availability = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            buildView();
            Notification.show("Hi there Admin!");
        } else {
            Notification.show("You shall not pass!");
        }
    }

    public static void setAvailability(boolean availability) {
        AdminView.availability = availability;
    }

    private void buildView() {
        controller = new AdminController();
        controller.setCountryFacade(countryFacade);
        controller.setCityFacade(cityFacade);
        setViewParameters();
        buildAccoordion();
    }

    private void setViewParameters() {
        this.setMargin(true);
        this.setSpacing(true);
        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }

    private void buildAccoordion() {
        accordion = new Accordion();
        addCountryTab();
        addCity();
        addCategory();
        addSubCategory();
        addState();
        addType();
        this.addComponent(accordion);
    }

    private void addCountryTab() {
        createCountryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Megye");

        addCountryButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addCity() {
        createCityFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Város");

        addCityButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addCategory() {
        createCategoryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Kategória");

        addCategoryButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addSubCategory() {
        createSubCategoryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Alkategória");

        addSubCategoryButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addState() {
        createStateFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Állapot");

        addStateButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addType() {
        createTypeFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, "Típus");

        addTypeButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private HorizontalLayout formattedHLayout() {
        HorizontalLayout tab = new HorizontalLayout();
        tab.setSpacing(true);
        tab.setMargin(true);
        return tab;
    }

    private TabSheet basicTabSheet() {
        TabSheet tSheet = new TabSheet();

        tabsheetLayoutLeft = formattedHLayout();
        tabsheetLayoutRight = formattedHLayout();

        tabsheetLayoutLeft.setCaption("Bővítés");
        tabsheetLayoutRight.setCaption("Módosítás");

        tSheet.addTab(tabsheetLayoutLeft);
        tSheet.addTab(tabsheetLayoutRight);

        accordionLayout = new VerticalLayout();
        accordionLayout.setSpacing(true);
        accordionLayout.setMargin(true);

        return tSheet;
    }

    private void createCountryFields() {
        txtFldCreateCountry = new TextField();
        txtFldModifyCountry = new TextField();
        cmbBxModifyCountry = new ComboBox();

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateCountry, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyCountry, txtFldModifyCountry, btnModify, btnDelete);
    }

    private void createCityFields() {
        txtFldCreateCity = new TextField();
        txtFldModifyCity = new TextField();
        cmbBxCreateCity = new ComboBox();
        cmbBxModifyCity = new ComboBox();

        cmbBxCreateCity.addItems(countryFacade.findAll());

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(cmbBxCreateCity, txtFldCreateCity, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyCity, txtFldModifyCity, btnModify, btnDelete);
    }

    private void createCategoryFields() {
        txtFldCreateCategory = new TextField();
        txtFldModifyCategory = new TextField();
        cmbBxModifyCategory = new ComboBox();

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateCategory, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyCategory, txtFldModifyCategory, btnModify, btnDelete);
    }

    private void createSubCategoryFields() {
        txtFldCreateSubCategory = new TextField();
        txtFldModifySubCategory = new TextField();
        cmbBxCreateSubCategory = new ComboBox();
        cmbBxModifySubCategory = new ComboBox();

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(cmbBxCreateSubCategory, txtFldCreateSubCategory, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifySubCategory, txtFldModifySubCategory, btnModify, btnDelete);
    }

    private void createStateFields() {
        txtFldCreateState = new TextField();
        txtFldModifyState = new TextField();
        cmbBxModifyState = new ComboBox();

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateState, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyState, txtFldModifyState, btnModify, btnDelete);
    }

    private void createTypeFields() {
        txtFldCreateType = new TextField();
        txtFldModifyType = new TextField();
        cmbBxModifyType = new ComboBox();

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateType, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyType, txtFldModifyType, btnModify, btnDelete);
    }

    private void createButtons() {
        btnCreate = new Button("Létrehoz");
        btnModify = new Button("Módosít");
        btnDelete = new Button("Töröl");
    }

    private void addCountryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.createCountry(txtFldCreateCountry.getValue());
            }
        });
    }

    private void addCityButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.createCity(txtFldCreateCity.getValue());
            }
        });
    }

    private void addCategoryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
    }

    private void addSubCategoryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
    }

    private void addStateButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
    }

    private void addTypeButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
    }
}
