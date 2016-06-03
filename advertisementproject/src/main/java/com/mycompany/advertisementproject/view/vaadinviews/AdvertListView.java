package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.control.AdvertListController;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

    @Inject
    private AdvertListController controller;

    private I18Helper i18Helper;

    private String searchTextFieldWidth;
    private String SearchButtonValue;
    private String searchbarPanelWidth;
    private String titleLabelWidth;
    private String filterLabelValue;
    private String filterPanelWidth;
    private String advertPanelWidth;
    private String categoryCmbbxPrompt;
    private String subCategoryCmbbxPrompt;
    private String typeCmbbxPromprt;
    private String stateCmbbxPrompt;
    private String cityCmbbxPrompt;
    private String countryCmbbxPrompt;
    private String minPriceTxtFldPrompt;
    private String maxPriceTxtFldPrompt;
    private String filterButtonCaption;
    private String noResult;
    private String imageHeight;
    private String imageWidth;

    private String sortCmbbxPrompt;
    private String sortTypePriceAsc;
    private String sortTypePriceDesc;
    private String sortTypeDateAsc;
    private String sortTypeDateDesc;

    private Label lblTitle;
    private Label lblPrice;
    private Label lblCategory;
    private Label lblSubCategory;
    private Label lblCity;
    private Label lblDate;
    private Label lblFilter;

    private ComboBox cmbBxSortType;
    private ComboBox cmbBxCategory;
    private ComboBox cmbBxSubCategory;
    private ComboBox cmbBxCountry;
    private ComboBox cmbBxCity;
    private ComboBox cmbBxType;
    private ComboBox cmbBxState;

    private Panel filterPanel;
    private Panel advertPanel;
    private Panel searchBarPanel;

    private TextField txtFldSearch;
    private TextField txtFldMinPrice;
    private TextField txtFldMaxPrice;

    private HorizontalLayout contentLayout;
    private HorizontalLayout searchBarLayout;
    private HorizontalLayout advertHorizontal;
    private VerticalLayout advertLeftVertical;
    private VerticalLayout advertMiddleVertical;
    private VerticalLayout advertRightVertical;

    private VerticalLayout advertList;
    private VerticalLayout filterForm;

    private Button btnFilter;
    private Button btnSearch;

    private Embedded image;
    private String numberFormatError;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
        buildAdverts();
    }

    @PostConstruct
    public void initComponent() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        build();
    }

    public void build() {
        defaultSettings();
        buildSearchBar();
        createAdvertPanel();
        buildFilters();
        addListeners();
    }

    private void defaultSettings() {
        controller.setView(this);
        updateStrings();
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        createContentLayout();
    }

    private void buildAdverts() {
        try {
            controller.fillAdverts();
            controller.loadAdverts();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createContentLayout() {
        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
    }

    private void buildSearchBar() {
        try {
            searchBarLayout = new HorizontalLayout();
            searchBarLayout.setSpacing(true);
            searchBarLayout.setMargin(true);

            txtFldSearch = new TextField();
            txtFldSearch.setWidth(searchTextFieldWidth);

            btnSearch = new Button(SearchButtonValue);

            cmbBxSortType = new ComboBox();
            cmbBxSortType.setInputPrompt(sortCmbbxPrompt);
            addSortTypes();

            searchBarLayout.addComponent(txtFldSearch);
            searchBarLayout.addComponent(btnSearch);
            searchBarLayout.addComponent(cmbBxSortType);

            searchBarPanel = new Panel();
            searchBarPanel.setWidth(searchbarPanelWidth);
            searchBarPanel.setContent(searchBarLayout);
            addComponent(searchBarPanel);
            setComponentAlignment(searchBarPanel, Alignment.TOP_CENTER);

        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HorizontalLayout buildSingleAdvert(final Advertisement adv) throws Exception {
        createAdvertLayouts();
        linkDataToLabels(adv);
        populateImageLayout(adv);
        populateAdvertLayouts();
        addAdvertLayoutListener(adv);
        return advertHorizontal;
    }

    private void createAdvertLayouts() {
        advertHorizontal = new HorizontalLayout();
        advertLeftVertical = new VerticalLayout();
        advertMiddleVertical = new VerticalLayout();
        advertRightVertical = new VerticalLayout();

        advertHorizontal.setMargin(true);
        advertHorizontal.setSpacing(true);
        advertMiddleVertical.setSpacing(true);
        advertRightVertical.setSpacing(true);
        advertLeftVertical.setSpacing(true);
        advertLeftVertical.setSizeFull();
    }

    private void populateImageLayout(Advertisement adv) {
        image = new Embedded();
        controller.addPictureToAdvert(image, adv);
        advertLeftVertical.addComponent(image);
        advertLeftVertical.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
    }

    private void populateAdvertLayouts() {
        advertMiddleVertical.addComponent(lblTitle);
        advertMiddleVertical.addComponent(lblPrice);
        advertRightVertical.addComponent(lblCategory);
        advertRightVertical.addComponent(lblSubCategory);
        advertRightVertical.addComponent(lblCity);
        advertRightVertical.addComponent(lblDate);
        advertHorizontal.addComponent(advertLeftVertical);
        advertHorizontal.addComponent(advertMiddleVertical);
        advertHorizontal.addComponent(advertRightVertical);

    }

    private void addAdvertLayoutListener(final Advertisement adv) {
        advertHorizontal.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                controller.selectedAdvert(adv);
            }
        });
    }

    private void createLabels() {
        lblTitle = new Label();
        lblPrice = new Label();
        lblCategory = new Label();
        lblSubCategory = new Label();
        lblCity = new Label();
        lblDate = new Label();
    }

    private void linkDataToLabels(Advertisement adv) {
        createLabels();

        if (adv.getTitle() != null) {
            lblTitle.setValue(adv.getTitle());
            lblTitle.setWidth(titleLabelWidth);
        }
        if (adv.getPrice() != null) {
            lblPrice.setValue(Global.CURRENCY.format(Global.exchange_huf_to_gbp(adv.getPrice())));
        }
        if (adv.getMainCategoryId() != null) {
            lblCategory.setValue(adv.getMainCategoryId().getName());
        }
        if (adv.getSubCategoryId() != null) {
            lblSubCategory.setValue(adv.getSubCategoryId().getName());
        }
        if (adv.getCityId() != null) {
            lblCity.setValue(adv.getCityId().getCityName());
        }
        if (adv.getRegistrationDate() != null) {
            lblDate.setValue(Global.DATEFORMAT.format(adv.getRegistrationDate()));
        }
    }

    private void buildFilters() {
        try {
            addFilterForm();
            createElements();
            setElements();
            populateFilterForm();
            createFilterPanel();
            contentLayout.addComponent(filterPanel);
            controller.fillComboBoxes();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addFilterForm() {
        filterForm = new VerticalLayout();
        filterForm.setWidthUndefined();
        filterForm.setSpacing(true);
        filterForm.setMargin(true);
        filterForm.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        lblFilter = new Label(filterLabelValue);
        lblFilter.setSizeUndefined();
        filterForm.addComponent(lblFilter);
        filterForm.addComponent(new Label("<hr />", ContentMode.HTML));
    }

    private void createElements() {
        cmbBxCategory = new ComboBox();
        cmbBxSubCategory = new ComboBox();
        cmbBxCountry = new ComboBox();
        cmbBxCity = new ComboBox();
        cmbBxType = new ComboBox();
        cmbBxState = new ComboBox();
        txtFldMinPrice = new TextField();
        txtFldMaxPrice = new TextField();
        btnFilter = new Button(filterButtonCaption);
    }

    private void setElements() {
        cmbBxSubCategory.setEnabled(false);
        cmbBxCity.setEnabled(false);
        cmbBxCategory.setInputPrompt(categoryCmbbxPrompt);
        cmbBxSubCategory.setInputPrompt(subCategoryCmbbxPrompt);
        cmbBxCountry.setInputPrompt(countryCmbbxPrompt);
        cmbBxCity.setInputPrompt(cityCmbbxPrompt);
        cmbBxType.setInputPrompt(typeCmbbxPromprt);
        cmbBxState.setInputPrompt(stateCmbbxPrompt);
        txtFldMaxPrice.setInputPrompt(maxPriceTxtFldPrompt);
        txtFldMinPrice.setInputPrompt(minPriceTxtFldPrompt);
    }

    private void populateFilterForm() {
        filterForm.addComponent(cmbBxCountry);
        filterForm.addComponent(cmbBxCity);
        filterForm.addComponent(cmbBxCategory);
        filterForm.addComponent(cmbBxSubCategory);
        filterForm.addComponent(cmbBxType);
        filterForm.addComponent(cmbBxState);
        filterForm.addComponent(txtFldMinPrice);
        filterForm.addComponent(txtFldMaxPrice);
        filterForm.addComponent(btnFilter);
    }

    private void createFilterPanel() {
        filterPanel = new Panel();
        filterPanel.setWidth(filterPanelWidth);
        filterPanel.setContent(filterForm);
    }

    public void createAdverts() throws Exception {
        advertList = new VerticalLayout();
        advertList.setSpacing(true);
        advertList.setMargin(true);

        final VerticalLayout itemsArea = new VerticalLayout();
        controller.pageAdverts(advertList, itemsArea);
    }

    private void createAdvertPanel() {
        addComponent(contentLayout);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        
        advertPanel = new Panel();
        advertPanel.setWidth(advertPanelWidth);
        advertPanel.setHeightUndefined();
        advertPanel.setContent(advertList);
        contentLayout.addComponent(advertPanel);
    }

    public void noResult() {
        Label lblNoResult = new Label(getNoResult());
        advertPanel.setContent(lblNoResult);
    }

    public void getResult() {
        try {
            createAdverts();
            advertPanel.setContent(advertList);
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addListeners() {
        try {
            addSearchButtonListener();
            addFilterButtonListener();
            addCountyComboListener();
            addCategoryComboListener();
            addSortTypeComboListener();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSearchButtonListener() {
        btnSearch.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.searchAdverts(txtFldSearch.getValue());

                } catch (Exception ex) {
                    Logger.getLogger(AdvertListView.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addFilterButtonListener() {
        btnFilter.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                txtFldMinPrice.setComponentError(null);
                txtFldMaxPrice.setComponentError(null);
                try {
                    controller.filterAdverts();
                } catch (NumberFormatException e) {
                    Notification.show(numberFormatError);

                } catch (Exception ex) {
                    Logger.getLogger(AdvertListView.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addCountyComboListener() {
        cmbBxCountry.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {

                if (cmbBxCountry.getValue() != null) {
                    controller.fillCmbBxCity(cmbBxCountry.getValue());
                } else {
                    cmbBxCity.setEnabled(false);
                }
            }
        });
    }

    private void addCategoryComboListener() {
        cmbBxCategory.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {

                if (cmbBxCategory.getValue() != null) {
                    controller.fillCmbBxSubCategory(cmbBxCategory.getValue());
                } else {
                    cmbBxSubCategory.setEnabled(false);
                }
            }
        });
    }

    private void addSortTypeComboListener() {
        cmbBxSortType.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    controller.sort(event.getProperty().getValue());

                } catch (Exception ex) {
                    Logger.getLogger(AdvertListView.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addSortTypes() throws Exception {
        cmbBxSortType.addItem(sortTypeDateAsc);
        cmbBxSortType.addItem(sortTypeDateDesc);
        cmbBxSortType.addItem(sortTypePriceAsc);
        cmbBxSortType.addItem(sortTypePriceDesc);
    }

    public void updateStrings() {
        searchTextFieldWidth = i18Helper.getMessage("Adverts.SearchTxtFldWidth");
        SearchButtonValue = i18Helper.getMessage("Search");
        searchbarPanelWidth = i18Helper.getMessage("Adverts.SearchPanelWidth");
        titleLabelWidth = i18Helper.getMessage("Adverts.TitleLabelWidth");
        filterLabelValue = i18Helper.getMessage("AdvancedFilter");
        filterPanelWidth = i18Helper.getMessage("Adverts.FilterPanelWidth");
        advertPanelWidth = i18Helper.getMessage("Adverts.AdvertPanelWidth");
        categoryCmbbxPrompt = i18Helper.getMessage("Category");
        subCategoryCmbbxPrompt = i18Helper.getMessage("SubCategory");
        typeCmbbxPromprt = i18Helper.getMessage("Type");
        stateCmbbxPrompt = i18Helper.getMessage("State");
        cityCmbbxPrompt = i18Helper.getMessage("City");
        countryCmbbxPrompt = i18Helper.getMessage("Country");
        minPriceTxtFldPrompt = i18Helper.getMessage("MinPrice");
        maxPriceTxtFldPrompt = i18Helper.getMessage("MaxPrice");
        filterButtonCaption = i18Helper.getMessage("Filter");
        imageWidth = i18Helper.getMessage("imgWidth");
        imageHeight = i18Helper.getMessage("imgHeight");
        noResult = i18Helper.getMessage("noResult");
        sortCmbbxPrompt = i18Helper.getMessage("Equalization");
        sortTypeDateAsc = i18Helper.getMessage("Oldest");
        sortTypeDateDesc = i18Helper.getMessage("Newest");
        sortTypePriceAsc = i18Helper.getMessage("Cheapest");
        sortTypePriceDesc = i18Helper.getMessage("MostExpensive");
        numberFormatError = i18Helper.getMessage("numberFormatError");
    }

    public ComboBox getCmbBxSortType() {
        return cmbBxSortType;
    }

    public ComboBox getCmbBxCategory() {
        return cmbBxCategory;
    }

    public ComboBox getCmbBxSubCategory() {
        return cmbBxSubCategory;
    }

    public ComboBox getCmbBxCountry() {
        return cmbBxCountry;
    }

    public ComboBox getCmbBxCity() {
        return cmbBxCity;
    }

    public ComboBox getCmbBxType() {
        return cmbBxType;
    }

    public ComboBox getCmbBxState() {
        return cmbBxState;
    }

    public VerticalLayout getAdvertList() {
        return advertList;
    }

    public TextField getTxtFldMinPrice() {
        return txtFldMinPrice;
    }

    public TextField getTxtFldMaxPrice() {
        return txtFldMaxPrice;
    }

    public Panel getAdvertPanel() {
        return advertPanel;
    }

    public String getNoResult() {
        return noResult;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getSortTypePriceAsc() {
        return sortTypePriceAsc;
    }

    public String getSortTypePriceDesc() {
        return sortTypePriceDesc;
    }

    public String getSortTypeDateAsc() {
        return sortTypeDateAsc;
    }

    public String getSortTypeDateDesc() {
        return sortTypeDateDesc;
    }

    public AdvertListController getController() {
        return controller;
    }

    public String getNumberFormatError() {
        return numberFormatError;
    }
}
