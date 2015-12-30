package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.Enums.SessionAttributes.SELECTEDADVERT;
import static com.mycompany.advertisementproject.Enums.Views.SELECTED;
import static com.mycompany.advertisementproject.Enums.SortAttributes.*;
import com.mycompany.advertisementproject.Tools.AdvertComparator;
import com.mycompany.advertisementproject.vaadinviews.AdvertListView;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.City;
import com.mycompany.advertisementproject.entities.Country;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.entities.Subcategory;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.builder.ElementsBuilder;
import org.vaadin.pagingcomponent.button.ButtonPageNavigator;
import org.vaadin.pagingcomponent.customizer.adaptator.GlobalCustomizer;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdvertListController {

    private List<Advertisement> adverts = new ArrayList<>();
    private final List<HorizontalLayout> advertlayouts = new ArrayList<>();

    private String filterText;

    private boolean filled = false;

    private final AdvertListView view;

    public AdvertListController(AdvertListView view) {
        this.view = view;
    }

    public void fillCmbBxSubCategory(Object value) {
        view.getCmbBxSubCategory().removeAllItems();
        view.getCmbBxSubCategory().setEnabled(true);
        for (Maincategory mcat : view.getMaincategoryFacade().findAll()) {
            if (mcat.getName().equals(value)) {
                view.getCmbBxSubCategory().addItems(mcat.getSubcategoryCollection());
            }
        }
    }

    public void fillCmbBxCity(Object value) {
        view.getCmbBxCity().removeAllItems();
        view.getCmbBxCity().setEnabled(true);
        for (Country c : view.getCountryFacade().findAll()) {
            if (c.getCountryName().equals(value)) {
                view.getCmbBxCity().addItems(c.getCityCollection());
            }
        }
    }

    public void pageAdverts(VerticalLayout advertList, VerticalLayout itemsArea) {
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

        final PagingComponent<HorizontalLayout> pagingComponent = PagingComponent.paginate(advertlayouts)
                .numberOfItemsPerPage(5)
                .numberOfButtonsPage(5)
                .globalCustomizer(adaptator).addListener(new LazyPagingComponentListener<HorizontalLayout>(itemsArea) {

                    Panel panel;

                    @Override
                    protected Collection<HorizontalLayout> getItemsList(int startIndex, int endIndex) {
                        return advertlayouts.subList(startIndex, endIndex);
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

    public void fillComboBoxes() {
        if (!filled) {
            for (Maincategory m : view.getMaincategoryFacade().findAll()) {
                view.getCmbBxCategory().addItem(m.getName());
            }
            for (Advertstate a : view.getAdvertstateFacade().findAll()) {
                view.getCmbBxState().addItem(a.getName());
            }
            for (Adverttype a : view.getAdverttypeFacade().findAll()) {
                view.getCmbBxType().addItem(a.getName());
            }
            for (Country c : view.getCountryFacade().findAll()) {
                view.getCmbBxCountry().addItem(c.getCountryName());
            }
            filled = true;
        }
    }

    public void filterAdverts() throws Exception {
        Maincategory mcategory = null;
        Subcategory subCategory = null;
        Country country = null;
        City city = null;
        Advertstate state = null;
        Adverttype type = null;
        int minPrice = 0;
        int maxPrice = 0;

        for (Maincategory m : view.getMaincategoryFacade().findAll()) {
            if (!view.getCmbBxCategory().isEmpty()) {
                if (view.getCmbBxCategory().getValue().equals(m.getName())) {
                    mcategory = m;
                }
            }
        }
        for (Subcategory s : view.getSubcategoryFacade().findAll()) {
            if (!view.getCmbBxSubCategory().isEmpty()) {
                if (view.getCmbBxSubCategory().getValue().equals(s.getName())) {
                    subCategory = s;
                }
            }
        }
        for (City c : view.getCityFacade().findAll()) {
            if (!view.getCmbBxCity().isEmpty()) {
                if (view.getCmbBxCity().getValue().equals(c.getCityName())) {
                    city = c;
                }
            }
        }

        for (Country c : view.getCountryFacade().findAll()) {
            if (!view.getCmbBxCountry().isEmpty()) {
                if (view.getCmbBxCountry().getValue().equals(c.getCountryName())) {
                    country = c;
                }
            }
        }

        for (Advertstate a : view.getAdvertstateFacade().findAll()) {
            if (!view.getCmbBxState().isEmpty()) {
                if (view.getCmbBxState().getValue().equals(a.getName())) {
                    state = a;
                }
            }
        }
        for (Adverttype a : view.getAdverttypeFacade().findAll()) {
            if (!view.getCmbBxType().isEmpty()) {
                if (view.getCmbBxType().getValue().equals(a.getName())) {
                    type = a;
                }
            }
        }

        if (!view.getTxtFldMinPrice().isEmpty()) {
            minPrice = Integer.valueOf(view.getTxtFldMinPrice().getValue());
        }

        if (!view.getTxtFldMaxPrice().isEmpty()) {
            maxPrice = Integer.valueOf(view.getTxtFldMaxPrice().getValue());
        }

        adverts = view.getAdvertisementFacade().findAdvertsByFilters(
                mcategory, subCategory, country, city, state, type, minPrice, maxPrice, filterText
        );
        loadAdverts();
    }

    private void addLabelNoResult() {
        Label lblNoResult = new Label(view.getNoResult());
        view.getAdvertPanel().setContent(lblNoResult);
    }

    public void fillAdverts() {
        adverts = view.getAdvertisementFacade().findAll();
    }

    public void loadAdverts() throws Exception {
        advertlayouts.clear();
        for (Advertisement a : adverts) {
            advertlayouts.add(view.buildSingleAdvert(a));
        }
        if (adverts.isEmpty()) {
            addLabelNoResult();
        } else {
            view.buildAdverts();
            view.getAdvertPanel().setContent(view.getAdvertList());
        }
    }

    public void selectedAdvert(Advertisement adv) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(SELECTEDADVERT.toString(),adv);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(SELECTED.toString());
    }

    public void addPictureToAdvert(Embedded image, Advertisement adv) {
        try {
            List<Picture> pictures = (List<Picture>) adv.getPictureCollection();
            if (pictures.size() > 0) {
                File file = new File(pictures.get(0).getAccessPath());
                FileResource source = new FileResource(file);
                image.setSource(source);
                image.setWidth(view.getImageWidth());
                image.setHeight(view.getImageHeight());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void searchAdverts(String value) throws Exception {
        filterText = value;
        if (!filterText.isEmpty()) {
            adverts = view.getAdvertisementFacade().findByText(filterText);
            loadAdverts();
        } else {
            filterText = null;
            loadAdverts();
        }
    }

    private void sortAdverts(List<Advertisement> list, String sortAttribute, boolean ascending) {
        Collections.sort(list, new AdvertComparator(sortAttribute, ascending));
    }

    public void sort(Object value) throws Exception {
        if (String.valueOf(value).equals(view.getSortTypeDateAsc())) {
            sortAdverts(adverts, DATE.toString(), Boolean.TRUE);
        }
        if (String.valueOf(value).equals(view.getSortTypeDateDesc())) {
            sortAdverts(adverts, DATE.toString(), Boolean.FALSE);
        }
        if (String.valueOf(value).equals(view.getSortTypePriceAsc())) {
            sortAdverts(adverts, PRICE.toString(), Boolean.TRUE);
        }
        if (String.valueOf(value).equals(view.getSortTypePriceDesc())) {
            sortAdverts(adverts, PRICE.toString(), Boolean.FALSE);
        }
        loadAdverts();
    }
}
