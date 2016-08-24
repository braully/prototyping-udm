package com.github.braully.app;

import com.github.braully.web.DescriptorExposedEntity;
import com.github.braully.domain.Menu;
import com.github.braully.sak.util.UtilIO;
import com.github.braully.web.GeneratorHtmlAngularBootstrap;
import com.github.braully.web.DescriptorHtmlEntity;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author braully
 */
@RestController
public class AngularJSWS {

    static final String DEFAULT_HTML = "<span>error</span>";

    static final Map<String, DescriptorHtmlEntity> FORM_ENTITY_DESCRIPTOR_CACHE = new HashMap<>();

    static final GeneratorHtmlAngularBootstrap GENERATOR_HTML = new GeneratorHtmlAngularBootstrap();

    static final String DEFAULT_APP_NAME = "baseApp";

    static final String DEFAULT_JS_TXT = "/js/partner.js";

    /*
    
     */
    @RequestMapping(value = {"/user/menu"},
            method = RequestMethod.GET)
    public List<Menu> getUserMenus() {
        List<Menu> ret = new ArrayList<>();
        Menu m = new Menu();
        m.setId(1l);
        m.setName("Sale");
        m.setIcon("table");
        m.setValue("Sale Section");
        m.setLink("/teste.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(2l);
        m.setName("Partner");
        m.setIcon("user");
        m.setValue("Partner");
        m.setLink("/partner.xhtml");
        ret.add(m);
        return ret;
    }

    @RequestMapping(value = {"/component/js/{classe}.js"},
            method = RequestMethod.GET, produces = "application/javascript")
    public String getComponentJavaScript(@PathVariable("classe") String classe) {
        String appName, controllerName, listName, varName, className, searchVar;
        appName = DEFAULT_APP_NAME;
        className = classe;
        controllerName = className + "Controller";
        listName = className + "s";
        varName = className;
        searchVar = className + "Search";

//        Map valuesMap = new HashMap();
//        valuesMap.put("partner", className);
//        valuesMap.put("baseApp", DEFAULT_APP_NAME);

//        StrSubstitutor sub = new StrSubstitutor(valuesMap);
//        sub.setVariablePrefix("");
//        sub.setVariableSuffix("");
//        String resolvedString = sub.replace(getTemplateStringControllerJS());
        String resolvedString = getTemplateStringControllerJS().replaceAll("partner", className);
        return resolvedString;
    }

    @RequestMapping(value = {"/component/table/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentTable(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderTable(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/table/{classe}/simple"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentTableSimple(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderTableSimple(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/filter/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentFilter(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderFilter(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/form/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentForm(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderForm(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/form/{classe}/lines"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentFormLines(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderFormOnlyChilds(htmlDescriptor);
        }
        return ret;
    }

    private DescriptorHtmlEntity getDescriptorHtmlEntity(String classe) {
        DescriptorExposedEntity descriptorExposedEntity = EntityRESTfulWS.EXPOSED_ENTITY.get(classe);
        DescriptorHtmlEntity descriptorHtmlEntity = null;
        if (descriptorExposedEntity != null) {
            descriptorHtmlEntity = FORM_ENTITY_DESCRIPTOR_CACHE.get(classe);
            if (descriptorHtmlEntity == null) {
                descriptorHtmlEntity = new DescriptorHtmlEntity(descriptorExposedEntity);
                FORM_ENTITY_DESCRIPTOR_CACHE.put(classe, descriptorHtmlEntity);
            }
        }
        return descriptorHtmlEntity;
    }

    private static String getTemplateStringControllerJS() {
        String ret = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(DEFAULT_JS_TXT);
            ret = FileCopyUtils.copyToString(new InputStreamReader(classPathResource.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(AngularJSWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
