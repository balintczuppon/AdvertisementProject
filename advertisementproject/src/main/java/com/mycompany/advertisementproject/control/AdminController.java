package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.model.entities.Advertstate;
import com.mycompany.advertisementproject.model.entities.Adverttype;
import com.mycompany.advertisementproject.model.entities.City;
import com.mycompany.advertisementproject.model.entities.Country;
import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.vaadin.ui.ComboBox;

public class AdminController {

    private CountryFacade countryFacade;
    private CityFacade cityFacade;
    private MaincategoryFacade categoryFacade;
    private SubcategoryFacade subcategoryFacade;
    private AdvertstateFacade stateFacade;
    private AdverttypeFacade typeFacade;

    public void createCountry(String name) throws Exception {
        Country country = new Country();
        country.setCountryName(name);
        countryFacade.create(country);
    }

    public void deleteCountry(String name) throws Exception {
        Country country = countryFacade.getCountryByName(name);
        countryFacade.remove(country);
    }

    public void modifyCountry(String oldName, String newName) throws Exception {
        Country country = countryFacade.getCountryByName(oldName);
        country.setCountryName(newName);
        countryFacade.edit(country);
    }

    public void popluateCountryFields(ComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItems(countryFacade.findAll());
    }
    
    
    
    
    
    

    public void createCity(String value) throws Exception {
        City city = new City();
        city.setCityName(value);
        city.setCountryId(null);
        cityFacade.create(city);
    }

    public void deleteCity(String name) throws Exception {
        City city = cityFacade.getCityByName(name);
        cityFacade.remove(city);
    }

    public void modifyCity(String oldName, String newName) throws Exception {
        City city = cityFacade.getCityByName(oldName);
        city.setCityName(newName);
        cityFacade.edit(city);
    }

    public void popluateCityFields(ComboBox comboBox) {
        comboBox.addItems(cityFacade.findAll());
    }
    
    public void createCategory(String value) throws Exception {
        Maincategory category = new Maincategory();
        category.setName(value);
        categoryFacade.create(category);
    }

    public void deleteCategory(String name) throws Exception {
        Maincategory category = categoryFacade.getCategoryByName(name);
        categoryFacade.remove(category);
    }

    public void modifyCategory(String oldName, String newName) throws Exception {
        Maincategory cateogry = categoryFacade.getCategoryByName(oldName);
        cateogry.setName(newName);
        categoryFacade.edit(cateogry);
    }

    public void popluateCategoryFields(ComboBox comboBox) {
        comboBox.addItems(categoryFacade.findAll());
    }

    public void createSubCategory(String value) throws Exception {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(value);
        subcategory.setMainCategoryId(null);
        subcategoryFacade.create(subcategory);
    }

    public void deleteSubCategory(String name) throws Exception {
        Subcategory subcategory = subcategoryFacade.getSubCateogryByName(name);
        subcategoryFacade.remove(subcategory);
    }

    public void modifySubCategory(String oldName, String newName) throws Exception {
        Subcategory subcategory = subcategoryFacade.getSubCateogryByName(oldName);
        subcategory.setName(newName);
        subcategoryFacade.edit(subcategory);
    }

    public void popluateSubCategoryFields(ComboBox comboBox) {
        comboBox.addItems(subcategoryFacade.findAll());
    }

    public void createState(String value) throws Exception {
        Advertstate state = new Advertstate();
        state.setName(value);
        stateFacade.create(state);
    }

    public void deleteState(String name) throws Exception {
        Advertstate state = stateFacade.getStateByName(name);
        stateFacade.remove(state);
    }

    public void modifyState(String oldName, String newName) throws Exception {
        Advertstate state = stateFacade.getStateByName(oldName);
        state.setName(newName);
        stateFacade.edit(state);
    }

    public void popluateStateFields(ComboBox comboBox) {
        comboBox.addItems(stateFacade.findAll());
    }

    public void createType(String name) throws Exception {
        Adverttype type = new Adverttype();
        type.setName(name);
        typeFacade.create(type);

    }

    public void deleteType(String name) throws Exception {
        Adverttype type = typeFacade.getTypeByName(name);
        typeFacade.remove(type);
    }

    public void modifyType(String oldName, String newName) throws Exception {
        Adverttype type = typeFacade.getTypeByName(oldName);
        type.setName(newName);
        typeFacade.edit(type);
    }

    public void popluateTypeFields(ComboBox comboBox) {
        comboBox.addItems(typeFacade.findAll());
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    public void setCategoryFacade(MaincategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    public void setSubcategoryFacade(SubcategoryFacade subcategoryFacade) {
        this.subcategoryFacade = subcategoryFacade;
    }

    public void setStateFacade(AdvertstateFacade stateFacade) {
        this.stateFacade = stateFacade;
    }

    public void setTypeFacade(AdverttypeFacade typeFacade) {
        this.typeFacade = typeFacade;
    }
}
