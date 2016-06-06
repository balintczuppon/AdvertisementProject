
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.City;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class CityFacade extends AbstractFacade<City> {
    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CityFacade() {
        super(City.class);
    }

    public City getCityByName(Object value) {
        return (City) em.createNamedQuery("City.findByCityName").setParameter("cityName", value).getSingleResult();
    }
    
}
