package com.mycompany.advertisementproject.Enums.control;

import static com.mycompany.advertisementproject.Enums.StyleNames.PICLABEL;
import static com.mycompany.advertisementproject.Enums.Views.SELECTED;
import com.mycompany.advertisementproject.UIs.Views.AdvertListView;
import com.mycompany.advertisementproject.UIs.Views.SelectedAdvert;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.Locality;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.entities.Subcategory;
import com.vaadin.server.FileResource;
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
import java.util.List;

public class AdvertListController {

    private List<Advertisement> filteredAdverts = new ArrayList<>();
    private List<HorizontalLayout> adverts = new ArrayList<>();

    private boolean filled = false;

    AdvertListView advertListView;

    public AdvertListController(AdvertListView view) {
        this.advertListView = view;
    }

    public void fillCmbBxSubCategory(Object value) {
        advertListView.getCmbBxSubCategory().removeAllItems();
        advertListView.getCmbBxSubCategory().setEnabled(true);
        for (Maincategory mcat : advertListView.getMaincategoryFacade().findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : mcat.getSubcategoryCollection()) {
                    advertListView.getCmbBxSubCategory().addItem(s.getName());
                }
            }
        }
    }

    public void fillCmbBxCity(Object value) {
        advertListView.getCmbBxCity().removeAllItems();
        advertListView.getCmbBxCity().setEnabled(true);
        for (Locality loc : advertListView.getLocalityFacade().findAll()) {
            if (loc.getCountry().equals(value)) {
                advertListView.getCmbBxCity().addItem(loc.getStationname());
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

    public void fillComboBoxes() {
        if (!filled) {
            for (Maincategory m : advertListView.getMaincategoryFacade().findAll()) {
                advertListView.getCmbBxCategory().addItem(m.getName());
            }
            for (Advertstate a : advertListView.getAdvertstateFacade().findAll()) {
                advertListView.getCmbBxState().addItem(a.getName());
            }
            for (Adverttype a : advertListView.getAdverttypeFacade().findAll()) {
                advertListView.getCmbBxType().addItem(a.getName());
            }
            for (Locality l : advertListView.getLocalityFacade().findAll()) {
                advertListView.getCmbBxCountry().addItem(l.getCountry());
            }
            filled = true;
        }
    }

    public void filterAdverts() {
        Maincategory mcategory = null;
        int subCategoryId = 0;
        int minPrice = 0;
        int maxPrice = 0;
        Locality locality = null;
        Advertstate state = null;
        Adverttype type = null;

        for (Maincategory m : advertListView.getMaincategoryFacade().findAll()) {
            if (!advertListView.getCmbBxCategory().isEmpty()) {
                if (advertListView.getCmbBxCategory().getValue().equals(m.getName())) {
                    mcategory = m;
                }
            }
        }
        for (Subcategory s : advertListView.getSubcategoryFacade().findAll()) {
            if (!advertListView.getCmbBxSubCategory().isEmpty()) {
                if (advertListView.getCmbBxSubCategory().getValue().equals(s.getName())) {
                    subCategoryId = s.getId();
                }
            }
        }
        for (Locality l : advertListView.getLocalityFacade().findAll()) {
            if (!advertListView.getCmbBxCountry().isEmpty()) {
                if (advertListView.getCmbBxCountry().getValue().equals(l.getCountry())) {
                    locality = l;
                }
            }
        }
        for (Locality l : advertListView.getLocalityFacade().findAll()) {
            if (!advertListView.getCmbBxCity().isEmpty()) {
                if (advertListView.getCmbBxCity().getValue().equals(l.getStationname())) {
                    locality = l;
                }
            }
        }
        for (Advertstate a : advertListView.getAdvertstateFacade().findAll()) {
            if (!advertListView.getCmbBxState().isEmpty()) {
                if (advertListView.getCmbBxState().getValue().equals(a.getName())) {
                    state = a;
                }
            }
        }
        for (Adverttype a : advertListView.getAdverttypeFacade().findAll()) {
            if (!advertListView.getCmbBxType().isEmpty()) {
                if (advertListView.getCmbBxType().getValue().equals(a.getName())) {
                    type = a;
                }
            }
        }

        if (!advertListView.getTxtFldMinPrice().isEmpty()) {
            minPrice = Integer.valueOf(advertListView.getTxtFldMinPrice().getValue());
        }

        if (!advertListView.getTxtFldMaxPrice().isEmpty()) {
            maxPrice = Integer.valueOf(advertListView.getTxtFldMaxPrice().getValue());
        }

        filteredAdverts = advertListView.getAdvertisementFacade().findAdvertsByFilters(
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
            adverts.add(advertListView.buildSingleAdvert(a));
        }
        advertListView.buildAdverts();
        advertListView.getAdvertPanel().setContent(advertListView.getAdvertList());
    }

    public void loadAdverts() {
        adverts.clear();
        for (Advertisement a : advertListView.getAdvertisementFacade().findAll()) {
            adverts.add(advertListView.buildSingleAdvert(a));
        }
    }

    public void selectedAdvert(Advertisement adv) {
        SelectedAdvert.setAdvertisement(adv);
        advertListView.getUI().getNavigator().navigateTo(SELECTED.toString());
    }

    public void addPictureToAdvert(Embedded image, Advertisement adv) {
        try {
            List<Picture> pictures = (List<Picture>) adv.getPictureCollection();
            File file = new File(pictures.get(0).getAccessPath());
            FileResource source = new FileResource(file);
            image.setSource(source);
            image.setWidth("128");
            image.setHeight("96");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
