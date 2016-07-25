/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.bsfinance;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import com.github.braully.domain.Partner;
import java.util.HashMap;
import java.util.List;
import com.github.braully.sak.persistence.IEntity;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author strike
 */
@RestController
public class EntityRESTfulWS {

    private static final Logger log = Logger.getLogger(EntityRESTfulWS.class);

    @Autowired
    private GenericDAO genericDAO;
    @Autowired(required = false)
    protected ServletRequest request;

    private static final Map<String, Class> LISTED_ENTITY = new HashMap<>();

    static {
        LISTED_ENTITY.put("partner", Partner.class);
    }

    @RequestMapping(value = {"/rest/{classe}/{id}"}, method = RequestMethod.GET)
    public IEntity getEntity(@PathVariable("classe") String classe,
            @PathVariable("id") Long id) {
        log.info("getEntity()");
        IEntity ret;
        Class entityClass = LISTED_ENTITY.get(classe);
        ret = (IEntity) genericDAO.load(id, entityClass);
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}"}, method = {RequestMethod.POST})
    @ResponseBody
    public IEntity createEntity(@PathVariable("classe") String classe) {
        log.info("createEntity()");
        IEntity ret = null;
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}/{id}"}, method = {RequestMethod.PUT})
    @ResponseBody
    public IEntity updateEntity(@PathVariable("classe") String classe,
            @PathVariable("id") Integer id) {
        log.info("updateEntity()");
        IEntity entidade = null;
        if (classe != null && !classe.trim().isEmpty()) {
            try {
                ServletInputStream inputStream = request.getInputStream();
                Class classeMapeada = null;
                if (inputStream != null) {
                    classeMapeada = LISTED_ENTITY.get(classe);
                    ObjectMapper mapper = new ObjectMapper();
                    entidade = (IEntity) mapper.readValue(inputStream, classeMapeada);
                }
            } catch (IOException ex) {
                log.error("Falha ao obter conteudo do servlete", ex);
            }
            if (entidade instanceof IEntity) {
                genericDAO.saveEntity((IEntity) entidade);
            }
        }
        return entidade;
    }

    @RequestMapping(value = {"/rest/{classe}/{id}"},
            method = RequestMethod.DELETE)
    public void removeEntity(@PathVariable("classe") String classe,
            @PathVariable("id") Integer id) {
        log.info("removeEntity()");
    }

    @RequestMapping(value = {"/rest/{classe}"},
            method = RequestMethod.GET)
    @ResponseBody
    public List listEntity(@PathVariable("classe") String classe) {
        log.info("listEntity()");
        List ret = null;
        Class entityClass = LISTED_ENTITY.get(classe);
        ret = genericDAO.loadCollection(entityClass);
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}/search"},
            method = RequestMethod.GET)
    @ResponseBody
    public List searchEntity(@PathVariable("classe") String classe,
            @RequestParam Map<String, String> allRequestParams,
            ModelMap model) {
        log.info("searchEntity()");
        List ret = null;
        Class entityClass = LISTED_ENTITY.get(classe);
        ret = genericDAO.loadCollection(entityClass);
        return ret;
    }
}
