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
import com.mycompany.advertisementproject.view.layouts.AppLayout;
import com.mycompany.advertisementproject.control.AdvertListController;
import com.mycompany.advertisementproject.toolz.XmlFileReader;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

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

    private VerticalLayout advertList;
    private VerticalLayout filterForm;

    private Button btnFilter;
    private Button btnSearch;

    private XmlFileReader xmlReader;

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
        try {
            setSizeFull();
            setMargin(true);
            setSpacing(true);
            addLabelText();
            buildView();
            addListeners();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildView() throws Exception{
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

        HorizontalLayout advertHorizontal = new HorizontalLayout();
        VerticalLayout advertLeftVertical = new VerticalLayout();
        VerticalLayout advertMiddleVertical = new VerticalLayout();
        VerticalLayout advertRightVertical = new VerticalLayout();

        advertHorizontal.setMargin(true);
        advertHorizontal.setSpacing(true);
        advertMiddleVertical.setSpacing(true);
        advertRightVertical.setSpacing(true);
        advertLeftVertical.setSpacing(true);
        advertLeftVertical.setSizeFull();

        image = new Embedded();
        controller.addPictureToAdvert(image, adv);
        advertLeftVertical.addComponent(image);
        advertLeftVertical.setComponentAlignment(image, Alignment.MIDDLE_CENTER);

        linkDataToLabels(adv);

        advertMiddleVertical.addComponent(lblTitle);
        advertMiddleVertical.addComponent(lblPrice);
        advertRightVertical.addComponent(lblCategory);
        advertRightVertical.addComponent(lblSubCategory);
        advertRightVertical.addComponent(lblCity);
        advertRightVertical.addComponent(lblDate);
        advertHorizontal.addComponent(advertLeftVertical);
        advertHorizontal.addComponent(advertMiddleVertical);
        advertHorizontal.addComponent(advertRightVertical);

        advertHorizontal.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                controller.selectedAdvert(adv);
            }
        });

        return advertHorizontal;
    }

    private void linkDataToLabels(Advertisement adv) {
        lblTitle = new Label();
        lblPrice = new Label();
        lblCategory = new Label();
        lblSubCategory = new Label();
        lblCity = new Label();
        lblDate = new Label();

        if (adv.getTitle() != null) {
            lblTitle.setValue(adv.getTitle());
            lblTitle.setWidth(titleLabelWidth);
        }
        if (adv.getPrice() != 0) {
            lblPrice.setValue(String.valueOf(adv.getPrice()) + " " + AppLayout.currency);
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
            lblDate.setValue(AppLayout.formattedDate.format(adv.getRegistrationDate()));
        }
    }

    private void buildFilters() throws Exception {
        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);

        filterForm = new VerticalLayout();
        filterForm.setWidthUndefined();
        filterForm.setSpacing(true);
        filterForm.setMargin(true);

        filterForm.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        lblFilter = new Label(filterLabelValue);
        lblFilter.setSizeUndefined();
        filterForm.addComponent(lblFilter);
        filterForm.addComponent(new Label("<hr />", ContentMode.HTML));

        cmbBxCategory = new ComboBox();
        cmbBxSubCategory = new ComboBox();
        cmbBxCountry = new ComboBox();
        cmbBxCity = new ComboBox();
        cmbBxType = new ComboBox();
        cmbBxState = new ComboBox();
        txtFldMinPrice = new TextField();
        txtFldMaxPrice = new TextField();
        btnFilter = new Button(filterButtonCaption);

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

        filterForm.addComponent(cmbBxCountry);
        filterForm.addComponent(cmbBxCity);
        filterForm.addComponent(cmbBxCategory);
        filterForm.addComponent(cmbBxSubCategory);
        filterForm.addComponent(cmbBxType);
        filterForm.addComponent(cmbBxState);
        filterForm.addComponent(txtFldMinPrice);
        filterForm.addComponent(txtFldMaxPrice);
        filterForm.addComponent(btnFilter);

        filterPanel = new Panel();
        filterPanel.setWidth(filterPanelWidth);
        filterPanel.setContent(filterForm);
        contentLayout.addComponent(filterPanel);
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

    private void addListeners() throws Exception{
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

    private void addSortTypes() throws Exception{
        cmbBxSortType.addItem(sortTypeDateAsc);
        cmbBxSortType.addItem(sortTypeDateDesc);
        cmbBxSortType.addItem(sortTypePriceAsc);
        cmbBxSortType.addItem(sortTypePriceDesc);
    }

    private void addLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setListView(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
        } catch (Exception ex) {
            Logger.getLogger(AdvertListView.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void setNoResult(String noResult) {
        this.noResult = noResult;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public TextField getTxtFldSearch() {
        return txtFldSearch;
    }

    public void setSearchTextFieldWidth(String searchTextFieldWidth) {
        this.searchTextFieldWidth = searchTextFieldWidth;
    }

    public void setSearchButtonValue(String SearchButtonValue) {
        this.SearchButtonValue = SearchButtonValue;
    }

    public void setTitleLabelWidth(String titleLabelWidth) {
        this.titleLabelWidth = titleLabelWidth;
    }

    public void setFilterLabelValue(String filterLabelValue) {
        this.filterLabelValue = filterLabelValue;
    }

    public void setFilterPanelWidth(String filterPanelWidth) {
        this.filterPanelWidth = filterPanelWidth;
    }

    public void setAdvertPanelWidth(String advertPanelWidth) {
        this.advertPanelWidth = advertPanelWidth;
    }

    public void setCategoryCmbbxPrompt(String categoryCmbbxPrompt) {
        this.categoryCmbbxPrompt = categoryCmbbxPrompt;
    }

    public void setSubCategoryCmbbxPrompt(String subCategoryCmbbxPrompt) {
        this.subCategoryCmbbxPrompt = subCategoryCmbbxPrompt;
    }

    public void setTypeCmbbxPromprt(String typeCmbbxPromprt) {
        this.typeCmbbxPromprt = typeCmbbxPromprt;
    }

    public void setCityCmbbxPrompt(String cityCmbbxPrompt) {
        this.cityCmbbxPrompt = cityCmbbxPrompt;
    }

    public void setCountryCmbbxPrompt(String countryCmbbxPrompt) {
        this.countryCmbbxPrompt = countryCmbbxPrompt;
    }

    public void setMaxPriceTxtFldPrompt(String maxPriceTxtFldPrompt) {
        this.maxPriceTxtFldPrompt = maxPriceTxtFldPrompt;
    }

    public void setFilterButtonCaption(String filterButtonCaption) {
        this.filterButtonCaption = filterButtonCaption;
    }

    public void setSearchbarPanelWidth(String searchbarPanelWidth) {
        this.searchbarPanelWidth = searchbarPanelWidth;
    }

    public void setStateCmbbxPrompt(String stateCmbbxPrompt) {
        this.stateCmbbxPrompt = stateCmbbxPrompt;
    }

    public void setMinPriceTxtFldPrompt(String minPriceTxtFldPrompt) {
        this.minPriceTxtFldPrompt = minPriceTxtFldPrompt;
    }

    public String getSortTypePriceAsc() {
        return sortTypePriceAsc;
    }

    public void setSortTypePriceAsc(String sortTypePriceAsc) {
        this.sortTypePriceAsc = sortTypePriceAsc;
    }

    public String getSortTypePriceDesc() {
        return sortTypePriceDesc;
    }

    public void setSortTypePriceDesc(String sortTypePriceDesc) {
        this.sortTypePriceDesc = sortTypePriceDesc;
    }

    public String getSortTypeDateAsc() {
        return sortTypeDateAsc;
    }

    public void setSortTypeDateAsc(String sortTypeDateAsc) {
        this.sortTypeDateAsc = sortTypeDateAsc;
    }

    public String getSortTypeDateDesc() {
        return sortTypeDateDesc;
    }

    public void setSortTypeDateDesc(String sortTypeDateDesc) {
        this.sortTypeDateDesc = sortTypeDateDesc;
    }

    public void setSortCmbbxPrompt(String sortCmbbxPrompt) {
        this.sortCmbbxPrompt = sortCmbbxPrompt;
    }
}
