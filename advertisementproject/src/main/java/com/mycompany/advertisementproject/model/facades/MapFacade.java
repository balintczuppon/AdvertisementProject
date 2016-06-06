
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class MapFacade extends AbstractFacade<Map> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MapFacade() {
        super(Map.class);
    }
    
}
