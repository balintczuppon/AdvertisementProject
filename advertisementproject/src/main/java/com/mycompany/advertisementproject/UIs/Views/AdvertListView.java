package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.StyleNames.PICLABEL;
import static com.mycompany.advertisementproject.Enums.Views.SELECTED;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.builder.ElementsBuilder;
import org.vaadin.pagingcomponent.button.ButtonPageNavigator;
import org.vaadin.pagingcomponent.customizer.adaptator.GlobalCustomizer;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

@CDIView("ADVERTS")
public class AdvertListView extends VerticalLayout implements View {

    private List<Advertisement> filteredAdverts = new ArrayList<>();
    private List<HorizontalLayout> adverts = new ArrayList<>();

    private HorizontalLayout contentLayout;

    private VerticalLayout advertList;
    private VerticalLayout filterForm;
    private HorizontalLayout searchBarLayout;

    private Panel filterPanel;
    private Panel advertPanel;
    private Panel searchBarPanel;

    private Label lblTitle;
    private Label lblPrice;
    private Label lblCategory;
    private Label lblSubCategory;
    private Label lblCity;
    private Label lblDate;
    private Label lblpicture;
    private Label lblFilter;

    private ComboBox cmbBxCategory;
    private ComboBox cmbBxSubCategory;
    private ComboBox cmbBxCountry;
    private ComboBox cmbBxCity;
    private ComboBox cmbBxType;
    private ComboBox cmbBxState;

    private TextField txtFldMinPrice;
    private TextField txtFldMaxPrice;

    private Button btnFilter;

    private boolean filled = false;

    @Inject
    MaincategoryFacade maincategoryFacade;
    @Inject
    SubcategoryFacade subcategoryFacade;
    @Inject
    PictureFacade pictureFacade;
    @Inject
    AdverttypeFacade adverttypeFacade;
    @Inject
    LocalityFacade localityFacade;
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
        buildView();
        fillComboBoxes();
        addListeners();
    }

    public void buildView() {

        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);

        setSizeFull();
        setMargin(true);
        setSpacing(true);
        loadAdverts();

        addSearchBar();
        searchBarPanel = new Panel();
        searchBarPanel.setWidth("700");
        searchBarPanel.setContent(searchBarLayout);
        addComponent(searchBarPanel);
        setComponentAlignment(searchBarPanel, Alignment.TOP_CENTER);

        addComponent(contentLayout);
        setComponentAlignment(contentLayout, Alignment.TOP_CENTER);

        addFilters();
        filterPanel = new Panel();
        filterPanel.setWidth("300");
        filterPanel.setContent(filterForm);
        contentLayout.addComponent(filterPanel);

        pageAdverts();
        advertPanel = new Panel();
        advertPanel.setWidthUndefined();
        advertPanel.setHeightUndefined();
        advertPanel.setContent(advertList);
        contentLayout.addComponent(advertPanel);

        contentLayout.setComponentAlignment(filterPanel, Alignment.TOP_LEFT);
        contentLayout.setComponentAlignment(advertPanel, Alignment.TOP_LEFT);
    }

    private void addSearchBar() {
        searchBarLayout = new HorizontalLayout();
        searchBarLayout.setSpacing(true);
        searchBarLayout.setMargin(true);
        TextField txtFldSearch = new TextField();
        txtFldSearch.setWidth("300");
        Button btnSearch = new Button("Keresés");
        searchBarLayout.addComponent(txtFldSearch);
        searchBarLayout.addComponent(btnSearch);
    }

    private HorizontalLayout buildAdvert(final Advertisement adv) {

        HorizontalLayout advertHorizontal = new HorizontalLayout();
        advertHorizontal.setSpacing(true);

        VerticalLayout advertLeftVertical = new VerticalLayout();
        VerticalLayout advertMiddleVertical = new VerticalLayout();
        VerticalLayout advertRightVertical = new VerticalLayout();

        advertMiddleVertical.setSpacing(true);
        advertRightVertical.setSpacing(true);
        advertLeftVertical.setSpacing(true);

        lblpicture = new Label();
        lblpicture.setWidth("100");
        lblpicture.setHeight("100");
        lblpicture.setStyleName(PICLABEL.toString());
        advertLeftVertical.addComponent(lblpicture);
        lblTitle = new Label(adv.getTitle());
        lblTitle.setWidth("300");
        advertMiddleVertical.addComponent(lblTitle);
        lblPrice = new Label(adv.getPrice() + "");
        advertMiddleVertical.addComponent(lblPrice);
        for (Maincategory m : maincategoryFacade.findAll()) {
            if (m.getId().equals(adv.getMainCategoryId())) {
                lblCategory = new Label(m.getName());
                advertRightVertical.addComponent(lblCategory);
            }
        }
        for (Subcategory s : subcategoryFacade.findAll()) {
            if (s.getId().equals(adv.getSubCategoryId())) {
                lblSubCategory = new Label(s.getName());
                advertRightVertical.addComponent(lblSubCategory);
            }
        }
        for (Locality l : localityFacade.findAll()) {
            if (l.getId().equals(adv.getLocalityId())) {
                lblCity = new Label(l.getStationname());
                advertRightVertical.addComponent(lblCity);
            }
        }
        String formattedDate = new SimpleDateFormat("yyyy MMMM dd").format(adv.getRegistrationDate());
        lblDate = new Label(formattedDate);
        advertRightVertical.addComponent(lblDate);

        advertHorizontal.addComponent(advertLeftVertical);
        advertHorizontal.addComponent(advertMiddleVertical);
        advertHorizontal.addComponent(advertRightVertical);

        advertHorizontal.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                selectedAdvert(adv);
            }
        });

        return advertHorizontal;
    }

    private void addFilters() {
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
        cmbBxCategory.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbBxCategory.getValue() != null) {
                    fillCmbBxSubCategory(cmbBxCategory.getValue());
                } else {
                    cmbBxSubCategory.setEnabled(false);
                }
            }
        });
        cmbBxSubCategory = new ComboBox();
        cmbBxSubCategory.setInputPrompt("Alkategória");
        cmbBxSubCategory.setEnabled(false);
        cmbBxCountry = new ComboBox();
        cmbBxCountry.setInputPrompt("Megye");
        cmbBxCountry.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (cmbBxCountry.getValue() != null) {
                    fillCmbBxCity(cmbBxCountry.getValue());
                } else {
                    cmbBxCity.setEnabled(false);
                }
            }
        });
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
    }

    private void pageAdverts() {

        advertList = new VerticalLayout();
        advertList.setSpacing(true);
        advertList.setMargin(true);

        final VerticalLayout itemsArea = new VerticalLayout();

        GlobalCustomizer adaptator = new GlobalCustomizer() {

            @Override
            public Button createButtonFirst() {
                Button button = new Button("<<");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonLast() {
                Button button = new Button(">>");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonNext() {
                Button button = new Button(">");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonPrevious() {
                Button button = new Button("<");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Component createFirstSeparator() {
                return null;
            }

            @Override
            public Component createLastSeparator() {
                return null;
            }

            @Override
            public ButtonPageNavigator createButtonPage() {
                ButtonPageNavigator button = new ButtonPageNavigator();
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public void styleButtonPageCurrentPage(ButtonPageNavigator button, int pageNumber) {
                button.setPage(pageNumber, "[" + pageNumber + "]");
                button.addStyleName("styleRed");
                button.focus();
            }

            @Override
            public void styleButtonPageNormal(ButtonPageNavigator button, int pageNumber) {
                button.setPage(pageNumber);
                button.removeStyleName("styleRed");
            }

            @Override
            public void styleTheOthersElements(ComponentsManager manager, ElementsBuilder builder) {
                // Do nothing
            }
        };

        final PagingComponent<HorizontalLayout> pagingComponent = PagingComponent.paginate(adverts)
                .numberOfItemsPerPage(5)
                .numberOfButtonsPage(5)
                .globalCustomizer(adaptator).addListener(new LazyPagingComponentListener<HorizontalLayout>(itemsArea) {

                    Panel panel;

                    @Override
                    protected Collection<HorizontalLayout> getItemsList(int startIndex, int endIndex) {
                        return adverts.subList(startIndex, endIndex);
                    }

                    @Override
                    protected Component displayItem(int index, HorizontalLayout item) {
                        panel = new Panel();
                        panel.setContent(item);
                        return panel;
                    }
                }).build();

        itemsArea.setSpacing(true);
        advertList.addComponent(itemsArea);
        advertList.setComponentAlignment(itemsArea, Alignment.TOP_CENTER);
        advertList.addComponent(pagingComponent);
        advertList.setComponentAlignment(pagingComponent, Alignment.TOP_CENTER);
    }

    private void fillComboBoxes() {
        if (!filled) {
            for (Maincategory m : maincategoryFacade.findAll()) {
                cmbBxCategory.addItem(m.getName());
            }
            for (Advertstate a : advertstateFacade.findAll()) {
                cmbBxState.addItem(a.getName());
            }
            for (Adverttype a : adverttypeFacade.findAll()) {
                cmbBxType.addItem(a.getName());
            }
            for (Locality l : localityFacade.findAll()) {
                cmbBxCountry.addItem(l.getCountry());
            }
            filled = true;
        }
    }

    private void fillCmbBxSubCategory(Object value) {
        cmbBxSubCategory.removeAllItems();
        cmbBxSubCategory.setEnabled(true);
        for (Maincategory mcat : maincategoryFacade.findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : mcat.getSubcategoryCollection()) {
                    cmbBxSubCategory.addItem(s.getName());
                }
            }
        }
    }

    private void fillCmbBxCity(Object value) {
        cmbBxCity.removeAllItems();
        cmbBxCity.setEnabled(true);
        for (Locality loc : localityFacade.findAll()) {
            if (loc.getCountry().equals(value)) {
                cmbBxCity.addItem(loc.getStationname());
            }
        }
    }

    private void addListeners() {
        btnFilter.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                filterAdverts();
            }
        });
    }

    private void filterAdverts() {
        Maincategory mcategory = null;
        int subCategoryId = 0;
        int minPrice = 0;
        int maxPrice = 0;
        Locality locality = null;
        Advertstate state = null;
        Adverttype type = null;

        for (Maincategory m : maincategoryFacade.findAll()) {
            if (!cmbBxCategory.isEmpty()) {
                if (cmbBxCategory.getValue().equals(m.getName())) {
                    mcategory = m;
                }
            }
        }
        for (Subcategory s : subcategoryFacade.findAll()) {
            if (!cmbBxSubCategory.isEmpty()) {
                if (cmbBxSubCategory.getValue().equals(s.getName())) {
                    subCategoryId = s.getId();
                }
            }
        }
        for (Locality l : localityFacade.findAll()) {
            if (!cmbBxCountry.isEmpty()) {
                if (cmbBxCountry.getValue().equals(l.getCountry())) {
                    locality = l;
                }
            }
        }
        for (Locality l : localityFacade.findAll()) {
            if (!cmbBxCity.isEmpty()) {
                if (cmbBxCity.getValue().equals(l.getStationname())) {
                    locality = l;
                }
            }
        }
        for (Advertstate a : advertstateFacade.findAll()) {
            if (!cmbBxState.isEmpty()) {
                if (cmbBxState.getValue().equals(a.getName())) {
                    state = a;
                }
            }
        }
        for (Adverttype a : adverttypeFacade.findAll()) {
            if (!cmbBxType.isEmpty()) {
                if (cmbBxType.getValue().equals(a.getName())) {
                    type = a;
                }
            }
        }

        if (!txtFldMinPrice.isEmpty()) {
            minPrice = Integer.valueOf(txtFldMinPrice.getValue());
        }

        if (!txtFldMaxPrice.isEmpty()) {
            maxPrice = Integer.valueOf(txtFldMaxPrice.getValue());
        }

        filteredAdverts = advertisementFacade.findAdvertsByFilters(
                mcategory,
                subCategoryId,
                locality,
                state,
                type,
                minPrice,
                maxPrice
        );

        adverts.clear();
        for (Advertisement a : filteredAdverts) {
            adverts.add(buildAdvert(a));
        }
        pageAdverts();
        advertPanel.setContent(advertList);
    }

    public void loadAdverts() {
        adverts.clear();
        for (Advertisement a : advertisementFacade.findAll()) {
            adverts.add(buildAdvert(a));
        }
    }

    public void selectedAdvert(Advertisement a) {
        SelectedAdvert.setAdvertisement(a);
        getUI().getNavigator().navigateTo(SELECTED.toString());
    }
}
