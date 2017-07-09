/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.web;

//import com.sun.faces.application.resource.ResourceHandlerImpl;
//import com.sun.faces.application.resource.ResourceImpl;
//import com.sun.faces.application.resource.ResourceInfo;
import com.github.braully.app.EntityRESTfulWS;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static final String AUTOGEN_PREFIX = "/gen/";

    private ResourceHandler wrapped;

    public JsfResourceHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ViewResource createViewResource(FacesContext context, String resourceName) {
        ViewResource resource = super.createViewResource(context, resourceName);
        if (resource == null && !resourceName.startsWith(JSF_DIRECTORY_PREFIX)
                && resourceName.endsWith("html")) {
            resource = super.createViewResource(context, JSF_DIRECTORY_PREFIX + resourceName);
        }

        if (resource == null && resourceName.endsWith("html")) {
//            String resourceTrimed = resourceName.substring(resourceName.lastIndexOf("/") + 1, resourceName.lastIndexOf("."));
//            String nameEntity = resourceName.substring(0, resourceName.lastIndexOf(".")).replaceAll(AUTOGEN_PREFIX, "");
            String nameEntity = resourceName.substring(resourceName.lastIndexOf("/") + 1, resourceName.lastIndexOf("."));
            DescriptorExposedEntity descriptorExposedEntity = EntityRESTfulWS.EXPOSED_ENTITY.get(nameEntity);
            if (descriptorExposedEntity != null) {
                resource = new JsfAutogenResource(resourceName, nameEntity, this.wrapped.createResource(nameEntity));
            }
        }

        return resource;
    }

    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }

    private static class JsfAutogenResource extends ResourceWrapper {

        private static Map<String, String> CACHE_AUTO_GENERATED_FILES = new HashMap<>();

        private static final String pgdefault = "<!DOCTYPE html>\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
                + "      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"\n"
                + "      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"\n"
                + "      xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">\n"
                + "\n"
                + "    <ui:composition template=\"/templates/template-jsf.xhtml\">\n"
                + "        <ui:define name=\"title-page\">purchaseOrder</ui:define>\n"
                + "\n"
                + "        <ui:define name=\"header-append\">\n"
                + "            <script src=\"/app/component/js/purchaseOrder.js\"></script>\n"
                + "        </ui:define>\n"
                + "\n"
                + "        <ui:param name=\"controllerAngular\" value=\"mainController\"/>\n"
                + "\n"
                + "        <ui:define name=\"body-content\">\n"
                + "            <ng-include src=\"'/app/component/form/purchaseOrder'\"></ng-include>\n"
                + "\n"
                + "            <ng-include src=\"'/app/component/table/purchaseOrder'\"></ng-include>\n"
                + "        </ui:define>\n"
                + "    </ui:composition>\n"
                + "    \n"
                + "</html>";

        private String resourceName;
        private Resource wrapped;
        private String nameEntity;

        public JsfAutogenResource(String resourceName, String nameEntity, Resource wrapped) {
            this.resourceName = resourceName;
            this.wrapped = wrapped;
            this.nameEntity = nameEntity;
        }

        @Override
        public Resource getWrapped() {
            return this.wrapped;
        }

        @Override
        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public String getResourceName() {
            return resourceName;
        }

        @Override
        public URL getURL() {
            URL url = null;
            try {
                url = new URL(null, "gen://" + resourceName, new VirtualUrlStreamHandler(pgdefault.replaceAll("purchaseOrder", nameEntity).getBytes("UTF-8")));
            } catch (MalformedURLException ex) {
                Logger.getLogger(JsfResourceHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JsfResourceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return url;
        }
    }

    private static class VirtualUrlStreamHandler extends URLStreamHandler {

        private final byte[] payload;

        public VirtualUrlStreamHandler(byte[] payload) {
            this.payload = payload;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            return new CustomURLConnection(u);
        }

        private class CustomURLConnection extends URLConnection {

            public CustomURLConnection(URL url) {
                super(url);
            }

            @Override
            public void connect() throws IOException {
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(payload);
            }
        }
    }
}
