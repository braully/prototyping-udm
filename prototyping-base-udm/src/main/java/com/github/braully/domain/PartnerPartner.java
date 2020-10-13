//
// This file was generated by the JPA Modeler
//
package com.github.braully.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PartnerPartner implements Serializable {

    @Id
    @ManyToOne(targetEntity = Partner.class)
    private Partner partnerTarget;

    @Id
    @ManyToOne(targetEntity = Partner.class)
    private Partner partnerSource;

    @Id
    private Long id;

    @Basic
    private String type;

    public PartnerPartner() {

    }

    public Partner getPartnerTarget() {
        return this.partnerTarget;
    }

    public void setPartnerTarget(Partner partnerTarget) {
        this.partnerTarget = partnerTarget;
    }

    public Partner getPartnerSource() {
        return this.partnerSource;
    }

    public void setPartnerSource(Partner partnerSource) {
        this.partnerSource = partnerSource;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}