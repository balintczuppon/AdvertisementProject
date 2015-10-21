/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.facades;

import com.mycompany.advertisementproject.entities.Advertisement;
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

    public List<Advertisement> getMyAdvertisements() {
        return em.createQuery("SELECT c FROM Advertisement WHERE c.advertiserId = null", Advertisement.class).getResultList();
    }

    public List<Advertisement> findAdvertsByFilters(Object category, Object subcategory, Object local, Object state, Object type, String min, String max) {
        return em.createNamedQuery("Advertisement.findByAll")
                .setParameter("minprice", Integer.valueOf(min))
                .setParameter("maxprice", Integer.valueOf(max))
                .setParameter("mCatId", category)
                .setParameter("sCatId", subcategory)
                .setParameter("stateId", state)
                .setParameter("typeId", type)
                .setParameter("localId", local).getResultList();
    }
}
