/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.bsfinance;

import com.github.braully.sak.persistence.DAO;
import com.github.braully.sak.persistence.IUser;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author braully
 */
@Repository
public class GenericDAO extends DAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected IUser getUserOperation() {
        return null;
    }
}
