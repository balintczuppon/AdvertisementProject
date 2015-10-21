package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.StyleNames.PICLABEL;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.Locality;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.entities.Subcategory;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.facades.LocalityFacade;
import com.mycompany.advertisementproject.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.facades.PictureFacade;
import com.mycompany.advertisementproject.facades.SubcategoryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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

    private boolean filled = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Under Developement.");
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        this.setSizeFull();
        this.addComponent(buildView());
        fillComboBoxes();
        addListeners();
    }

    private VerticalLayout vl;
    private HorizontalLayout contentLayout;
    private HorizontalLayout searchBarLayout;

    private List<HorizontalLayout> adverts = new ArrayList<>();

    private List<String> categorys = new ArrayList<>();
    private List<String> subCategorys = new ArrayList<>();
    private List<String> subSubCategorys = new ArrayList<>();

    private String title = "Eladó Autó";
    private String price = "230000";
    private String category = "Jármű";
    private String subcategory = "Személygépkocsi";
    private String city = "Budapest";
    private String date = "2015.02.12";

    private Label lblTitle;
    private Label lblPrice;
    private Label lblCategory;
    private Label lblSubCategory;
    private Label lblCity;
    private Label lblDate;

    private Label lblpicture = new Label();

    private ComboBox cmbBxCategory;
    private ComboBox cmbBxSubCategory;
    private ComboBox cmbBxCity;
    private ComboBox cmbBxType;
    private ComboBox cmbBxState;
    private TextField txtFldMinPrice;
    private TextField txtFldMaxPrice;
    private Button btnFilter;

    public VerticalLayout buildView() {
        vl = new VerticalLayout();
        contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);

        fillExamples();

        contentLayout.addComponent(addFilters());
        contentLayout.addComponent(pageAdverts());
        vl.addComponent(addSearchBar());
        vl.addComponent(contentLayout);

        vl.setComponentAlignment(searchBarLayout, Alignment.TOP_CENTER);
        vl.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
        return vl;
    }

    private HorizontalLayout addSearchBar() {
        searchBarLayout = new HorizontalLayout();

        searchBarLayout.setSpacing(true);
        searchBarLayout.setMargin(true);
        TextField txtFldSearch = new TextField();
        txtFldSearch.setWidth("300");
        Button btnSearch = new Button("Keresés");
        searchBarLayout.addComponent(txtFldSearch);
        searchBarLayout.addComponent(btnSearch);

        return searchBarLayout;
    }

    private HorizontalLayout buildAdvert1() {

        HorizontalLayout advertHorizontal = new HorizontalLayout();
        advertHorizontal.setSpacing(true);

        VerticalLayout advertLeftVertical = new VerticalLayout();
        VerticalLayout advertMiddleVertical = new VerticalLayout();
        VerticalLayout advertRightVertical = new VerticalLayout();

        advertMiddleVertical.setSpacing(true);

        lblpicture = new Label();
        lblpicture.setWidth("100");
        lblpicture.setHeight("100");
        lblpicture.setStyleName(PICLABEL.toString());
        advertLeftVertical.addComponent(lblpicture);
        lblTitle = new Label(title);
        lblTitle.setWidth("300");
        advertMiddleVertical.addComponent(lblTitle);
        lblPrice = new Label(price);
        advertMiddleVertical.addComponent(lblPrice);
        lblCategory = new Label(category);
        advertRightVertical.addComponent(lblCategory);
        lblSubCategory = new Label(subcategory);
        advertRightVertical.addComponent(lblSubCategory);
        lblCity = new Label(city);
        advertRightVertical.addComponent(lblCity);
        lblDate = new Label(date);
        advertRightVertical.addComponent(lblDate);

        advertHorizontal.addComponent(advertLeftVertical);
        advertHorizontal.addComponent(advertMiddleVertical);
        advertHorizontal.addComponent(advertRightVertical);

        return advertHorizontal;
    }

    private VerticalLayout addFilters() {
        VerticalLayout filterLayout = new VerticalLayout();
        filterLayout.setSpacing(true);

        cmbBxCategory = new ComboBox("Kategória");
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
        cmbBxSubCategory = new ComboBox("AlKategória");
        cmbBxSubCategory.setEnabled(false);
        cmbBxCity = new ComboBox("Város");
        cmbBxType = new ComboBox("Típus");
        cmbBxState = new ComboBox("Állapot");
        txtFldMinPrice = new TextField("Minimum Ár");
        txtFldMaxPrice = new TextField("Maximum Ár");
        btnFilter = new Button("Szűrés");

        filterLayout.addComponent(cmbBxCity);
        filterLayout.addComponent(cmbBxCategory);
        filterLayout.addComponent(cmbBxSubCategory);
        filterLayout.addComponent(cmbBxType);
        filterLayout.addComponent(cmbBxState);
        filterLayout.addComponent(txtFldMinPrice);
        filterLayout.addComponent(txtFldMaxPrice);
        filterLayout.addComponent(btnFilter);

        return filterLayout;
    }

    private VerticalLayout pageAdverts() {

        final VerticalLayout mainLayout = new VerticalLayout();
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
        mainLayout.addComponent(itemsArea);
        mainLayout.setComponentAlignment(itemsArea, Alignment.TOP_CENTER);
        mainLayout.addComponent(pagingComponent);
        return mainLayout;
    }

    private void fillExamples() {
        for (int i = 0; i < 200; i++) {
            adverts.add(buildAdvert1());
        }
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
                cmbBxCity.addItem(l.getStationname());
            }
            filled = true;
        }
    }

    private void fillCmbBxSubCategory(Object value) {
        cmbBxSubCategory.removeAllItems();
        cmbBxSubCategory.setEnabled(true);
        for (Maincategory mcat : maincategoryFacade.findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : subcategoryFacade.findByMainCategoryId(mcat)) {
                    cmbBxSubCategory.addItem(s.getName());
                }
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
        Locality locality = null;
        Advertstate state = null;
        Adverttype type = null;
        
        for (Maincategory m : maincategoryFacade.findAll()) {
            if (cmbBxCategory.getValue().equals(m.getName())) {
                mcategory = m;
            }
        }
        for (Subcategory s : subcategoryFacade.findAll()) {
            if (cmbBxSubCategory.getValue().equals(s.getName())) {
                subCategoryId = s.getId();
            }
        }
        for (Locality l : localityFacade.findAll()) {
            if (cmbBxCity.getValue().equals(l.getStationname())) {
                locality = l;
            }
        }
        for (Advertstate a : advertstateFacade.findAll()) {
            if (cmbBxState.getValue().equals(a.getName())) {
                state = a;
            }
        }
        for (Adverttype a : adverttypeFacade.findAll()) {
            if (cmbBxType.getValue().equals(a.getName())) {
                type = a;
            }
        }

        filteredAdverts = advertisementFacade.findAdvertsByFilters(
                mcategory,
                subCategoryId,
                locality,
                state,
                type,
                txtFldMinPrice.getValue(),
                txtFldMaxPrice.getValue()
        );
        
        int i = 0;
        for (Advertisement a : filteredAdverts) {
            addComponent(new Label(i+""));
        }
    }
}
