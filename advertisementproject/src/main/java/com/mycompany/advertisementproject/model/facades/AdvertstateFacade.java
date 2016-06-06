
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Advertstate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class AdvertstateFacade extends AbstractFacade<Advertstate> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvertstateFacade() {
        super(Advertstate.class);
    }

    public Advertstate getStateByName(Object value) {
        return (Advertstate) em.createNamedQuery("Advertstate.findByName").setParameter("name", value).getSingleResult();
    }
    
}
