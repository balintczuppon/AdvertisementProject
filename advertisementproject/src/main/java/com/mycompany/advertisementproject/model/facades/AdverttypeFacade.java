
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Adverttype;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class AdverttypeFacade extends AbstractFacade<Adverttype> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdverttypeFacade() {
        super(Adverttype.class);
    }

    public Adverttype getTypeByName(Object value) {
        return (Adverttype) em.createNamedQuery("Adverttype.findByName").setParameter("name", value).getSingleResult();
    }
    
}
