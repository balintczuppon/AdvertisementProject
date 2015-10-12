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
@Table(name = "postbox")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postbox.findAll", query = "SELECT p FROM Postbox p"),
    @NamedQuery(name = "Postbox.findById", query = "SELECT p FROM Postbox p WHERE p.id = :id"),
    @NamedQuery(name = "Postbox.findByVisitorName", query = "SELECT p FROM Postbox p WHERE p.visitorName = :visitorName"),
    @NamedQuery(name = "Postbox.findByVisitorEmail", query = "SELECT p FROM Postbox p WHERE p.visitorEmail = :visitorEmail"),
    @NamedQuery(name = "Postbox.findByLetterTitle", query = "SELECT p FROM Postbox p WHERE p.letterTitle = :letterTitle"),
    @NamedQuery(name = "Postbox.findByLetterText", query = "SELECT p FROM Postbox p WHERE p.letterText = :letterText"),
    @NamedQuery(name = "Postbox.findBySender", query = "SELECT p FROM Postbox p WHERE p.sender = :sender")})
public class Postbox implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "visitorName")
    private String visitorName;
    @Size(max = 20)
    @Column(name = "visitorEmail")
    private String visitorEmail;
    @Size(max = 20)
    @Column(name = "letterTitle")
    private String letterTitle;
    @Size(max = 300)
    @Column(name = "letterText")
    private String letterText;
    @Column(name = "sender")
    private Boolean sender;
    @OneToMany(mappedBy = "postboxId")
    private Collection<Advertiser> advertiserCollection;

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

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public String getLetterTitle() {
        return letterTitle;
    }

    public void setLetterTitle(String letterTitle) {
        this.letterTitle = letterTitle;
    }

    public String getLetterText() {
        return letterText;
    }

    public void setLetterText(String letterText) {
        this.letterText = letterText;
    }

    public Boolean getSender() {
        return sender;
    }

    public void setSender(Boolean sender) {
        this.sender = sender;
    }

    @XmlTransient
    public Collection<Advertiser> getAdvertiserCollection() {
        return advertiserCollection;
    }

    public void setAdvertiserCollection(Collection<Advertiser> advertiserCollection) {
        this.advertiserCollection = advertiserCollection;
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
        return "com.mycompany.advertisementproject.Enums.Postbox[ id=" + id + " ]";
    }
    
}
