/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Letter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author balin
 */
@Stateless
public class LetterFacade extends AbstractFacade<Letter> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LetterFacade() {
        super(Letter.class);
    }

    public List<Letter> getMyLetters(Advertiser current_advertiser) {
        return em.createQuery("SELECT l FROM Letter l WHERE l.advertiserId = :advertiser")
                .setParameter("advertiser", current_advertiser)
                .getResultList();
    }

    public Letter findById(int letterID) {
        return (Letter) em.createNamedQuery("Letter.findById").setParameter("id",letterID).getSingleResult();
    }
    
}
