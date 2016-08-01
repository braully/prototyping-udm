/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.bsfinance;

import com.github.braully.domain.Menu;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static j2html.TagCreator.*;
import j2html.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author braully
 */
@RestController
public class AngularJSWS {

    @RequestMapping(value = {"/app/user/menu"}, method = RequestMethod.GET)
    public List<Menu> getUserMenus() {
        List<Menu> ret = new ArrayList<>();
        Menu m = new Menu();
        m.setId(1l);
        m.setName("Sale");
        m.setIcon("table");
        m.setValue("Sale Section");
        m.setLink("/sale");
        ret.add(m);
        return ret;
    }

    @RequestMapping(value = {"/app/component/form/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponent(@PathVariable("classe") String classe) {
        Tag form = form();

        
        
        return form.render();
    }
}
