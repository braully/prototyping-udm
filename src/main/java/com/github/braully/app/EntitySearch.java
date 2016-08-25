package com.github.braully.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author braully
 */
@Service
public class EntitySearch {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private GenericDAO genericDAO;

    List searchEntitys(Class entitityClass, String searchMethod, Map params) {
        Object bean = context.getBean(searchMethod, params);
        Object ret = null;
        try {
            Method method = bean.getClass().getMethod(searchMethod);
            Parameter[] parametersDescription = method.getParameters();
            Object[] paramObjects = new Object[parametersDescription.length];
            int i = 0;
            for (Parameter p : parametersDescription) {
                String name = p.getName();
                Object pValue = params.get(name);
                Object value = convertParameter(pValue, p.getType());
                paramObjects[i++] = value;
            }
            ret = MethodUtils.invokeMethod(bean, searchMethod, paramObjects);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(EntitySearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (List) ret;
    }

    private Object convertParameter(Object pValue, Class type) {
        return pValue;
    }

    public List defaultSearchEntity(Class entitityClass, Map parameters) {
        return null;
    }
}
