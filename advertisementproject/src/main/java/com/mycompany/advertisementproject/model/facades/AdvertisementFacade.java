package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Advertstate;
import com.mycompany.advertisementproject.model.entities.Adverttype;
import com.mycompany.advertisementproject.model.entities.City;
import com.mycompany.advertisementproject.model.entities.Country;
import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class AdvertisementFacade extends AbstractFacade<Advertisement> {

    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvertisementFacade() {
        super(Advertisement.class);
    }

    public List<Advertisement> getMyAdvertisements(Advertiser advertiser) {
        return em.createQuery("SELECT a FROM Advertisement a WHERE a.advertiserId = :advertiser")
                .setParameter("advertiser", advertiser)
                .getResultList();
    }

    private String query;

    public List<Advertisement> findAdvertsByFilters(Maincategory category, Subcategory subcategory, Country country, City city, Advertstate state, Adverttype type, int min, int max, String text) {

        query = "SELECT a FROM Advertisement a WHERE ";

        checkCategory(category);
        checkSubCategory(subcategory);
        checkCountry(country);
        checkCity(city);
        checkState(state);
        checkType(type);
        checkPrice(max);
        checkText(text);

        return em.createQuery(query).setParameter("minprice", min).setParameter("maxprice", max)
                .setParameter("mCatId", category).setParameter("sCatId", subcategory).setParameter("stateId", state)
                .setParameter("typeId", type).setParameter("countryId", country).setParameter("cityId", city)
                .setParameter("text", text).getResultList();
    }

    public List<Advertisement> findByText(String value) {
        return em.createQuery("SELECT a FROM Advertisement a WHERE a.title LIKE :text OR a.description LIKE :text")
                .setParameter("text", "%"+value+"%")
                .getResultList();
    }

    private void checkCategory(Maincategory category) {
        if (category != null) {
            query += "a.mainCategoryId = :mCatId ";
        } else {
            query += ":mCatId is null ";
        }
    }

    private void checkSubCategory(Subcategory subcategory) {
        if (subcategory != null) {
            query += "AND a.subCategoryId = :sCatId ";
        } else {
            query += "AND :sCatId is null ";
        }
    }

    private void checkCountry(Country country) {
        if (country != null) {
            query += "AND a.countryId = :countryId ";
        } else {
            query += "AND :countryId is null ";
        }
    }

    private void checkCity(City city) {
        if (city != null) {
            query += "AND a.cityId = :cityId ";
        } else {
            query += "AND :cityId is null ";
        }
    }

    private void checkState(Advertstate state) {
        if (state != null) {
            query += "AND a.advertStateId = :stateId ";
        } else {
            query += "AND :stateId is null ";
        }
    }

    private void checkPrice(int max) {
        if (max == 0) {
            query += "AND a.price > :minprice AND :maxprice = 0 ";
        } else {
            query += "AND a.price BETWEEN :minprice AND :maxprice ";
        }
    }

    private void checkText(String text) {
        if (text == null) {
            query += "AND :text is null";
        } else if (text.isEmpty()) {
            query += "AND :text is null";
        } else {
            query += "AND a.title like :text OR a.description like :text";
        }
    }

    private void checkType(Adverttype type) {
        if (type != null) {
            query += "AND a.advertTypeId = :typeId ";
        } else {
            query += "AND :typeId is null ";
        }
    }
}
