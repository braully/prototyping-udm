/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author braully
 */
public class HtmlAngularBootstrap extends HtmlElement {

    public static final String DEFAULT_TYPE = "div";
    public static final String FORM_TYPE = "form";
    Class classe;

    List<HtmlElement> elements;

    public HtmlAngularBootstrap() {
    }

    public HtmlAngularBootstrap(String model,
            Class classe,
            String type) {
        this.classe = classe;
        this.property = model;
        this.type = type;
    }

    public HtmlAngularBootstrap(Class classe, String type) {
        this(decapitalize(classe.getSimpleName()), classe, type);

        ReflectionUtils.doWithFields(classe, (Field field) -> {
            addHtmlElement(field);
        }, (final Field field) -> {
            final int modifiers = field.getModifiers();
            return !Modifier.isStatic(modifiers);
        });
    }

    public HtmlAngularBootstrap(Class classe) {
        this(decapitalize(classe.getSimpleName()), classe, DEFAULT_TYPE);

        ReflectionUtils.doWithFields(classe, (Field field) -> {
            addHtmlElement(field);
        }, (final Field field) -> {
            final int modifiers = field.getModifiers();
            return !Modifier.isStatic(modifiers);
        });
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
