
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Country;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class CountryFacade extends AbstractFacade<Country> {

    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryFacade() {
        super(Country.class);
    }

    public Country getCountryByName(String name) {
        return (Country) em.createNamedQuery("Country.findByCountryName").setParameter("countryName",name).getSingleResult();
    }


}
