/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Braully Rocha
 */
public class UtilReflection {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(UtilReflection.class);

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericTypeArgument(final Class<?> clazz, final int idx) {
        final Type type = clazz.getGenericSuperclass();

        ParameterizedType paramType;
        try {
            paramType = (ParameterizedType) type;
        } catch (ClassCastException cause) {
            paramType = (ParameterizedType) ((Class<T>) type).getGenericSuperclass();
        }

        return (Class<T>) paramType.getActualTypeArguments()[idx];
    }

    public static synchronized <T> T createInstance(Class<T> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException ex) {
            log.error("Failed instance", ex);
        } catch (IllegalAccessException ex) {
            log.error("Failed instance", ex);
        }
        return null;
    }

    public static Object getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return PropertyUtils.getProperty(bean, name);
    }

    public static String getPropertyText(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtils.getProperty(bean, name);
    }
}
