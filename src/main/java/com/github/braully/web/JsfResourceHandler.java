/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;

/**
 *
 * @author strike
 */
public class JsfResourceHandler extends ResourceHandlerWrapper {

    public static final String JSF_DIRECTORY_PREFIX = "/jsf";

    private ResourceHandler wrapped;

    public JsfResourceHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ViewResource createViewResource(FacesContext context, String resourceName) {
        ViewResource resource = null;
        if (!resourceName.startsWith(JSF_DIRECTORY_PREFIX)
                && resourceName.endsWith("html")) {
            resource = super.createViewResource(context, JSF_DIRECTORY_PREFIX + resourceName);
        }
        if (resource == null) {
            resource = super.createViewResource(context, resourceName);
        }
        return resource;
    }

    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }
}
