package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.control.AdvertListController;
import com.mycompany.advertisementproject.entities.*;
import com.mycompany.advertisementproject.facades.*;
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
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

    private final SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy MMMM dd");

    private AdvertListController controller;

    private HorizontalLayout contentLayout;
    private HorizontalLayout searchBarLayout;

    private VerticalLayout advertList;
    private VerticalLayout filterForm;

    private Panel filterPanel;
    private Panel advertPanel;
    private Panel searchBarPanel;

    private Label lblSort;
    private Label lblTitle;
    private Label lblPrice;
    private Label lblCategory;
    private Label lblSubCategory;
    private Label lblCity;
    private Label lblDate;
    private Label lblFilter;

    private Embedded image;

    private ComboBox cmbBxSortType;
    private ComboBox cmbBxCategory;
    private ComboBox cmbBxSubCategory;
    private ComboBox cmbBxCountry;
    private ComboBox cmbBxCity;
    private ComboBox cmbBxType;
    private ComboBox cmbBxState;

    private TextField txtFldMinPrice;
    private TextField txtFldMaxPrice;

    private Button btnFilter;

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
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        buildView();
        addListeners();
    }

    public void buildView() {
        controller = new AdvertListController(this);
        controller.loadAdverts();
        buildSearchBar();
        buildFilters();
        buildAdverts();
        layoutSettings();
        controller.fillComboBoxes();
    }

    private void layoutSettings() {
        setComponentAlignment(searchBarPanel, Alignment.TOP_CENTER);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        contentLayout.setComponentAlignment(filterPanel, Alignment.TOP_LEFT);
        contentLayout.setComponentAlignment(advertPanel, Alignment.TOP_LEFT);
    }

    private void buildSearchBar() {
        searchBarLayout = new HorizontalLayout();
        searchBarLayout.setSpacing(true);
        searchBarLayout.setMargin(true);
        TextField txtFldSearch = new TextField();
        txtFldSearch.setWidth("300");
        Button btnSearch = new Button("Keresés");
        searchBarLayout.addComponent(txtFldSearch);
        searchBarLayout.addComponent(btnSearch);

        searchBarPanel = new Panel();
        searchBarPanel.setWidth("700");
        searchBarPanel.setContent(searchBarLayout);
        addComponent(searchBarPanel);
    }

    public HorizontalLayout buildSingleAdvert(final Advertisement adv) {

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

        lblTitle = new Label(adv.getTitle());
        lblTitle.setWidth("300");
        lblPrice = new Label(String.valueOf(adv.getPrice()));
        lblCategory = new Label(adv.getMainCategoryId().getName());
        lblSubCategory = new Label(adv.getSubCategoryId().getName());
//        lblCity = new Label(adv.getCityId().getCityName());
        lblDate = new Label(formattedDate.format(adv.getRegistrationDate()));

        advertMiddleVertical.addComponent(lblTitle);
        advertMiddleVertical.addComponent(lblPrice);
        advertRightVertical.addComponent(lblCategory);
        advertRightVertical.addComponent(lblSubCategory);
//        advertRightVertical.addComponent(lblCity);
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

    private void buildFilters() {
        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);

        filterForm = new VerticalLayout();
        filterForm.setWidthUndefined();
        filterForm.setSpacing(true);
        filterForm.setMargin(true);

        lblFilter = new Label("Részletes keresés");
        lblFilter.setSizeUndefined();

        filterForm.addComponent(lblFilter);
        filterForm.addComponent(new Label("<hr />", ContentMode.HTML));

        cmbBxCategory = new ComboBox();
        cmbBxCategory.setInputPrompt("Kategória");
        cmbBxSubCategory = new ComboBox();
        cmbBxSubCategory.setInputPrompt("Alkategória");
        cmbBxSubCategory.setEnabled(false);
        cmbBxCountry = new ComboBox();
        cmbBxCountry.setInputPrompt("Megye");
        cmbBxCity = new ComboBox();
        cmbBxCity.setInputPrompt("Város");
        cmbBxCity.setEnabled(false);
        cmbBxType = new ComboBox();
        cmbBxType.setInputPrompt("Típus");
        cmbBxState = new ComboBox();
        cmbBxState.setInputPrompt("Állapot");
        txtFldMinPrice = new TextField();
        txtFldMinPrice.setInputPrompt("Minimum Ár");
        txtFldMaxPrice = new TextField();
        txtFldMaxPrice.setInputPrompt("Maximum Ár");
        btnFilter = new Button("Szűrés");

        filterForm.addComponent(cmbBxCountry);
        filterForm.addComponent(cmbBxCity);
        filterForm.addComponent(cmbBxCategory);
        filterForm.addComponent(cmbBxSubCategory);
        filterForm.addComponent(cmbBxType);
        filterForm.addComponent(cmbBxState);
        filterForm.addComponent(txtFldMinPrice);
        filterForm.addComponent(txtFldMaxPrice);
        filterForm.addComponent(btnFilter);

        filterForm.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        filterPanel = new Panel();
        filterPanel.setWidth("300");
        filterPanel.setContent(filterForm);
        contentLayout.addComponent(filterPanel);
    }

    public void buildAdverts() {

        advertList = new VerticalLayout();
        
        advertList.setSpacing(true);
        advertList.setMargin(true);

        final VerticalLayout itemsArea = new VerticalLayout();
        controller.pageAdverts(advertList, itemsArea);

        if (advertPanel == null) {
            advertPanel = new Panel();
            advertPanel.setWidth("700");
            advertPanel.setHeightUndefined();
            advertPanel.setContent(advertList);
            contentLayout.addComponent(advertPanel);
            addComponent(contentLayout);
        }
    }

    private void addListeners() {
        btnFilter.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.filterAdverts();
            }
        });

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

}
