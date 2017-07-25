/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.domain.util;

import com.github.braully.domain.AbstractEntity;
import com.github.braully.domain.UserLogin;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author strike
 */
@Entity
public class EntityOperation extends AbstractEntity {

    @Basic
    private String classe;
    @Basic
    private Integer countCreate;
    @Basic
    private Integer countRead;
    @Basic
    private Integer countUpdate;
    @Basic
    private Integer countDelete;
    @ManyToOne(targetEntity = UserLogin.class)
    private UserLogin userLogin;

    public EntityOperation() {
    }

    public EntityOperation(String classe, Integer countCreate, Integer countRead, Integer countUpdate, Integer countDelete, UserLogin userLogin) {
        this.classe = classe;
        this.countCreate = countCreate;
        this.countRead = countRead;
        this.countUpdate = countUpdate;
        this.countDelete = countDelete;
        this.userLogin = userLogin;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getCountCreate() {
        return countCreate;
    }

    public void setCountCreate(Integer countCreate) {
        this.countCreate = countCreate;
    }

    public Integer getCountRead() {
        return countRead;
    }

    public void setCountRead(Integer countRead) {
        this.countRead = countRead;
    }

    public Integer getCountUpdate() {
        return countUpdate;
    }

    public void setCountUpdate(Integer countUpdate) {
        this.countUpdate = countUpdate;
    }

    public Integer getCountDelete() {
        return countDelete;
    }

    public void setCountDelete(Integer countDelete) {
        this.countDelete = countDelete;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

}
