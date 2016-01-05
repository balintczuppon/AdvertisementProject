/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.model.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author balin
 */
@Entity
@Table(name = "subsubcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subsubcategory.findAll", query = "SELECT s FROM Subsubcategory s"),
    @NamedQuery(name = "Subsubcategory.findById", query = "SELECT s FROM Subsubcategory s WHERE s.id = :id"),
    @NamedQuery(name = "Subsubcategory.findByName", query = "SELECT s FROM Subsubcategory s WHERE s.name = :name")})
public class Subsubcategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 40)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "subCategoryId", referencedColumnName = "id")
    @ManyToOne
    private Subcategory subCategoryId;

    public Subsubcategory() {
    }

    public Subsubcategory(Integer id) {
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

    public Subcategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Subcategory subCategoryId) {
        this.subCategoryId = subCategoryId;
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
        if (!(object instanceof Subsubcategory)) {
            return false;
        }
        Subsubcategory other = (Subsubcategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.Enums.Subsubcategory[ id=" + id + " ]";
    }
    
}
