
package com.mycompany.advertisementproject.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Czuppon Balint Peter
 */
@Entity
@Table(name = "map")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Map.findAll", query = "SELECT m FROM Map m"),
    @NamedQuery(name = "Map.findById", query = "SELECT m FROM Map m WHERE m.id = :id"),
    @NamedQuery(name = "Map.findByCordx", query = "SELECT m FROM Map m WHERE m.cordx = :cordx"),
    @NamedQuery(name = "Map.findByCordy", query = "SELECT m FROM Map m WHERE m.cordy = :cordy")})
public class Map implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cordx")
    private Float cordx;
    @Column(name = "cordy")
    private Float cordy;
    @OneToMany(mappedBy = "mapId")
    private Collection<Advertisement> advertisementCollection;

    public Map() {
    }

    public Map(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCordx() {
        return cordx;
    }

    public void setCordx(Float cordx) {
        this.cordx = cordx;
    }

    public Float getCordy() {
        return cordy;
    }

    public void setCordy(Float cordy) {
        this.cordy = cordy;
    }

    @XmlTransient
    public Collection<Advertisement> getAdvertisementCollection() {
        return advertisementCollection;
    }

    public void setAdvertisementCollection(Collection<Advertisement> advertisementCollection) {
        this.advertisementCollection = advertisementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Map)) {
            return false;
        }
        Map other = (Map) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.Enums.Map[ id=" + id + " ]";
    }
    
}
