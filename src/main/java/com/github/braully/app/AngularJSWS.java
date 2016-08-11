package com.github.braully.app;

import com.github.braully.domain.Menu;
import com.github.braully.domain.Partner;
import com.github.braully.web.GeneratorHtmlAngularBootstrap;
import com.github.braully.web.HtmlAngularBootstrap;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author braully
 */
@RestController
public class AngularJSWS {

    private static final String DEFAULT_HTML = "<span>error</span>";

    private static final Map<String, HtmlAngularBootstrap> FORM_ENTITY = new HashMap<>();

    private static final GeneratorHtmlAngularBootstrap GENERATOR_HTML = new GeneratorHtmlAngularBootstrap();

    static {
        FORM_ENTITY.put("partner", new HtmlAngularBootstrap(Partner.class));
    }

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
        m.setLink("/sale");
        ret.add(m);

        m = new Menu();
        m.setId(2l);
        m.setName("Partner");
        m.setIcon("user");
        m.setValue("Partner");
        m.setLink("/partner");
        ret.add(m);
        return ret;
    }

    @RequestMapping(value = {"/component/form/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponent(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        HtmlAngularBootstrap htmlDescriptor = FORM_ENTITY.get(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderInputs(htmlDescriptor);
        }
        return ret;
    }
}
