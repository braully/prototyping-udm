/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author strike
 */
public class DescriptorExposedEntity {

    static final String[] DEFAULT_HIDDEN_FORM_FIELDS = new String[]{"id", "version"};

    Class classExposed;
    Set<String> hiddenFormProperties;
    Set<String> hiddenListProperties;
    Set<String> excludeProperties;
    String searchNameMethod;

    public DescriptorExposedEntity() {
        hiddenFormProperties = new HashSet<>();
        hiddenListProperties = new HashSet<>();
        Arrays.stream(DEFAULT_HIDDEN_FORM_FIELDS).forEach(s -> hiddenFormProperties.add(s));
    }

    public DescriptorExposedEntity(Class aClass) {
        this();
        this.classExposed = aClass;
    }

    public Class getClassExposed() {
        return classExposed;
    }

    public DescriptorExposedEntity hiddenForm(String... hiddenProperties) {
        if (hiddenProperties != null) {
            Arrays.stream(hiddenProperties).forEach(hp -> this.hiddenFormProperties.add(hp));
        }
        return this;
    }

    public DescriptorExposedEntity hiddenList(String... hiddenProperties) {
        if (hiddenProperties != null) {
            Arrays.stream(hiddenProperties).forEach(hp -> this.hiddenListProperties.add(hp));
        }
        return this;
    }

    public Map<String, String> sanitizeFilterParams(Map<String, String> params) {
        return params;
    }

    public String getSearchNameMethod() {
        return searchNameMethod;
    }

    public DescriptorExposedEntity filterMethod(String searchMethod) {
        this.searchNameMethod = searchMethod;
        return this;
    }
}
