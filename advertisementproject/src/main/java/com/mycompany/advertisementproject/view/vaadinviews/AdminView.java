package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.control.AdminController;
import static com.mycompany.advertisementproject.enumz.Fields.*;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADMINPAGE")
public class AdminView extends VerticalLayout implements View {

    private ResourceBundle bundle;

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

    private String countryCaption;
    private String cityCaption;
    private String categoryCaption;
    private String subCateogryCaption;
    private String stateCaption;
    private String typeCaption;
    private String expansion;
    private String create;
    private String modify;
    private String delete;

    @Inject
    private CountryFacade countryFacade;
    @Inject
    private CityFacade cityFacade;
    @Inject
    private MaincategoryFacade categoryFacade;
    @Inject
    private SubcategoryFacade subcategoryFacade;
    @Inject
    private AdvertstateFacade stateFacade;
    @Inject
    private AdverttypeFacade typeFacade;

    private static boolean availability = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            bundle = AppBundle.currentBundle("");
            buildView();
            Notification.show("Hi there Admin!");
        } else {
            Notification.show("You shall not pass!");
        }
    }

    public static void setAvailability(boolean availability) {
        AdminView.availability = availability;
    }

    public void buildView() {
        try {
            setController();
            updateStrings();
            setViewParameters();
            buildAccoordion();
        } catch (Exception ex) {
            Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setController() {
        controller = new AdminController();
        controller.setCountryFacade(countryFacade);
        controller.setCityFacade(cityFacade);
        controller.setCategoryFacade(categoryFacade);
        controller.setSubcategoryFacade(subcategoryFacade);
        controller.setStateFacade(stateFacade);
        controller.setTypeFacade(typeFacade);
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
        addAccoridonChangeListener();
        this.addComponent(accordion);
    }

    private void addCountryTab() {
        createCountryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, countryCaption);

        addCountryButtonLogic(btnCreate, btnDelete, btnModify);
        addCountryComboBoxLogic();
    }

    private void addCity() {
        createCityFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, cityCaption);

        addCityButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addCategory() {
        createCategoryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, categoryCaption);

        addCategoryButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addSubCategory() {
        createSubCategoryFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, subCateogryCaption);

        addSubCategoryButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addState() {
        createStateFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, stateCaption);

        addStateButtonLogic(btnCreate, btnDelete, btnModify);
    }

    private void addType() {
        createTypeFields();

        accordionLayout.addComponent(tabSheet);
        accordion.addTab(accordionLayout, typeCaption);

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

        tabsheetLayoutLeft.setCaption(expansion);
        tabsheetLayoutRight.setCaption(modify);

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

        controller.popluateCountryFields(cmbBxModifyCountry);

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

        controller.popluateCityFields(cmbBxModifyCity, cmbBxCreateCity);

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(cmbBxCreateCity, txtFldCreateCity, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyCity, txtFldModifyCity, btnModify, btnDelete);
    }

    private void createCategoryFields() {
        txtFldCreateCategory = new TextField();
        txtFldModifyCategory = new TextField();
        cmbBxModifyCategory = new ComboBox();

        controller.popluateCategoryFields(cmbBxModifyCategory);
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

        controller.popluateSubCategoryFields(cmbBxModifySubCategory, cmbBxCreateSubCategory);

        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(cmbBxCreateSubCategory, txtFldCreateSubCategory, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifySubCategory, txtFldModifySubCategory, btnModify, btnDelete);
    }

    private void createStateFields() {
        txtFldCreateState = new TextField();
        txtFldModifyState = new TextField();
        cmbBxModifyState = new ComboBox();

        controller.popluateStateFields(cmbBxModifyState);
        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateState, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyState, txtFldModifyState, btnModify, btnDelete);
    }

    private void createTypeFields() {
        txtFldCreateType = new TextField();
        txtFldModifyType = new TextField();
        cmbBxModifyType = new ComboBox();

        controller.popluateTypeFields(cmbBxModifyType);
        createButtons();

        tabSheet = basicTabSheet();

        tabsheetLayoutLeft.addComponents(txtFldCreateType, btnCreate);
        tabsheetLayoutRight.addComponents(cmbBxModifyType, txtFldModifyType, btnModify, btnDelete);
    }

    private void createButtons() {
        btnCreate = new Button(create);
        btnModify = new Button(modify);
        btnDelete = new Button(delete);
    }

    private void addCountryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createCountry(txtFldCreateCountry.getValue());
                    txtFldCreateCountry.clear();
                } catch (SQLIntegrityConstraintViolationException e) {
                    Notification.show("Ennek kéne működnie de nem működik és már le is szarom.");
                } catch (Exception ex) {
                    Notification.show("Nem ennek kéne működnie de már leszarom.");
                }
            }
        }
        );
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteCountry(cmbBxModifyCountry.getValue().toString());
                    controller.popluateCountryFields(cmbBxModifyCountry);
                    controller.popluateCityFields(cmbBxModifyCity, cmbBxCreateCity);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyCountry(cmbBxModifyCountry.getValue().toString(), txtFldModifyCountry.getValue());
                    controller.popluateCountryFields(cmbBxModifyCountry);
                    controller.popluateCityFields(cmbBxModifyCity, cmbBxCreateCity);
                    cmbBxCreateCity.addItems(countryFacade.findAll());
                    txtFldModifyCountry.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addCityButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createCity(txtFldCreateCity.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteCity(cmbBxModifyCity.getValue().toString());
                    controller.popluateCityFields(cmbBxModifyCity, cmbBxCreateCity);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyCity(cmbBxModifyCity.getValue().toString(), txtFldModifyCity.getValue());
                    controller.popluateCityFields(cmbBxModifyCity, cmbBxCreateCity);
                    txtFldModifyCity.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addCategoryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createCategory(txtFldCreateCategory.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteCategory(cmbBxModifyCategory.getValue().toString());
                    controller.popluateCategoryFields(cmbBxModifyCategory);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyCategory(cmbBxModifyCategory.getValue().toString(), txtFldModifyCategory.getValue());
                    controller.popluateCategoryFields(cmbBxModifyCategory);
                    txtFldModifyCategory.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addSubCategoryButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createSubCategory(txtFldCreateSubCategory.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteSubCategory(cmbBxModifySubCategory.getValue().toString());
                    controller.popluateSubCategoryFields(cmbBxModifySubCategory, cmbBxCreateSubCategory);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifySubCategory(cmbBxModifySubCategory.getValue().toString(), txtFldModifySubCategory.getValue());
                    controller.popluateSubCategoryFields(cmbBxModifySubCategory, cmbBxCreateSubCategory);
                    txtFldModifySubCategory.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addStateButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createState(txtFldCreateState.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteState(cmbBxModifyState.getValue().toString());
                    controller.popluateStateFields(cmbBxModifyState);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyState(cmbBxModifyState.getValue().toString(), txtFldModifyState.getValue());
                    controller.popluateStateFields(cmbBxModifyState);
                    txtFldModifyState.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addTypeButtonLogic(Button btnCreate, Button btnDelete, Button btnModify) {
        btnCreate.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.createType(txtFldCreateType.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDelete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteType(cmbBxModifyType.getValue().toString());
                    controller.popluateTypeFields(cmbBxModifyType);
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnModify.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.modifyType(cmbBxModifyType.getValue().toString(), txtFldModifyType.getValue());
                    controller.popluateTypeFields(cmbBxModifyType);
                    txtFldModifyType.clear();
                } catch (Exception ex) {
                    Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addCountryComboBoxLogic() {
        cmbBxModifyCountry.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Notification.show("trigger");
//                txtFldModifyCountry.setValue(cmbBxModifyCountry.getValue().toString());
            }
        });
    }

    public void updateStrings() throws Exception {
        countryCaption = bundle.getString("Country");
        cityCaption = bundle.getString("City");
        categoryCaption = bundle.getString("Category");
        subCateogryCaption = bundle.getString("SubCategory");
        stateCaption = bundle.getString("State");
        typeCaption = bundle.getString("Type");
        expansion = bundle.getString("Expansion");
        create = bundle.getString("Create");
        modify = bundle.getString("Modify");
        delete = bundle.getString("Delete");
    }

    private void addAccoridonChangeListener() {
        
    }

}
