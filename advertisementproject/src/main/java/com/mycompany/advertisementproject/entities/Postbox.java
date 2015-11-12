/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author balin
 */
@Entity
@Table(name = "postbox")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postbox.findAll", query = "SELECT p FROM Postbox p"),
    @NamedQuery(name = "Postbox.findById", query = "SELECT p FROM Postbox p WHERE p.id = :id")})
public class Postbox implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Advertiser advertiser;
    @OneToMany(mappedBy = "postBoxId")
    private Collection<Letter> letterCollection;

    public Postbox() {
    }

    public Postbox(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    @XmlTransient
    public Collection<Letter> getLetterCollection() {
        return letterCollection;
    }

    public void setLetterCollection(Collection<Letter> letterCollection) {
        this.letterCollection = letterCollection;
    }
    
    public void addLetter(Letter e){
        letterCollection.add(e);
        e.setPostBoxId(this);
    }
    
    public void removeLetter(Letter e){
        letterCollection.remove(e);
        e.setPostBoxId(null);
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
        if (!(object instanceof Postbox)) {
            return false;
        }
        Postbox other = (Postbox) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.entities.Postbox[ id=" + id + " ]";
    }
    
}
