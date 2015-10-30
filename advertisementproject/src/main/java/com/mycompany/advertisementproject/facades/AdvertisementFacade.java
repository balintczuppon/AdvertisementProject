/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.facades;

import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.Locality;
import com.mycompany.advertisementproject.entities.Maincategory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author balin
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

    public List<Advertisement> findAdvertsByFilters(Maincategory category, int subcategory, Locality local, Advertstate state, Adverttype type, int min, int max) {
        String query;

        query = "SELECT a FROM Advertisement a WHERE ";

        if (category != null) {
            query += "a.mainCategoryId = :mCatId ";
        } else {
            query += "a.mainCategoryId is not null AND :mCatId is null ";
        }

        if (subcategory > 0) {
            query += "AND a.subCategoryId = :sCatId ";
        } else {
            query += "AND a.subCategoryId is not null AND :sCatId = 0 ";
        }

        if (local != null) {
            query += "AND a.localityId = :localId ";
        } else {
            query += "AND a.localityId is not null AND :localId is null ";
        }

        if (state != null) {
            query += "AND a.advertStateId = :stateId ";
        } else {
            query += "AND a.advertStateId is not null AND :stateId is null ";
        }

        if (type != null) {
            query += "AND a.advertTypeId = :typeId ";
        } else {
            query += "AND a.advertTypeId is not null AND :typeId is null ";
        }

        if (max == 0) {
            query += "AND a.price > :minprice AND :maxprice = 0";
        } else {
            query += "AND a.price BETWEEN :minprice AND :maxprice";
        }

        return em.createQuery(query)
                .setParameter("minprice", min)
                .setParameter("maxprice", max)
                .setParameter("mCatId", category)
                .setParameter("sCatId", subcategory)
                .setParameter("stateId", state)
                .setParameter("typeId", type)
                .setParameter("localId", local)
                .getResultList();
    }
}
