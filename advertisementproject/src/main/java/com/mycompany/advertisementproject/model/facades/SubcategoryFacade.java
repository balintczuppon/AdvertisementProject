
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Czuppon Balint Peter
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
