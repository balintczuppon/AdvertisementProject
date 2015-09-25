/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.Layouts;

import static com.mycompany.advertisementproject.Enums.StyleNames.PICLABEL;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.builder.ElementsBuilder;
import org.vaadin.pagingcomponent.button.ButtonPageNavigator;
import org.vaadin.pagingcomponent.customizer.adaptator.GlobalCustomizer;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

/**
 *
 * @author balin
 */
public class AdvertListLayout {

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

    public VerticalLayout buildView() {
        vl = new VerticalLayout();
        contentLayout = new HorizontalLayout();

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

        fillCategory();
        fillSubCategory();
        
        Tree tree = new Tree();

        for (int i = 0; i < categorys.size(); i++) {
            String category = categorys.get(i);
            tree.addItem(category);
            
            for (int j = 0; j < subCategorys.size(); j++) {
                if(category.equals("Ingatlan")){
                     String subcategory = subCategorys.get(j);
                    tree.addItem(subcategory);
                    tree.setParent(subcategory,category);
                }
                
                for (int k = 0; k < subSubCategorys.size(); k++) {
                    if(subCategorys.equals("alkategória1")){
                        String subsubCategory = subSubCategorys.get(k);
                        tree.addItem(subSubCategorys.get(k));
                        tree.setParent(subSubCategorys.get(k),subCategorys.get(j));
                    }
                }
                
            }       
        }
        
        VerticalLayout filterLayout = new VerticalLayout();
        filterLayout.setSpacing(true);

        ComboBox cmbBxCategory = new ComboBox("Kategória");

        ComboBox cmbBxCity = new ComboBox("Város");

        TextField txtFldMinPrice = new TextField("Minimum Ár");
        TextField txtFldMaxPrice = new TextField("Maximum Ár");

        Button btnFilter = new Button("Szűrés");

        filterLayout.addComponent(cmbBxCategory);
        filterLayout.addComponent(cmbBxCity);
        filterLayout.addComponent(txtFldMinPrice);
        filterLayout.addComponent(txtFldMaxPrice);
        filterLayout.addComponent(btnFilter);

        filterLayout.addComponent(tree);

        return filterLayout;
    }

    private VerticalLayout pageAdverts() {

        final VerticalLayout mainLayout = new VerticalLayout();
        final VerticalLayout itemsArea = new VerticalLayout();

        GlobalCustomizer adaptator = new GlobalCustomizer() {

            @Override
            public Button createButtonFirst() {
                Button button = new Button("<< Első");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonLast() {
                Button button = new Button("Utolsó >>");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonNext() {
                Button button = new Button("Következő >");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonPrevious() {
                Button button = new Button("< Előző");
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

    private void fillCategory() {
        categorys.add("Ingatlan");
        categorys.add("Jármű");
        categorys.add("Műszaki cikk");
    }

    private void fillSubCategory() {
        subCategorys.add("alkategória1");
        subCategorys.add("alkategória2");
    }
    
    private void fillSubSubCategory(){
        subSubCategorys.add("alalal");
    }
}
