package com.mycompany.advertisementproject.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
 * @author Czuppon Balint Peter
 */
@Entity
@Cacheable(false)
@Table(name = "advertisement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advertisement.findAll", query = "SELECT a FROM Advertisement a"),
    @NamedQuery(name = "Advertisement.findById", query = "SELECT a FROM Advertisement a WHERE a.id = :id"),
    @NamedQuery(name = "Advertisement.findByTitle", query = "SELECT a FROM Advertisement a WHERE a.title = :title"),
    @NamedQuery(name = "Advertisement.findByDescription", query = "SELECT a FROM Advertisement a WHERE a.description = :description"),
    @NamedQuery(name = "Advertisement.findByRegistrationDate", query = "SELECT a FROM Advertisement a WHERE a.registrationDate = :registrationDate"),
    @NamedQuery(name = "Advertisement.findByPrice", query = "SELECT a FROM Advertisement a WHERE a.price = :price")})
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
    @Column(name = "description")
    private String description;
    @Column(name = "registrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "price")
    private Integer price;
    @JoinColumn(name = "subCategoryId", referencedColumnName = "id")
    @ManyToOne
    private Subcategory subCategoryId;
    @JoinColumn(name = "advertiserId", referencedColumnName = "id")
    @ManyToOne
    private Advertiser advertiserId;
    @JoinColumn(name = "mainCategoryId", referencedColumnName = "id")
    @ManyToOne
    private Maincategory mainCategoryId;
    @JoinColumn(name = "advertStateId", referencedColumnName = "id")
    @ManyToOne
    private Advertstate advertStateId;
    @JoinColumn(name = "advertTypeId", referencedColumnName = "id")
    @ManyToOne
    private Adverttype advertTypeId;
    @JoinColumn(name = "countryId", referencedColumnName = "id")
    @ManyToOne
    private Country countryId;
    @JoinColumn(name = "cityId", referencedColumnName = "id")
    @ManyToOne
    private City cityId;
    @JoinColumn(name = "mapId", referencedColumnName = "id")
    @ManyToOne
    private Map mapId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advertisementId", orphanRemoval = true)
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
        if (price == null) {
            return 0;
        }
        return price;
    }

    public void setPrice(Integer price) {
        if (price == null) {
            this.price = 0;
        } else {
            this.price = price;
        }
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

    public Subcategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Subcategory subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    @XmlTransient
    public Collection<Picture> getPictureCollection() {
        return pictureCollection;
    }

    public void setPictureCollection(Collection<Picture> pictureCollection) {
        this.pictureCollection = pictureCollection;
    }

    public void addPicture(Picture p) {
        p.setAdvertisementId(this);
        this.pictureCollection.add(p);
    }

    public void removePicture(Picture p) {
        p.setAdvertisementId(null);
        this.pictureCollection.remove(p);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
        return title;
    }
}
