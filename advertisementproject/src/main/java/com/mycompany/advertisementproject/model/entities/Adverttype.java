/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author balin
 */
@Entity
@Table(name = "adverttype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adverttype.findAll", query = "SELECT a FROM Adverttype a"),
    @NamedQuery(name = "Adverttype.findById", query = "SELECT a FROM Adverttype a WHERE a.id = :id"),
    @NamedQuery(name = "Adverttype.findByName", query = "SELECT a FROM Adverttype a WHERE a.name = :name")})
public class Adverttype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "advertTypeId")
    private Collection<Advertisement> advertisementCollection;

    public Adverttype() {
    }

    public Adverttype(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adverttype)) {
            return false;
        }
        Adverttype other = (Adverttype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
