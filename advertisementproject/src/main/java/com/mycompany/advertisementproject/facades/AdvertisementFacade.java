package com.mycompany.advertisementproject.facades;

import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.City;
import com.mycompany.advertisementproject.entities.Country;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.entities.Subcategory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public List<Advertisement> findAdvertsByFilters(Maincategory category, Subcategory subcategory, Country country, City city, Advertstate state, Adverttype type, int min, int max, String text) {
        String query;

        query = "SELECT a FROM Advertisement a WHERE ";

        if (category != null) {
            query += "a.mainCategoryId = :mCatId ";
        } else {
            query += ":mCatId is null ";
        }

        if (subcategory != null) {
            query += "AND a.subCategoryId = :sCatId ";
        } else {
            query += "AND :sCatId is null ";
        }

        if (country != null) {
            query += "AND a.countryId = :countryId ";
        } else {
            query += "AND :countryId is null ";
        }

        if (city != null) {
            query += "AND a.cityId = :cityId ";
        } else {
            query += "AND :cityId is null ";
        }

        if (state != null) {
            query += "AND a.advertStateId = :stateId ";
        } else {
            query += "AND :stateId is null ";
        }

        if (type != null) {
            query += "AND a.advertTypeId = :typeId ";
        } else {
            query += "AND :typeId is null ";
        }

        if (max == 0) {
            query += "AND a.price > :minprice AND :maxprice = 0 ";
        } else {
            query += "AND a.price BETWEEN :minprice AND :maxprice ";
        }

        if (text == null) {
            query += "AND :text is null";
        } else if (text.isEmpty()) {
            query += "AND :text is null";
        } else {
            query += "AND a.title like :text OR a.description like :text";
        }

        System.out.println(em.createQuery(query)
                .setParameter("minprice", min)
                .setParameter("maxprice", max)
                .setParameter("mCatId", category)
                .setParameter("sCatId", subcategory)
                .setParameter("stateId", state)
                .setParameter("typeId", type)
                .setParameter("countryId", country)
                .setParameter("cityId", city)
                .setParameter("text", text)
                .toString());

        return em.createQuery(query)
                .setParameter("minprice", min)
                .setParameter("maxprice", max)
                .setParameter("mCatId", category)
                .setParameter("sCatId", subcategory)
                .setParameter("stateId", state)
                .setParameter("typeId", type)
                .setParameter("countryId", country)
                .setParameter("cityId", city)
                .setParameter("text", text)
                .getResultList();
    }

    public List<Advertisement> findByText(String value) {
        return em.createQuery("SELECT a FROM Advertisement a WHERE a.title like :text OR a.description like :text")
                .setParameter("text", value)
                .getResultList();
    }
}
