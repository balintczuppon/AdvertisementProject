package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.PictureFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.control.AdvertListController;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.Global;
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

    private ResourceBundle bundle;

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

    private AdvertListController controller;

    private Embedded image;

    @Inject
    MaincategoryFacade maincategoryFacade;
    @Inject
    SubcategoryFacade subcategoryFacade;
    @Inject
    PictureFacade pictureFacade;
    @Inject
    AdverttypeFacade adverttypeFacade;
    @Inject
    CountryFacade countryFacade;
    @Inject
    CityFacade cityFacade;
    @Inject
    AdvertisementFacade advertisementFacade;
    @Inject
    AdvertstateFacade advertstateFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        bundle = AppBundle.currentBundle("");
        build();
    }

    public void build() {
        try {
            updateStrings();
            setSizeFull();
            setMargin(true);
            setSpacing(true);
            buildView();
            addListeners();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildView() throws Exception {
        controller = new AdvertListController(this);
        buildSearchBar();
        buildFilters();
        controller.fillComboBoxes();
        controller.fillAdverts();
        controller.loadAdverts();
        layoutSettings();
    }

    private void layoutSettings() {
        setComponentAlignment(searchBarPanel, Alignment.TOP_CENTER);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        contentLayout.setComponentAlignment(filterPanel, Alignment.TOP_LEFT);
        contentLayout.setComponentAlignment(advertPanel, Alignment.TOP_LEFT);
    }

    private void buildSearchBar() throws Exception {
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
        if (adv.getPrice() != 0) {
            lblPrice.setValue(Global.CURRENCY.format(adv.getPrice()));
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

    private void buildFilters() throws Exception {
        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);

        addFilterForm();
        createElements();
        setElements();
        populateFilterForm();
        createFilterPanel();

        contentLayout.addComponent(filterPanel);
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

    public void buildAdverts() throws Exception {
        advertList = new VerticalLayout();
        advertList.setSpacing(true);
        advertList.setMargin(true);

        final VerticalLayout itemsArea = new VerticalLayout();
        controller.pageAdverts(advertList, itemsArea);

        if (advertPanel == null) {
            advertPanel = new Panel();
            advertPanel.setWidth(advertPanelWidth);
            advertPanel.setHeightUndefined();
            advertPanel.setContent(advertList);
            contentLayout.addComponent(advertPanel);
            addComponent(contentLayout);
        }
    }

    private void addListeners() throws Exception {
        addSearchButtonListener();
        addFilterButtonListener();;
        addCountyComboListener();
        addCategoryComboListener();
        addSortTypeComboListener();
    }

    private void addSearchButtonListener() {
        btnSearch.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.searchAdverts(txtFldSearch.getValue());
                } catch (Exception ex) {
                    Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addFilterButtonListener() {
        btnFilter.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.filterAdverts();
                } catch (Exception ex) {
                    Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
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
        searchTextFieldWidth = bundle.getString("Adverts.SearchTxtFldWidth");
        SearchButtonValue = bundle.getString("Search");
        searchbarPanelWidth = bundle.getString("Adverts.SearchPanelWidth");
        titleLabelWidth = bundle.getString("Adverts.TitleLabelWidth");
        filterLabelValue = bundle.getString("AdvancedFilter");
        filterPanelWidth = bundle.getString("Adverts.FilterPanelWidth");
        advertPanelWidth = bundle.getString("Adverts.AdvertPanelWidth");
        categoryCmbbxPrompt = bundle.getString("Category");
        subCategoryCmbbxPrompt = bundle.getString("SubCategory");
        typeCmbbxPromprt = bundle.getString("Type");
        stateCmbbxPrompt = bundle.getString("State");
        cityCmbbxPrompt = bundle.getString("City");
        countryCmbbxPrompt = bundle.getString("Country");
        minPriceTxtFldPrompt = bundle.getString("MinPrice");
        maxPriceTxtFldPrompt = bundle.getString("MaxPrice");
        filterButtonCaption = bundle.getString("Filter");
        imageWidth = bundle.getString("imgWidth");
        imageHeight = bundle.getString("imgHeight");
        noResult = bundle.getString("noResult");
        sortCmbbxPrompt = bundle.getString("Equalization");
        sortTypeDateAsc = bundle.getString("Oldest");
        sortTypeDateDesc = bundle.getString("Newest");
        sortTypePriceAsc = bundle.getString("Cheapest");
        sortTypePriceDesc = bundle.getString("MostExpensive");
    }

    public MaincategoryFacade getMaincategoryFacade() {
        return maincategoryFacade;
    }

    public SubcategoryFacade getSubcategoryFacade() {
        return subcategoryFacade;
    }

    public PictureFacade getPictureFacade() {
        return pictureFacade;
    }

    public AdverttypeFacade getAdverttypeFacade() {
        return adverttypeFacade;
    }

    public CountryFacade getCountryFacade() {
        return countryFacade;
    }

    public CityFacade getCityFacade() {
        return cityFacade;
    }

    public AdvertisementFacade getAdvertisementFacade() {
        return advertisementFacade;
    }

    public AdvertstateFacade getAdvertstateFacade() {
        return advertstateFacade;
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
}
