package com.github.braully.app;

import com.github.braully.web.DescriptorExposedEntity;
import com.github.braully.domain.Menu;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    StatisticalConsolidation statisticalConsolidation;

    /*
    
     */
    @RequestMapping(value = {"/user/menu"},
            method = RequestMethod.GET)
    public List<Menu> getUserMenus() {
        List<Menu> ret = new ArrayList<>();
        Menu m = new Menu();
        m.setId(1l);
        m.setName("Form Static");
        m.setIcon("wpforms");
        m.setValue("Form Static");
        m.setLink("/form-static.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(2l);
        m.setName("Form");
        m.setIcon("wpforms");
        m.setValue("Form");
        m.setLink("/form.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(3l);
        m.setName("Filter Static");
        m.setIcon("filter");
        m.setValue("Filter Static");
        m.setLink("/filter-static.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(4l);
        m.setName("Filter");
        m.setIcon("filter");
        m.setValue("Filter");
        m.setLink("/filter.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(5l);
        m.setName("Table Static");
        m.setIcon("table");
        m.setValue("Table Static");
        m.setLink("/table-static.xhtml");
        ret.add(m);

        m = new Menu();
        m.setId(6l);
        m.setName("Table");
        m.setIcon("table");
        m.setValue("Table");
        m.setLink("/table.xhtml");
        ret.add(m);
        return ret;
    }

    @RequestMapping(value = {"/component/js/{classe}.js"},
            method = RequestMethod.GET, produces = "application/javascript")
    public String getComponentJavaScript(@PathVariable("classe") String classe,
            @RequestParam(required = false) Map<String, String> params) {
        String className = classe;
        String resolvedString = getTemplateStringControllerJS()
                .replaceAll("partner", className);
        String controllerName = null;
        if (params != null && !StringUtils.isEmpty(controllerName = params.get("controller.name"))) {
            resolvedString = resolvedString.replaceAll("mainController", controllerName);
        }
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
