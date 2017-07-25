/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author braully
 */
public class DescriptorHtmlEntity {


    static final String TYPE_SELECT_ONE = "selectone";
    static final String TYPE_SELECT_MANY = "selectmany";

    List<DescriptorHtmlEntity> elementsForm;
    List<DescriptorHtmlEntity> elementsFilter;
    List<DescriptorHtmlEntity> elementsList;
    Set<String> hiddenFormProperties;
    Set<String> hiddenFilterProperties;
    Set<String> hiddenListProperties;
    Set<String> exclude = new HashSet<>();

    String id;
    String label;
    String type;
    String property;
    String pattern;
    String mdSize;
    String sort;

    Class classe;

    Map<String, String> attributes;

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
            addHtmlFilterElement(field);
            addHtmlListElement(field);
        });

        Collections.sort(elementsList, new Comparator<DescriptorHtmlEntity>() {
            @Override
            public int compare(DescriptorHtmlEntity t, DescriptorHtmlEntity t1) {
                try {
                    return t.property.compareToIgnoreCase(t1.property);
                } catch (Exception e) {
                }
                return 0;
            }
        });
    }

    private DescriptorHtmlEntity(Class classe) {
        this(decapitalize(classe.getSimpleName()), classe, null);
    }

    public DescriptorHtmlEntity(DescriptorExposedEntity descriptorExposedEntity) {
        this(descriptorExposedEntity.classExposed);
        this.hiddenFormProperties = descriptorExposedEntity.hiddenFormProperties;
        this.hiddenFilterProperties = descriptorExposedEntity.hiddenFilterProperties;
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
            elementsList.add(buildDescriptorHtmlEntity(field));
        }
    }

    private void addHtmlFilterElement(Field field) {
        if (elementsFilter == null) {
            elementsFilter = new ArrayList<>();
        }
        final int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)
                && !hiddenFilterProperties.contains(field.getName())) {
            elementsFilter.add(buildDescriptorHtmlEntity(field));
        }
    }

    private void addHtmlFormElement(Field field) {
        if (elementsForm == null) {
            elementsForm = new ArrayList<>();
        }
        final int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)
                && !hiddenFormProperties.contains(field.getName())) {
            DescriptorHtmlEntity htmlElement = buildDescriptorHtmlEntity(field);
            elementsForm.add(htmlElement);
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

    DescriptorHtmlEntity buildDescriptorHtmlEntity(Field field) {
        String ltype = field.getType().getSimpleName();
        ltype = ltype.substring(0, 1).toLowerCase() + ltype.substring(1);
        String lproperty = field.getName();
        String lpattern = null;
        if (field.getAnnotation(OneToOne.class) != null
                || field.getAnnotation(ManyToOne.class) != null) {
            lpattern = ltype;
            ltype = "entity";
        }
        if (field.getType().isAssignableFrom(Collection.class)
                && (field.getAnnotation(ManyToMany.class) != null
                || field.getAnnotation(OneToMany.class) != null)) {
            lpattern = ltype;
            ltype = "collection";
        }
        String llabel = lproperty.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
        llabel = WordUtils.capitalize(llabel);

        DescriptorHtmlEntity he = new DescriptorHtmlEntity();
        he.classe = field.getType();
        he.type = ltype;
        he.property = lproperty;
        he.label = llabel;
        he.pattern = lpattern;
        return he;
    }
}
