
package com.mycompany.advertisementproject.model.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Czuppon Balint Peter
 */
@Entity
@Table(name = "letter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Letter.findAll", query = "SELECT l FROM Letter l"),
    @NamedQuery(name = "Letter.findById", query = "SELECT l FROM Letter l WHERE l.id = :id"),
    @NamedQuery(name = "Letter.findBySendername", query = "SELECT l FROM Letter l WHERE l.sendername = :sendername"),
    @NamedQuery(name = "Letter.findBySenderphone", query = "SELECT l FROM Letter l WHERE l.senderphone = :senderphone"),
    @NamedQuery(name = "Letter.findBySendermail", query = "SELECT l FROM Letter l WHERE l.sendermail = :sendermail"),
    @NamedQuery(name = "Letter.findByMailtitle", query = "SELECT l FROM Letter l WHERE l.mailtitle = :mailtitle"),
    @NamedQuery(name = "Letter.findBySender", query = "SELECT l FROM Letter l WHERE l.sender = :sender")})
public class Letter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "advertisementId")
    private Integer advertisementId;
    @Size(max = 30)
    @Column(name = "sendername")
    private String sendername;
    @Size(max = 12)
    @Column(name = "senderphone")
    private String senderphone;
    @Size(max = 30)
    @Column(name = "sendermail")
    private String sendermail;
    @Size(max = 50)
    @Column(name = "mailtitle")
    private String mailtitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "mailtext")
    private String mailtext;
    @Column(name = "sender")
    private Boolean sender;
    @JoinColumn(name = "advertiserId", referencedColumnName = "id")
    @ManyToOne
    private Advertiser advertiserId;
    @Column(name = "senddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

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

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public Integer getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Integer advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getSenderphone() {
        return senderphone;
    }

    public void setSenderphone(String senderphone) {
        this.senderphone = senderphone;
    }

    public String getSendermail() {
        return sendermail;
    }

    public void setSendermail(String sendermail) {
        this.sendermail = sendermail;
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

    public Advertiser getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Advertiser advertiserId) {
        this.advertiserId = advertiserId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
        return id.toString();
    }

}
