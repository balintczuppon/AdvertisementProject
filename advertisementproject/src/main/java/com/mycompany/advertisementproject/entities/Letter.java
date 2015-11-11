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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Letter.findByQuestionername", query = "SELECT l FROM Letter l WHERE l.questionername = :questionername"),
    @NamedQuery(name = "Letter.findByQuestionerphone", query = "SELECT l FROM Letter l WHERE l.questionerphone = :questionerphone"),
    @NamedQuery(name = "Letter.findByQuestionermail", query = "SELECT l FROM Letter l WHERE l.questionermail = :questionermail"),
    @NamedQuery(name = "Letter.findByMailtitle", query = "SELECT l FROM Letter l WHERE l.mailtitle = :mailtitle"),
    @NamedQuery(name = "Letter.findBySender", query = "SELECT l FROM Letter l WHERE l.sender = :sender")})
public class Letter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "questionername")
    private String questionername;
    @Size(max = 12)
    @Column(name = "questionerphone")
    private String questionerphone;
    @Size(max = 30)
    @Column(name = "questionermail")
    private String questionermail;
    @Size(max = 30)
    @Column(name = "mailtitle")
    private String mailtitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "mailtext")
    private String mailtext;
    @Column(name = "sender")
    private Boolean sender;
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

    public String getQuestionername() {
        return questionername;
    }

    public void setQuestionername(String questionername) {
        this.questionername = questionername;
    }

    public String getQuestionerphone() {
        return questionerphone;
    }

    public void setQuestionerphone(String questionerphone) {
        this.questionerphone = questionerphone;
    }

    public String getQuestionermail() {
        return questionermail;
    }

    public void setQuestionermail(String questionermail) {
        this.questionermail = questionermail;
    }

    public String getMailtitle() {
        return mailtitle;
    }

    public void setMailtitle(String mailtitle) {
        this.mailtitle = mailtitle;
    }

    public String getMailtext() {
        return mailtext;
    }

    public void setMailtext(String mailtext) {
        this.mailtext = mailtext;
    }

    public Boolean getSender() {
        return sender;
    }

    public void setSender(Boolean sender) {
        this.sender = sender;
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
