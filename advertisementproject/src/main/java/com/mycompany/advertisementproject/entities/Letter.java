/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.entities;

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
@Table(name = "letter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Letter.findAll", query = "SELECT l FROM Letter l"),
    @NamedQuery(name = "Letter.findById", query = "SELECT l FROM Letter l WHERE l.id = :id"),
    @NamedQuery(name = "Letter.findByTitle", query = "SELECT l FROM Letter l WHERE l.title = :title"),
    @NamedQuery(name = "Letter.findByText", query = "SELECT l FROM Letter l WHERE l.text = :text"),
    @NamedQuery(name = "Letter.findBySender", query = "SELECT l FROM Letter l WHERE l.sender = :sender"),
    @NamedQuery(name = "Letter.findByReceiver", query = "SELECT l FROM Letter l WHERE l.receiver = :receiver")})
public class Letter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "title")
    private String title;
    @Size(max = 300)
    @Column(name = "text")
    private String text;
    @Size(max = 30)
    @Column(name = "sender")
    private String sender;
    @Size(max = 30)
    @Column(name = "receiver")
    private String receiver;
    @JoinColumn(name = "postBoxId", referencedColumnName = "id")
    @ManyToOne
    private Postbox postBoxId;

    public Letter() {
    }

    public Letter(Integer id) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Postbox getPostBoxId() {
        return postBoxId;
    }

    public void setPostBoxId(Postbox postBoxId) {
        this.postBoxId = postBoxId;
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
        if (!(object instanceof Letter)) {
            return false;
        }
        Letter other = (Letter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.advertisementproject.entities.Letter[ id=" + id + " ]";
    }
    
}
