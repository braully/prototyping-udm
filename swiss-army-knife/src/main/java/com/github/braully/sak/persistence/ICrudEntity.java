/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.persistence;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Braully Rocha da Silva
 */
public interface ICrudEntity {

    List loadCollection(String nameCls);

    <T> List<T> loadCollection(Class<T> cls);

    <T> List<T> loadCollectionWhere(Class<T> cls, Object... args);

    <T> List<T> loadCollectionSorted(Class<T> cls, String... desc);

    <T> List<T> loadCollectionFetch(Class<T> cls, String... props);

    <T> T loadEntity(T entity);

    <T> T loadEntity(IEntity e, String... props);

    <T> T loadEntity(String nameEntity, Object id, String... props);

    void saveEntity(IEntity e);

    void saveEntityCascade(IEntity e, Collection<? extends IEntity>... rels);

    void saveEntityFlyWeigth(IEntity entity, String... nameProps);

    void saveEntitys(IEntity... args);

    void delete(Object entity);
}
