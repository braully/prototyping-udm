package com.github.braully.app;

import com.github.braully.web.DescriptorExposedEntity;
import com.github.braully.domain.Menu;
import com.github.braully.web.GeneratorHtmlAngularBootstrap;
import com.github.braully.web.DescriptorHtmlEntity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author braully
 */
@RestController
public class AngularJSWS {

    static final String DEFAULT_HTML = "<span>error</span>";

    static final Map<String, DescriptorHtmlEntity> FORM_ENTITY_DESCRIPTOR_CACHE = new HashMap<>();

    static final GeneratorHtmlAngularBootstrap GENERATOR_HTML = new GeneratorHtmlAngularBootstrap();

    static final String DEFAULT_APP_NAME = "baseApp";

    static final String DEFAULT_JS_TXT = "angular.module('%s').controller('%s', function ($scope, $controller) {\n"
            + "    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));\n"
            + "    \n"
            + "    $scope.%s = [];\n"
            + "    $scope.%s = { classe: '%s' };\n"
            + "});\n"
            + "\n";

    /*
    
     */
    @RequestMapping(value = {"/user/menu"},
            method = RequestMethod.GET)
    public List<Menu> getUserMenus() {
        List<Menu> ret = new ArrayList<>();
        Menu m = new Menu();
        m.setId(1l);
        m.setName("Sale");
        m.setIcon("table");
        m.setValue("Sale Section");
        m.setLink("/sale");
        ret.add(m);

        m = new Menu();
        m.setId(2l);
        m.setName("Partner");
        m.setIcon("user");
        m.setValue("Partner");
        m.setLink("/partner");
        ret.add(m);
        return ret;
    }

    @RequestMapping(value = {"/component/js/{classe}.js"},
            method = RequestMethod.GET, produces = "application/javascript")
    public String getComponentJavaScript(@PathVariable("classe") String classe) {
        String appName, controllerName, listName, varName, className;
        appName = DEFAULT_APP_NAME;
        className = classe;
        controllerName = className + "Controller";
        listName = className + "s";
        varName = className;
//        String ret = MessageFormat.format(DEFAULT_JS_TXT, appName, controllerName, listName, varName, className);
        String ret = String.format(DEFAULT_JS_TXT, appName, controllerName, listName, varName, className);
        return ret;
    }

    @RequestMapping(value = {"/component/table/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentTable(@PathVariable("classe") String classe) {
        String ret = "                        <div class=\"table-responsive\">\n" +
                "                            <table id=\"mytable\" class=\"table table-bordred table-striped\">\n" +
                "                                <thead>\n" +
                "                                <th><input type=\"checkbox\" id=\"checkall\"/></th>\n" +
                "                                <th>First Name</th>\n" +
                "                                <th>Last Name</th>\n" +
                "                                <th>Address</th>\n" +
                "                                <th>Email</th>\n" +
                "                                <th>Contact</th>\n" +
                "                                <th>Edit</th>\n" +
                "                                <th>Delete</th>\n" +
                "                                </thead>\n" +
                "                                <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td><input type=\"checkbox\" class=\"checkthis\"/></td>\n" +
                "                                    <td>Mohsin</td>\n" +
                "                                    <td>Irshad</td>\n" +
                "                                    <td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>\n" +
                "                                    <td>isometric.mohsin@gmail.com</td>\n" +
                "                                    <td>+923335586757</td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
                "                                            <button class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#edit\"><span class=\"glyphicon glyphicon-pencil\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
                "                                            <button class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td><input type=\"checkbox\" class=\"checkthis\"/></td>\n" +
                "                                    <td>Mohsin</td>\n" +
                "                                    <td>Irshad</td>\n" +
                "                                    <td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>\n" +
                "                                    <td>isometric.mohsin@gmail.com</td>\n" +
                "                                    <td>+923335586757</td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
                "                                            <button class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#edit\"><span class=\"glyphicon glyphicon-pencil\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
                "                                            <button class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td><input type=\"checkbox\" class=\"checkthis\"/></td>\n" +
                "                                    <td>Mohsin</td>\n" +
                "                                    <td>Irshad</td>\n" +
                "                                    <td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>\n" +
                "                                    <td>isometric.mohsin@gmail.com</td>\n" +
                "                                    <td>+923335586757</td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
                "                                            <button class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#edit\"><span class=\"glyphicon glyphicon-pencil\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
                "                                            <button class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td><input type=\"checkbox\" class=\"checkthis\"/></td>\n" +
                "                                    <td>Mohsin</td>\n" +
                "                                    <td>Irshad</td>\n" +
                "                                    <td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>\n" +
                "                                    <td>isometric.mohsin@gmail.com</td>\n" +
                "                                    <td>+923335586757</td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
                "                                            <button class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#edit\"><span class=\"glyphicon glyphicon-pencil\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
                "                                            <button class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td><input type=\"checkbox\" class=\"checkthis\"/></td>\n" +
                "                                    <td>Mohsin</td>\n" +
                "                                    <td>Irshad</td>\n" +
                "                                    <td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>\n" +
                "                                    <td>isometric.mohsin@gmail.com</td>\n" +
                "                                    <td>+923335586757</td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
                "                                            <button class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#edit\"><span class=\"glyphicon glyphicon-pencil\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
                "                                            <button class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
                "                                                    data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
                "                                            </button>\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                        <div class=\"row\">\n" +
                "                            <div class=\"col-xs-4\">\n" +
                "                                <div class=\"btn-group pagination\" role=\"group\">\n" +
                "                                    <button type=\"button\" class=\"btn btn-default dropdown-toggle\"\n" +
                "                                            data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
                "                                        <span class=\"glyphicon glyphicon-th\"></span>\n" +
                "                                        Em Planilha\n" +
                "                                        <span class=\"caret\"></span>\n" +
                "                                    </button>\n" +
                "                                    <ul class=\"dropdown-menu\">\n" +
                "                                        <li><a href=\"#\">Toda tabela</a></li>\n" +
                "                                        <li><a href=\"#\">Somente selecionados</a></li>\n" +
                "                                        <li><a href=\"#\">Apenas da pagina atual</a></li>\n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"col-xs-8\">\n" +
                "                                <ul class=\"pagination pull-right\">\n" +
                "                                    <li class=\"disabled\"><a href=\"#\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>\n" +
                "                                    </li>\n" +
                "                                    <li class=\"active\"><a href=\"#\">1</a></li>\n" +
                "                                    <li><a href=\"#\">2</a></li>\n" +
                "                                    <li><a href=\"#\">3</a></li>\n" +
                "                                    <li><a href=\"#\">4</a></li>\n" +
                "                                    <li><a href=\"#\">5</a></li>\n" +
                "                                    <li><a href=\"#\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a></li>\n" +
                "                                </ul>\n" +
                "                            </div>\n" +
                "                        </div>";
        return ret;
    }

    @RequestMapping(value = {"/component/table/{classe}/simple"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentTableSimple(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderTable(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/filter/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentFilter(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderFilter(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/form/{classe}"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentForm(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderForm(htmlDescriptor);
        }
        return ret;
    }

    @RequestMapping(value = {"/component/form/{classe}/lines"},
            method = RequestMethod.GET, produces = "text/html")
    public String getComponentFormLines(@PathVariable("classe") String classe) {
        String ret = DEFAULT_HTML;
        DescriptorHtmlEntity htmlDescriptor = getDescriptorHtmlEntity(classe);
        if (htmlDescriptor != null) {
            ret = GENERATOR_HTML.renderFormOnlyChilds(htmlDescriptor);
        }
        return ret;
    }

    private DescriptorHtmlEntity getDescriptorHtmlEntity(String classe) {
        DescriptorExposedEntity descriptorExposedEntity = EntityRESTfulWS.EXPOSED_ENTITY.get(classe);
        DescriptorHtmlEntity descriptorHtmlEntity = null;
        if (descriptorExposedEntity != null) {
            descriptorHtmlEntity = FORM_ENTITY_DESCRIPTOR_CACHE.get(classe);
            if (descriptorHtmlEntity == null) {
                descriptorHtmlEntity = new DescriptorHtmlEntity(descriptorExposedEntity);
                FORM_ENTITY_DESCRIPTOR_CACHE.put(classe, descriptorHtmlEntity);
            }
        }
        return descriptorHtmlEntity;
    }
}
