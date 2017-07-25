/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.app;

//import java.util.HashMap;
//import java.util.Map;
import javax.annotation.PostConstruct;
//import org.apache.commons.lang3.tuple.ImmutablePair;
//import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author strike
 */
@Component
public class StatisticalConsolidation {

//    enum EntityStatisticalInformation {
//        CREATE, READ, UPDATE, DELETE, TOTAL
//    }
    
    static {

    }

    @Autowired
    GenericDAO genericDAO;

    @PostConstruct
    void loadInitialData() {

    }

//    private static final Map<Pair<Class, EntityStatisticalInformation>, Integer> entityCountStatiscInformation = new HashMap<>();
//
//    public int entityCountStatiscInformation(Class entityClass, EntityStatisticalInformation operationEntity) {
//        int ret = 0;
//        Pair<Class, EntityStatisticalInformation> pair = new ImmutablePair<>(entityClass, operationEntity);
//        Integer cachedVal = entityCountStatiscInformation.get(pair);
//        if (cachedVal == null) {
//            cachedVal = calcCountStatiscInformation(entityClass, operationEntity);
//            entityCountStatiscInformation.put(pair, cachedVal);
//        } else {
//            ret = cachedVal;
//        }
//        return ret;
//    }
//
//    public int exposedCountStatiscInformation(Class entityClass, EntityStatisticalInformation operationEntity) {
//        int ret = 0;
//        return ret;
//    }
//
//    private Integer calcCountStatiscInformation(Class entityClass, EntityStatisticalInformation operationEntity) {
//        Integer ret = 0;
//        if (operationEntity != null) {
//            switch (operationEntity) {
//                case TOTAL:
//                    try {
//                        ret = genericDAO.count(entityClass);
//                    } catch (Exception e) {
//                        ret = 0;
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//        return ret;
//    }    
    
    
    public int countEntity(Class classe) {
        int count = 0;
        try {
            count = genericDAO.count(classe);
        } catch (Exception e) {

        }
        return count;
    }
}
