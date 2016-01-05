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
@Table(name = "subcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subcategory.findAll", query = "SELECT s FROM Subcategory s"),
    @NamedQuery(name = "Subcategory.findById", query = "SELECT s FROM Subcategory s WHERE s.id = :id"),
    @NamedQuery(name = "Subcategory.findByName", query = "SELECT s FROM Subcategory s WHERE s.name = :name")})
public class Subcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 40)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "subCategoryId")
    private Collection<Advertisement> advertisementCollection;
    @OneToMany(mappedBy = "subCategoryId")
    private Collection<Subsubcategory> subsubcategoryCollection;
    @JoinColumn(name = "mainCategoryId", referencedColumnName = "id")
    @ManyToOne
    private Maincategory mainCategoryId;

    public Subcategory() {
    }

    public Subcategory(Integer id) {
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
    public Collection<Subsubcategory> getSubsubcategoryCollection() {
        return subsubcategoryCollection;
    }

    public void setSubsubcategoryCollection(Collection<Subsubcategory> subsubcategoryCollection) {
        this.subsubcategoryCollection = subsubcategoryCollection;
    }

    public Maincategory getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Maincategory mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

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
        if (!(object instanceof Subcategory)) {
            return false;
        }
        Subcategory other = (Subcategory) object;
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
