/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Advertiser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author balin
 */
@Stateless
public class AdvertiserFacade extends AbstractFacade<Advertiser> {

    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Object getAdvertiserByMail(String user){
        return em.createNamedQuery("Advertiser.findByEmail").setParameter("email",user).getSingleResult();
    }
    
    public AdvertiserFacade() {
        super(Advertiser.class);
    }
}
