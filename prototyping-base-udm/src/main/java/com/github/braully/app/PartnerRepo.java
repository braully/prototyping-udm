/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.app;

import com.github.braully.domain.Partner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author braully
 */
@Repository
public class PartnerRepo {

    @Autowired
    GenericDAO genericDAO;

    public List searchPartner(String name) {
        if (name == null) {
            return genericDAO.loadCollection(Partner.class);
        }
        EntityManager entityManager = genericDAO.getEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM Partner p WHERE lower(p.name) like :name");
        query.setParameter("name", name.toLowerCase() + "%");
        return query.getResultList();
    }
}
