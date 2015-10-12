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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "advertiser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advertiser.findAll", query = "SELECT a FROM Advertiser a"),
    @NamedQuery(name = "Advertiser.findById", query = "SELECT a FROM Advertiser a WHERE a.id = :id"),
    @NamedQuery(name = "Advertiser.findByPassword", query = "SELECT a FROM Advertiser a WHERE a.password = :password"),
    @NamedQuery(name = "Advertiser.findByName", query = "SELECT a FROM Advertiser a WHERE a.name = :name"),
    @NamedQuery(name = "Advertiser.findByPhonenumber", query = "SELECT a FROM Advertiser a WHERE a.phonenumber = :phonenumber"),
    @NamedQuery(name = "Advertiser.findByEmail", query = "SELECT a FROM Advertiser a WHERE a.email = :email"),
    @NamedQuery(name = "Advertiser.findByNewsletter", query = "SELECT a FROM Advertiser a WHERE a.newsletter = :newsletter")})
public class Advertiser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Size(max = 12)
    @Column(name = "phonenumber")
    private String phonenumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email")
    private String email;
    @Column(name = "newsletter")
    private Boolean newsletter;
    @JoinColumn(name = "authorityId", referencedColumnName = "id")
    @ManyToOne
    private Authority authorityId;
    @JoinColumn(name = "postboxId", referencedColumnName = "id")
    @ManyToOne
    private Postbox postboxId;
    @OneToMany(mappedBy = "advertiserId")
    private Collection<Advertisement> advertisementCollection;

    public Advertiser() {
    }

    public Advertiser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Authority getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Authority authorityId) {
        this.authorityId = authorityId;
    }

    public Postbox getPostboxId() {
        return postboxId;
    }

    public void setPostboxId(Postbox postboxId) {
        this.postboxId = postboxId;
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
        if (!(object instanceof Advertiser)) {
            return false;
        }
        Advertiser other = (Advertiser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.Enums.Advertiser[ id=" + id + " ]";
    }
    
}
