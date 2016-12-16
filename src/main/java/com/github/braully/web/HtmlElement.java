/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.util.Map;

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

    Class classe;

    Map<String, String> attributes;

    public HtmlElement() {
    }

}
