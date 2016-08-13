/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author braully
 */
public class DescriptorHtmlEntity extends HtmlElement {

    public static final String DEFAULT_TYPE = "div";
    public static final String FORM_TYPE = "form";
    Class classe;

    List<HtmlElement> elements;
    Set<String> hidden;
    Set<String> exclude = new HashSet<>();

    public DescriptorHtmlEntity() {
    }

    public DescriptorHtmlEntity(String model,
            Class classe,
            String type) {
        this.classe = classe;
        this.property = model;
        this.type = type;
    }

    public DescriptorHtmlEntity(Class classe, String type) {
        this(decapitalize(classe.getSimpleName()), classe, type);

        parseFieldClass(classe);
    }

    private void parseFieldClass(Class classe1) {
        ReflectionUtils.doWithFields(classe1, (Field field) -> {
            addHtmlElement(field);
        }, (final Field field) -> {
            final int modifiers = field.getModifiers();
            return !Modifier.isStatic(modifiers)
                    && !exclude.contains(field.getName());
        });
    }

    public DescriptorHtmlEntity(Class classe) {
        this(decapitalize(classe.getSimpleName()), classe, DEFAULT_TYPE);
        parseFieldClass(classe);
    }

    public DescriptorHtmlEntity(DescriptorExposedEntity descriptorExposedEntity) {
        this(descriptorExposedEntity.classExposed);
        this.hidden = descriptorExposedEntity.hiddenProperties;
    }

    private void addHtmlElement(Field field) {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        elements.add(new HtmlElement(field));
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }
}
