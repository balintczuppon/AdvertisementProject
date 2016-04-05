
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Maincategory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MaincategoryFacade extends AbstractFacade<Maincategory> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaincategoryFacade() {
        super(Maincategory.class);
    }

    public Maincategory getCategoryByName(String value) {
        return (Maincategory) em.createNamedQuery("Maincategory.findByName").setParameter("name", value).getSingleResult();
    }
    
    
    
}
