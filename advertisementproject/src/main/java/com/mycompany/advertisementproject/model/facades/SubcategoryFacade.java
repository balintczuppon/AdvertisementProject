/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author balin
 */
@Stateless
public class SubcategoryFacade extends AbstractFacade<Subcategory> {

    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubcategoryFacade() {
        super(Subcategory.class);
    }

    public Iterable<Subcategory> findByMainCategoryId(Maincategory mcat) {
        Query query = em.createNamedQuery("Subcategory.findByMainCategoryId").setParameter("id", mcat);
        return query.getResultList();
    }

    public Subcategory getSubCateogryByName(Object value) {
        return (Subcategory) em.createNamedQuery("Subcategory.findByName").setParameter("name", value).getSingleResult();
    }

}
