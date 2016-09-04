package com.github.braully.app;

import com.github.braully.sak.persistence.DAO;
import com.github.braully.sak.persistence.IEntity;
import com.github.braully.sak.persistence.IUser;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
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

    @Transactional
    @Override
    public void saveEntity(IEntity e) {
        super.saveEntity(e);
    }

    @Transactional
    @Override
    public void update(Object entity) {
        super.update(entity);
    }

    @Transactional
    @Override
    public void insert(Object entity) {
        super.insert(entity);
    }

    @Transactional
    @Override
    public void delete(Object entity) {
        super.delete(entity);
    }

    @Transactional
    @Override
    public <T> void delete(Object id, Class<T> classe) {
        super.delete(id, classe);
    }

//    public Session getSession() {
//        Object delegate = entityManager.unwrap(org.hibernate.Session.class);
//        return (Session) delegate;
//    }
//
//    public SessionFactory getSessionFactory() {
//        Object delegate = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
//        return (SessionFactory) delegate;
//    }
    @Transactional
    public int count(Class entiClass) {
        return this.quantidadeEntitys(entiClass);
    }
}
