
package com.mycompany.advertisementproject.model.facades;

import com.mycompany.advertisementproject.model.entities.Advertiser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuppon Balint Peter
 */
@Stateless
public class AdvertiserFacade extends AbstractFacade<Advertiser> {

    @PersistenceContext(unitName = "com.mycompany_advertisementproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Long countUsers(String user){
        return (Long) em.createQuery(
                "SELECT Count(a.id) FROM Advertiser a WHERE a.email = :email"
        ).setParameter("email",user).getSingleResult();
    }
    
    public Object getAdvertiserByMail(String user){
        return em.createNamedQuery("Advertiser.findByEmail").setParameter("email",user).getSingleResult();
    }
    
    
    public void verifyUser(String VerificationId){
        em.createQuery("UPDATE Advertiser a set a.isVerificated = TRUE"
                + " WHERE a.verificationID = :verificationId")
                .setParameter("verificationId",VerificationId).executeUpdate();
    }
    
    public AdvertiserFacade() {
        super(Advertiser.class);
    }
}
