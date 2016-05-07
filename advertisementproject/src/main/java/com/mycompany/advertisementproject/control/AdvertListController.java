package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.SELECTEDADVERT;
import static com.mycompany.advertisementproject.enumz.Views.SELECTED;
import static com.mycompany.advertisementproject.enumz.SortAttributes.*;
import com.mycompany.advertisementproject.toolz.AdvertComparator;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertListView;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Advertstate;
import com.mycompany.advertisementproject.model.entities.Adverttype;
import com.mycompany.advertisementproject.model.entities.City;
import com.mycompany.advertisementproject.model.entities.Country;
import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Picture;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.toolz.MyAdvertPager;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdvertListController {

    private MaincategoryFacade maincategoryFacade;
    private SubcategoryFacade subcategoryFacade;
    private AdverttypeFacade adverttypeFacade;
    private CountryFacade countryFacade;
    private CityFacade cityFacade;
    private AdvertisementFacade advertisementFacade;
    private AdvertstateFacade advertstateFacade;

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
        if (value != null && !((Maincategory) (value)).getSubcategoryCollection().isEmpty()) {
            view.getCmbBxSubCategory().addItems(((Maincategory) (value)).getSubcategoryCollection());
        }
    }

    public void fillCmbBxCity(Object value) {
        view.getCmbBxCity().removeAllItems();
        view.getCmbBxCity().setEnabled(true);
        if (value != null && !((Country) value).getCityCollection().isEmpty()) {
            view.getCmbBxCity().addItems(((Country) value).getCityCollection());
        }
    }

    public void pageAdverts(VerticalLayout advertList, VerticalLayout itemsArea) {
        MyAdvertPager advertPager = new MyAdvertPager();
        advertPager.pageAdverts(advertList, itemsArea, advertlayouts);
    }

    public void fillComboBoxes() {
        if (!filled) {
            view.getCmbBxCategory().addItems(maincategoryFacade.findAll());
            view.getCmbBxState().addItems(advertstateFacade.findAll());
            view.getCmbBxType().addItems(adverttypeFacade.findAll());
            view.getCmbBxCountry().addItems(countryFacade.findAll());
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

        if (!view.getCmbBxCategory().isEmpty()) {
            mcategory = maincategoryFacade.getCategoryByName(view.getCmbBxCategory().getValue().toString());
        }

        if (!view.getCmbBxSubCategory().isEmpty()) {
            subCategory = subcategoryFacade.getSubCateogryByName(view.getCmbBxSubCategory().getValue().toString());
        }

        if (!view.getCmbBxCity().isEmpty()) {
            city = cityFacade.getCityByName(view.getCmbBxCity().getValue().toString());
        }

        if (!view.getCmbBxCountry().isEmpty()) {
            country = countryFacade.getCountryByName(view.getCmbBxCountry().getValue().toString());
        }

        if (!view.getCmbBxState().isEmpty()) {
            state = advertstateFacade.getStateByName(view.getCmbBxState().getValue().toString());
        }

        if (!view.getCmbBxType().isEmpty()) {

            type = adverttypeFacade.getTypeByName(view.getCmbBxType().getValue().toString());
        }

        if (!view.getTxtFldMinPrice().isEmpty()) {
            minPrice = Integer.valueOf(view.getTxtFldMinPrice().getValue());
        }

        if (!view.getTxtFldMaxPrice().isEmpty()) {
            maxPrice = Integer.valueOf(view.getTxtFldMaxPrice().getValue());
        }

        adverts = advertisementFacade.findAdvertsByFilters(
                mcategory, subCategory, country, city, state, type, minPrice, maxPrice, filterText
        );
        loadAdverts();
    }

    private void addLabelNoResult() {
        Label lblNoResult = new Label(view.getNoResult());
        view.getAdvertPanel().setContent(lblNoResult);
    }

    public void fillAdverts() {
        adverts = advertisementFacade.findAll();
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
            VaadinSession.getCurrent().setAttribute(SELECTEDADVERT.toString(), adv);
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
            adverts = advertisementFacade.findByText(filterText);
            loadAdverts();
        } else {
            filterText = null;
            fillAdverts();
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

    public void setMaincategoryFacade(MaincategoryFacade maincategoryFacade) {
        this.maincategoryFacade = maincategoryFacade;
    }

    public void setSubcategoryFacade(SubcategoryFacade subcategoryFacade) {
        this.subcategoryFacade = subcategoryFacade;
    }

    public void setAdverttypeFacade(AdverttypeFacade adverttypeFacade) {
        this.adverttypeFacade = adverttypeFacade;
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    public void setAdvertisementFacade(AdvertisementFacade advertisementFacade) {
        this.advertisementFacade = advertisementFacade;
    }

    public void setAdvertstateFacade(AdvertstateFacade advertstateFacade) {
        this.advertstateFacade = advertstateFacade;
    }

}
