/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

import com.sun.faces.application.resource.ResourceHandlerImpl;
import com.sun.faces.application.resource.ResourceImpl;
import com.sun.faces.application.resource.ResourceInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;

/**
 *
 * @author strike
 */
public class JsfResourceHandler extends ResourceHandlerWrapper {

    public static final String JSF_DIRECTORY_PREFIX = "/jsf";

    public static final String AUTOGEN_PREFIX = "/gen";

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

        if (resource == null && resourceName.startsWith(AUTOGEN_PREFIX)
                && resourceName.endsWith("html")) {
            //resource = context.getApplication().getResourceHandler().createResource(resourceName);
//            this.wrapped.createResource(resourceName);
//            new ResourceImpl(resourceInfo, resourceName, 0, 0);
//            ResourceHandlerImpl n;
//            ResourceInfo info = manager.findResource(null, resourceName, ctype, true, facesContext);
//            resource = new ResourceImpl(info, ctype, creationTime, maxAge);
//            resource = new JsfAutogenResource(context.getApplication().getResourceHandler().createResource(resourceName));

            resource = new JsfAutogenResource(resourceName, this.wrapped.createResource("/jsf/index-jsf.xhtml"));
//            resource = new JsfAutogenResource(
//                    new ResourceImpl(new ResourceInfo(null, null, resourceName, null), "application/xhtml+xml", System.currentTimeMillis(), 0));
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

    private static class JsfAutogenResource extends ResourceWrapper {

        private static final String pgdefault = "<!DOCTYPE html>\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
                + "      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"\n"
                + "      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"\n"
                + "      xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">\n"
                + "\n"
                + "    <f:view contentType=\"text/html\" locale=\"pt\" />\n"
                + "\n"
                + "    <ui:composition template=\"/templates/template-jsf.xhtml\">\n"
                + "    </ui:composition>\n"
                + "</html>";

        private String resourceName;
        private Resource wrapped;

        public JsfAutogenResource(String resourceName, Resource wrapped) {
            this.resourceName = resourceName;
            this.wrapped = wrapped;
        }

        private JsfAutogenResource(Resource wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Resource getWrapped() {
            return this.wrapped;
        }

        public JsfAutogenResource(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public String getResourceName() {
            return resourceName;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            InputStream is = new ByteArrayInputStream(pgdefault.getBytes());
            return is;
        }
    }
}
