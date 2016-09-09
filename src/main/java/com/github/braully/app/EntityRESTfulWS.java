package com.github.braully.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.github.braully.web.DescriptorExposedEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.braully.domain.Inventory;
import java.util.Map;
import com.github.braully.domain.Partner;
import com.github.braully.domain.PurchaseOrder;
import java.util.HashMap;
import java.util.List;
import com.github.braully.sak.persistence.IEntity;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private EntitySearch entitySearch;

    static final Map<String, DescriptorExposedEntity> EXPOSED_ENTITY = new HashMap<>();

    static {
        EXPOSED_ENTITY.put("partner", new DescriptorExposedEntity(Partner.class)
                .hiddenForm("phoneticName", "attribute"));
        EXPOSED_ENTITY.put("purchaseOrder", new DescriptorExposedEntity(PurchaseOrder.class));
//                .hiddenForm("partner").hiddenForm("inventory"));
        EXPOSED_ENTITY.put("inventory", new DescriptorExposedEntity(Inventory.class));
    }

    @RequestMapping(value = {"/rest/{classe}/{id}"}, method = RequestMethod.GET)
    public IEntity getEntity(@PathVariable("classe") String classe,
            @PathVariable("id") Long id) {
        log.info("getEntity()");
        IEntity ret = null;
        DescriptorExposedEntity exposedEntity = EXPOSED_ENTITY.get(classe);
        if (exposedEntity != null) {
            ret = (IEntity) genericDAO.load(id, exposedEntity.getClassExposed());
        }
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}"},
            method = {RequestMethod.POST},
            consumes = "application/json")
    @ResponseBody
    public IEntity createEntity(@PathVariable("classe") String classe,
            @RequestBody String jsonEntity) {
        log.info("createEntity()");
        IEntity ret = null;
        DescriptorExposedEntity exposedEntity = EXPOSED_ENTITY.get(classe);
        if (exposedEntity != null && jsonEntity != null && !jsonEntity.isEmpty()) {
            try {
                Class classeMapeada = EXPOSED_ENTITY.get(classe).getClassExposed();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ret = (IEntity) mapper.readValue(jsonEntity, classeMapeada);
                genericDAO.saveEntity(ret);
            } catch (IOException ex) {
                log.error("Falha ao obter conteudo do servlete", ex);
            }
        }
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}/{id}"},
            method = {RequestMethod.PUT})
    @ResponseBody
    public IEntity updateEntity(@PathVariable("classe") String classe,
            @PathVariable("id") Integer id,
            @RequestBody String jsonEntity) {
        log.info("updateEntity()");
        IEntity entidade = null;
        if (classe != null && !classe.trim().isEmpty()) {
            try {
                Class classeMapeada = null;
                if (jsonEntity != null) {
                    classeMapeada = EXPOSED_ENTITY.get(classe).getClassExposed();
                    ObjectMapper mapper = new ObjectMapper();
                    entidade = (IEntity) mapper.readValue(jsonEntity, classeMapeada);
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
    public List listEntity(@PathVariable("classe") String classe,
            @RequestParam(required = false) Map<String, String> params) {
        log.info("listEntity()");
        List ret = null;
        DescriptorExposedEntity exposedEntity = EXPOSED_ENTITY.get(classe);
        if (exposedEntity != null) {
            Class entityClass = exposedEntity.getClassExposed();
            String searchMethodName = exposedEntity.getSearchNameMethod();
            params = exposedEntity.sanitizeFilterParams(params);
            List searchEntitys = entitySearch.searchEntitys(entityClass, searchMethodName, params);
            ret = searchEntitys;
        }
        return ret;
    }

    @RequestMapping(value = {"/rest/{classe}/search"},
            method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public List searchEntity(@PathVariable("classe") String classe,
            @RequestParam Map<String, String> allRequestParams,
            ModelMap model) {
        log.info("searchEntity()");
        List ret = null;
        Class entityClass = EXPOSED_ENTITY.get(classe).getClassExposed();
        ret = genericDAO.loadCollection(entityClass);
        return ret;
    }
}
