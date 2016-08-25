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

    Class classe;

    List<HtmlElement> elementsForm;
    List<HtmlElement> elementsList;
    Set<String> hiddenFormProperties;
    Set<String> hiddenListProperties;
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
            addHtmlFormElement(field);
            addHtmlListElement(field);
        });
    }

    private DescriptorHtmlEntity(Class classe) {
        this(decapitalize(classe.getSimpleName()), classe, null);
    }

    public DescriptorHtmlEntity(DescriptorExposedEntity descriptorExposedEntity) {
        this(descriptorExposedEntity.classExposed);
        this.hiddenFormProperties = descriptorExposedEntity.hiddenFormProperties;
        this.hiddenListProperties = descriptorExposedEntity.hiddenListProperties;
        parseFieldClass(classe);
    }

    private void addHtmlListElement(Field field) {
        if (elementsList == null) {
            elementsList = new ArrayList<>();
        }
        final int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)
                && !hiddenListProperties.contains(field.getName())) {
            elementsList.add(new HtmlElement(field));
        }
    }

    private void addHtmlFormElement(Field field) {
        if (elementsForm == null) {
            elementsForm = new ArrayList<>();
        }
        final int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)
                && !hiddenFormProperties.contains(field.getName())) {
            elementsForm.add(new HtmlElement(field));
        }
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
