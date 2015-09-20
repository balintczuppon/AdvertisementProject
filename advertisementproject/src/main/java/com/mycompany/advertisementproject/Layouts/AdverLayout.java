/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.Layouts;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;
import org.vaadin.pagingcomponent.listener.impl.SimplePagingComponentListener;

/**
 *
 * @author balin
 */
public class AdverLayout extends HorizontalLayout {

    List<HorizontalLayout> adverts = new ArrayList<>();

    String title = "Eladó Autó";
    String price = "230000";
    String category = "Jármű";
    String subcategory = "Személygépkocsi";
    String city = "Budapest";
    String date = "2015.02.12";

    Label lblTitle;
    Label lblPrice;
    Label lblCategory;
    Label lblSubCategory;
    Label lblCity;
    Label lblDate;

    Label lblpicture = new Label();

    public AdverLayout() {
        fillExamples();
        this.addComponent(addFilters());
        this.addComponent(pageAdverts());
    }

    public HorizontalLayout buildAdvert1() {

        HorizontalLayout advertHorizontal = new HorizontalLayout();
        advertHorizontal.setSpacing(true);

        VerticalLayout advertLeftVertical = new VerticalLayout();
        VerticalLayout advertMiddleVertical = new VerticalLayout();
        VerticalLayout advertRightVertical = new VerticalLayout();

        advertMiddleVertical.setSpacing(true);

        lblpicture = new Label();
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

    public HorizontalLayout buildAdvert2() {

        HorizontalLayout advertHorizontal = new HorizontalLayout();
        advertHorizontal.setSpacing(true);

        VerticalLayout advertLeftVertical = new VerticalLayout();
        VerticalLayout advertMiddleVertical = new VerticalLayout();
        VerticalLayout advertRightVertical = new VerticalLayout();

        advertMiddleVertical.setSpacing(true);

        lblpicture = new Label();
        advertLeftVertical.addComponent(lblpicture);
        lblTitle = new Label("Kiadó Lakás Pécs");
        lblTitle.setWidth("300");
        advertMiddleVertical.addComponent(lblTitle);
        lblPrice = new Label("134520");
        advertMiddleVertical.addComponent(lblPrice);
        lblCategory = new Label("ingatlan");
        advertRightVertical.addComponent(lblCategory);
        lblSubCategory = new Label("lakás");
        advertRightVertical.addComponent(lblSubCategory);
        lblCity = new Label("Pécs");
        advertRightVertical.addComponent(lblCity);
        lblDate = new Label("2013.03.23");
        advertRightVertical.addComponent(lblDate);

        advertHorizontal.addComponent(advertLeftVertical);
        advertHorizontal.addComponent(advertMiddleVertical);
        advertHorizontal.addComponent(advertRightVertical);

        return advertHorizontal;
    }

    private VerticalLayout addFilters() {
        VerticalLayout filterLayout = new VerticalLayout();
        return filterLayout;
    }

    private VerticalLayout pageAdverts() {

        final VerticalLayout mainLayout = new VerticalLayout();
        final VerticalLayout itemsArea = new VerticalLayout();

        final PagingComponent<HorizontalLayout> pagingComponent = PagingComponent.paginate(adverts).addListener(new LazyPagingComponentListener<HorizontalLayout>(itemsArea) {

            @Override
            protected Collection<HorizontalLayout> getItemsList(int startIndex, int endIndex) {
                return adverts.subList(startIndex, endIndex);
            }

            @Override
            protected Component displayItem(int index, HorizontalLayout item) {
                return item;
            }
        }).build();

        mainLayout.addComponent(itemsArea);
        mainLayout.setComponentAlignment(itemsArea, Alignment.TOP_CENTER);
        mainLayout.addComponent(pagingComponent);
        return mainLayout;
    }

    private void fillExamples() {
        for (int i = 0; i < 20; i++) {
            adverts.add(buildAdvert1());
        }
        for (int i = 0; i < 20; i++) {
            adverts.add(buildAdvert2());
        }
    }
}
