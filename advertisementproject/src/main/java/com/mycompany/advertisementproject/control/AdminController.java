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
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author balin
 */
public class AdminController implements Serializable {

    @Inject
    private CountryFacade countryFacade;
    @Inject
    private CityFacade cityFacade;
    @Inject
    private MaincategoryFacade categoryFacade;
    @Inject
    private SubcategoryFacade subcategoryFacade;
    @Inject
    private AdvertstateFacade stateFacade;
    @Inject
    private AdverttypeFacade typeFacade;

    /**
     *
     * @param name
     * @throws Exception
     */
    public void createCountry(String name) throws Exception {
        Country country = new Country();
        country.setCountryName(name);
        countryFacade.create(country);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteCountry(String name) throws Exception {
        Country country = countryFacade.getCountryByName(name);
        countryFacade.remove(country);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifyCountry(String oldName, String newName) throws Exception {
        Country country = countryFacade.getCountryByName(oldName);
        country.setCountryName(newName);
        countryFacade.edit(country);
    }

    /**
     *
     * @param comboBox
     */
    public void popluateCountryFields(ComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItems(countryFacade.findAll());
        comboBox.markAsDirtyRecursive();
    }

    /**
     *
     * @param value
     * @throws Exception
     */
    public void createCity(String value) throws Exception {
        City city = new City();
        city.setCityName(value);
        city.setCountryId(null);
        cityFacade.create(city);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteCity(String name) throws Exception {
        City city = cityFacade.getCityByName(name);
        cityFacade.remove(city);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifyCity(String oldName, String newName) throws Exception {
        City city = cityFacade.getCityByName(oldName);
        city.setCityName(newName);
        cityFacade.edit(city);
    }

    /**
     *
     * @param comboBoxModify
     * @param comboBoxCreate
     */
    public void popluateCityFields(ComboBox comboBoxModify, ComboBox comboBoxCreate) {
        comboBoxModify.removeAllItems();
        comboBoxCreate.removeAllItems();
        comboBoxModify.addItems(cityFacade.findAll());
        comboBoxCreate.addItems(countryFacade.findAll());
    }

    /**
     *
     * @param value
     * @throws Exception
     */
    public void createCategory(String value) throws Exception {
        Maincategory category = new Maincategory();
        category.setName(value);
        categoryFacade.create(category);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteCategory(String name) throws Exception {
        Maincategory category = categoryFacade.getCategoryByName(name);
        categoryFacade.remove(category);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifyCategory(String oldName, String newName) throws Exception {
        Maincategory cateogry = categoryFacade.getCategoryByName(oldName);
        cateogry.setName(newName);
        categoryFacade.edit(cateogry);
    }

    /**
     *
     * @param comboBox
     */
    public void popluateCategoryFields(ComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.markAsDirtyRecursive();
        comboBox.addItems(categoryFacade.findAll());
    }

    /**
     *
     * @param value
     * @throws Exception
     */
    public void createSubCategory(String value) throws Exception {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(value);
        subcategory.setMainCategoryId(null);
        subcategoryFacade.create(subcategory);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteSubCategory(String name) throws Exception {
        Subcategory subcategory = subcategoryFacade.getSubCateogryByName(name);
        subcategoryFacade.remove(subcategory);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifySubCategory(String oldName, String newName) throws Exception {
        Subcategory subcategory = subcategoryFacade.getSubCateogryByName(oldName);
        subcategory.setName(newName);
        subcategoryFacade.edit(subcategory);
    }

    /**
     *
     * @param comboBoxModify
     * @param comboBoxCreate
     */
    public void popluateSubCategoryFields(ComboBox comboBoxModify, ComboBox comboBoxCreate) {
        comboBoxModify.removeAllItems();
        comboBoxCreate.removeAllItems();
        comboBoxModify.addItems(subcategoryFacade.findAll());
        comboBoxCreate.addItems(categoryFacade.findAll());
    }

    /**
     *
     * @param value
     * @throws Exception
     */
    public void createState(String value) throws Exception {
        Advertstate state = new Advertstate();
        state.setName(value);
        stateFacade.create(state);
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteState(String name) throws Exception {
        Advertstate state = stateFacade.getStateByName(name);
        stateFacade.remove(state);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifyState(String oldName, String newName) throws Exception {
        Advertstate state = stateFacade.getStateByName(oldName);
        state.setName(newName);
        stateFacade.edit(state);
    }

    /**
     *
     * @param comboBox
     */
    public void popluateStateFields(ComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItems(stateFacade.findAll());
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void createType(String name) throws Exception {
        Adverttype type = new Adverttype();
        type.setName(name);
        typeFacade.create(type);

    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteType(String name) throws Exception {
        Adverttype type = typeFacade.getTypeByName(name);
        typeFacade.remove(type);
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void modifyType(String oldName, String newName) throws Exception {
        Adverttype type = typeFacade.getTypeByName(oldName);
        type.setName(newName);
        typeFacade.edit(type);
    }

    /**
     *
     * @param comboBox
     */
    public void popluateTypeFields(ComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItems(typeFacade.findAll());
    }
}
