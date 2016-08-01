/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.lang.reflect.Field;
import java.util.Map;

/**
 *
 * @author braully
 */
public class HtmlElement {

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

    }
}
