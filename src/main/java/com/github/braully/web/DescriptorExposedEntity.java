/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author strike
 */
public class DescriptorExposedEntity {

    Class classExposed;
    Set<String> hiddenProperties;
    Set<String> excludeProperties;

    public DescriptorExposedEntity() {
        hiddenProperties = new HashSet<>();
    }

    public DescriptorExposedEntity(Class aClass) {
        this();
        this.classExposed = aClass;
    }

    public Class getClassExposed() {
        return classExposed;
    }

    public void hidden(String... hiddenProperties) {
        if (hiddenProperties != null) {
            Arrays.stream(hiddenProperties).forEach(hp -> this.hiddenProperties.add(hp));
        }
    }
}
