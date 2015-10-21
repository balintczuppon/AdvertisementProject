/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author balin
 */
@Entity
@Table(name = "advertisement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advertisement.findAll", query = "SELECT a FROM Advertisement a"),
    @NamedQuery(name = "Advertisement.findById", query = "SELECT a FROM Advertisement a WHERE a.id = :id"),
    @NamedQuery(name = "Advertisement.findByTitle", query = "SELECT a FROM Advertisement a WHERE a.title = :title"),
    @NamedQuery(name = "Advertisement.findByDescription", query = "SELECT a FROM Advertisement a WHERE a.description = :description"),
    @NamedQuery(name = "Advertisement.findByRegistrationDate", query = "SELECT a FROM Advertisement a WHERE a.registrationDate = :registrationDate"),
    @NamedQuery(name = "Advertisement.findByPrice", query = "SELECT a FROM Advertisement a WHERE a.price = :price"),
    @NamedQuery(name = "Advertisement.findByAll",
            query = "SELECT a FROM Advertisement a WHERE a.price BETWEEN :minprice AND :maxprice AND "
            + "a.mainCategoryId = :mCatId AND a.subCategoryId = :sCatId AND a.advertStateId = :stateId AND"
            + " a.advertTypeId = :typeId AND a.localityId = :localId")})
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "title")
    private String title;
    @Size(max = 300)
    @Column(name = "description")
    private String description;
    @Column(name = "registrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "price")
    private Integer price;
    @Column(name = "subCategoryId")
    private Integer subCategoryId;
    @JoinColumn(name = "advertiserId", referencedColumnName = "id")
    @ManyToOne
    private Advertiser advertiserId;
    @JoinColumn(name = "mainCategoryId", referencedColumnName = "id")
    @ManyToOne
    private Maincategory mainCategoryId;
    @JoinColumn(name = "advertStateId", referencedColumnName = "id")
    @ManyToOne
    private Advertstate advertStateId;
    @JoinColumn(name = "localityId", referencedColumnName = "id")
    @ManyToOne
    private Locality localityId;
    @JoinColumn(name = "advertTypeId", referencedColumnName = "id")
    @ManyToOne
    private Adverttype advertTypeId;
    @JoinColumn(name = "mapId", referencedColumnName = "id")
    @ManyToOne
    private Map mapId;
    @OneToMany(mappedBy = "advertisementId")
    private Collection<Picture> pictureCollection;

    public Advertisement() {
    }

    public Advertisement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Advertiser getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Advertiser advertiserId) {
        this.advertiserId = advertiserId;
    }

    public Maincategory getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Maincategory mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public Advertstate getAdvertStateId() {
        return advertStateId;
    }

    public void setAdvertStateId(Advertstate advertStateId) {
        this.advertStateId = advertStateId;
    }

    public Locality getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Locality localityId) {
        this.localityId = localityId;
    }

    public Adverttype getAdvertTypeId() {
        return advertTypeId;
    }

    public void setAdvertTypeId(Adverttype advertTypeId) {
        this.advertTypeId = advertTypeId;
    }

    public Map getMapId() {
        return mapId;
    }

    public void setMapId(Map mapId) {
        this.mapId = mapId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @XmlTransient
    public Collection<Picture> getPictureCollection() {
        return pictureCollection;
    }

    public void setPictureCollection(Collection<Picture> pictureCollection) {
        this.pictureCollection = pictureCollection;
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
        if (!(object instanceof Advertisement)) {
            return false;
        }
        Advertisement other = (Advertisement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.Enums.Advertisement[ id=" + id + " ]";
    }
}
