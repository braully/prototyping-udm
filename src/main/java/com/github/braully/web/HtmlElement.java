/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author braully
 */
public class HtmlElement {

    static final String TYPE_SELECT_ONE = "selectone";
    static final String TYPE_SELECT_MANY = "selectmany";

    String id;
    String label;
    String type;
    String property;
    String pattern;
    String mdSize;
    String sort;

    Map<String, String> attributes;

    public HtmlElement() {
    }

    HtmlElement(Field field) {
        type = field.getType().getSimpleName();
        property = field.getName();
        if (field.getAnnotation(OneToOne.class) != null
                || field.getAnnotation(ManyToOne.class) != null) {
            type = TYPE_SELECT_ONE;
        }
        if (field.getType().isAssignableFrom(Collection.class)
                && (field.getAnnotation(ManyToMany.class) != null
                || field.getAnnotation(OneToMany.class) != null)) {
            type = TYPE_SELECT_MANY;
        }
        label = property.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
        label = WordUtils.capitalize(label);
    }
}
