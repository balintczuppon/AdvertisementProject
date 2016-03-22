package com.mycompany.advertisementproject.control;

import com.mycompany.advertisementproject.model.entities.City;
import com.mycompany.advertisementproject.model.entities.Country;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;

public class AdminController {

    private CountryFacade countryFacade;
    private CityFacade cityFacade;

    public void createCountry(String value) {
        Country country = new Country();
        country.setCountryName(value);
        countryFacade.create(country);
    }

    public void createCity(String value) {
        City city = new City();
        city.setCityName(value);
        cityFacade.create(city);
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }
}
