package com.github.braully.web;

import com.github.braully.app.AngularJSWS;
import com.github.braully.app.StatisticalConsolidation;
import j2html.TagCreator;
import j2html.tags.ContainerTag;
import j2html.tags.EmptyTag;
import j2html.tags.Tag;

import static j2html.TagCreator.*;
import j2html.tags.UnescapedText;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import org.springframework.util.StringUtils;

/**
 * @author braully
 */
public class GeneratorHtmlAngularBootstrap {

    public static final int THRESHOLD_ENTITY_COUNT_SELECT = 5;

    public static final String FILE_INPUT_SELECT = "/templates/input/input-select.html";
    public static final String FILE_INPUT_AUTOCOMPLETE = "/templates/input/input-autocomplete.html";
    public static final String DEFAULT_TYPE = "div";
    public static final String FORM_TYPE = "form";
    public static final String TABLE_TYPE = "table";
    public static final String INPUT_TYPE = "input";
    public static final String CHECK_TYPE = "check";
    public static final String DATE_TYPE = "date";
    public static final String NG_MODEL = "ng-model";
    public static final String LABEL_CLASS = "col-sm-1";
    public static final String ROW_CLASS_FORM = "form-group";

    public String renderForm(DescriptorHtmlEntity html, StatisticalConsolidation statisticalConsolidation) {
        return renderFormLocal(html, statisticalConsolidation).render();
    }

    public String renderForm(DescriptorHtmlEntity html) {
        return renderForm(html, null);
    }

    public String renderFormOnlyChilds(DescriptorHtmlEntity html) {
        return renderFormOnlyChilds(html, null);
    }

    public String renderFormOnlyChilds(DescriptorHtmlEntity html, StatisticalConsolidation statisticalConsolidation) {
        ContainerTag txtHtml = renderFormLocal(html, statisticalConsolidation);
        StringBuilder childs = new StringBuilder();
        if (txtHtml.children != null) {
            txtHtml.children.stream().forEach((t) -> {
                childs.append(t.render());
            });
        }
        return childs.toString();
    }

    private ContainerTag renderFormLocal(DescriptorHtmlEntity html, StatisticalConsolidation statisticalConsolidation) {
        String typeRoot = html.type;
        if (typeRoot == null) {
            typeRoot = FORM_TYPE;
        }
        ContainerTag txtHtml = new ContainerTag(typeRoot);
        if (html.elementsForm != null) {
            html.elementsForm.forEach((ef) -> {
                txtHtml.with(buildFormElemet(ef, statisticalConsolidation));
            });
        }
        return txtHtml;
    }

    private Tag buildFormElemet(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        Tag ret = null;
        if (he != null) {
            ContainerTag row = null;
            if (!StringUtils.isEmpty(he.label)) {
                row = TagCreator.div();
                row.withClass(ROW_CLASS_FORM);
                ContainerTag labelHtml = TagCreator.label(he.label);
                row.with(labelHtml);
            }
            ret = getHtmlFormElement(he, statisticalConsolidation);
            if (row != null) {
                row.with(ret);
                ret = row;
            }
        }
        return ret;
    }

    private Tag getHtmlFormElement(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        Tag ret = null;
        if (he != null && he.type != null) {
            String type = he.type.toLowerCase();
            Tag rt = null;
            switch (type) {
                case "string":
                case "boolean":
                case "date":
                case "int":
                case "integer":
                case "long":
                case "float":
                case "double":
                    rt = buildSimpleInput(he, statisticalConsolidation);
                    break;
                case "entity":
                    rt = entityHtmlFormElement(he, statisticalConsolidation);
                    break;
                case "collection":
                    rt = collectionHtmlFormElement(he, statisticalConsolidation);
                    break;
                case HtmlElement.TYPE_SELECT_ONE:
                case HtmlElement.TYPE_SELECT_MANY:
                default:
                    rt = buildGenericInput(he, statisticalConsolidation);
                    break;
            }
            ret = rt;
        }
        return ret;
    }

    private String buildNgModelPath(String... property) {
        return buildNgModelPath(false, property);
    }

    private String buildNgModelPath(boolean brackets, String... property) {
        StringBuilder sb = new StringBuilder();
        if (brackets) {
            sb.append("{{ ");
        }
        if (property != null && property.length > 0) {
            for (int i = 0; i < property.length; i++) {
                String p = property[i];
                sb.append(p);
                if (i == property.length - 2) {
                    sb.append(".");
                }
            }
        }
        if (brackets) {
            sb.append(" }}");
        }
        return sb.toString();
    }

    private String buildNgRepeatPath(String var, String collection) {
        StringBuilder sb = new StringBuilder();
        if (var != null && collection != null) {
            sb.append(var);
            sb.append(" in ");
            sb.append(collection);
        }
        return sb.toString();
    }

    public String renderTableSimple(DescriptorHtmlEntity htmlDescriptor, StatisticalConsolidation statisticalConsolidation) {
        String typeRoot = htmlDescriptor.type;
        if (typeRoot == null) {
            typeRoot = TABLE_TYPE;
        }
        ContainerTag txtHtml = new ContainerTag(typeRoot);

        if (htmlDescriptor.elementsForm != null) {
            ContainerTag thead = TagCreator.thead();
            ContainerTag tr = TagCreator.tr();

            for (HtmlElement he : htmlDescriptor.elementsForm) {
                if (!StringUtils.isEmpty(he.label)) {
                    ContainerTag th = TagCreator.th(he.label);
                    tr.with(th);
                }
            }
            thead.with(tr);
            txtHtml.with(thead);

            String var = htmlDescriptor.property + "Item";
            String collection = "model.entities";

            ContainerTag tbody = TagCreator.tbody();
            ContainerTag trBody = TagCreator.tr();
            trBody.attr("ng-repeat", buildNgRepeatPath(var, collection));

            for (HtmlElement he : htmlDescriptor.elementsForm) {
                ContainerTag td = TagCreator.td();
                if (he.attributes != null) {
                    he.attributes.entrySet().stream().forEach((at) -> {
                        td.setAttribute(at.getKey(), at.getValue());
                    });
                }
                td.withText(buildNgModelPath(true, var, he.property));
                trBody.with(td);
            }
            tbody.with(trBody);
            txtHtml.with(tbody);
        }
        return txtHtml.render();
    }

    public String renderFilter(DescriptorHtmlEntity html) {
        return renderFilter(html, null);
    }

    public String renderFilter(DescriptorHtmlEntity html, StatisticalConsolidation statisticalConsolidation) {
        String typeRoot = html.type;
        String beanRoot = "model.search";
        if (typeRoot == null) {
            typeRoot = FORM_TYPE;
        }
        ContainerTag txtHtml = new ContainerTag(typeRoot);
        ContainerTag collapse = div().withClass("collapse").withId("advanced-search");

        if (html.elementsForm != null) {
            for (HtmlElement he : html.elementsForm) {
                ContainerTag container;
                //he.principalFilter
                boolean hePrincipal = Math.random() <= 0.5;
                if (hePrincipal) {
                    container = txtHtml;
                } else {
                    container = collapse;
                }
                ContainerTag parent = container;

                if (!StringUtils.isEmpty(he.label)) {
                    parent = TagCreator.div();
                    parent.withClass(ROW_CLASS_FORM);
                    container.with(parent);
                    ContainerTag labelHtml = TagCreator.label(he.label);
                    parent.with(labelHtml);
                }

                Tag the = getHtmlFormElement(he, statisticalConsolidation);
                if (he.attributes != null) {
                    he.attributes.entrySet().stream().forEach((at) -> {
                        the.setAttribute(at.getKey(), at.getValue());
                    });
                }
                the.setAttribute("class", "form-control");
                the.setAttribute(NG_MODEL, buildNgModelPath(beanRoot, he.property));
                parent.with(the);
            }
        }

        txtHtml.with(collapse);
        txtHtml.with(button("Advanced").withClass("btn btn-default").withType("button")
                .attr("data-toggle", "collapse").attr("data-target", "#advanced-search").attr("aria-expand", "false")
                .attr("aria-controls", "advanced-search")
                .with(span().withClass("glyphicon glyphicon-option-vertical")));

        txtHtml.with(button().withClass("btn btn-default pull-right").withType("Search")
                .with(span().withClass("glyphicon glyphicon-search")).withText("Search"));

        if (true) {
            StringBuilder childs = new StringBuilder();
            if (txtHtml.children != null) {
                txtHtml.children.stream().forEach((t) -> {
                    childs.append(t.render());
                });
            }
            return childs.toString();
        } else {
            return txtHtml.render();
        }
    }

    public String renderTable(DescriptorHtmlEntity htmlDescriptor, StatisticalConsolidation statisticalConsolidation) {
        String typeRoot = htmlDescriptor.type;
        if (typeRoot == null) {
            typeRoot = TABLE_TYPE;
        }
        ContainerTag txtHtml = new ContainerTag(typeRoot);
        txtHtml.withClass("table table-bordred table-striped");

        if (htmlDescriptor.elementsList != null) {
            ContainerTag thead = TagCreator.thead();
            ContainerTag tr = TagCreator.tr();

            for (HtmlElement he : htmlDescriptor.elementsList) {
                if (!StringUtils.isEmpty(he.label)) {
                    ContainerTag th = TagCreator.th(he.label);
                    tr.with(th);
                }
            }

            ContainerTag th = TagCreator.th("Edit");
            tr.with(th);
            th = TagCreator.th("Delete");
            tr.with(th);

            thead.with(tr);
            txtHtml.with(thead);

            String var = htmlDescriptor.property + "Item";
            String collection = "model.entities";

            ContainerTag tbody = TagCreator.tbody();
            ContainerTag trBody = TagCreator.tr();

            trBody.attr("ng-repeat", buildNgRepeatPath(var, collection));

            for (HtmlElement he : htmlDescriptor.elementsList) {
                ContainerTag td = TagCreator.td();
                if (he.attributes != null) {
                    he.attributes.entrySet().stream().forEach((at) -> {
                        td.setAttribute(at.getKey(), at.getValue());
                    });
                }
                td.withText(buildNgModelPath(true, var, he.property));
                trBody.with(td);
            }

            ContainerTag td = TagCreator.td();
            td.with(p().attr("data-placement", "top").attr("data-toggle", "tooltip").attr("title", "Edit").with(
                    button().withClass("btn btn-primary btn-xs").attr("data-title", "Edit")
                    .attr("data-toggle", "modal").attr("data-target", "#edit").with(
                    span().withClass("glyphicon glyphicon-pencil"))));
            trBody.with(td);

            td = TagCreator.td();
            td.with(p().attr("data-placement", "top").attr("data-toggle", "tooltip").attr("title", "Delete").with(
                    button().withClass("btn btn-danger btn-xs").attr("data-title", "Delete")
                    .attr("data-toggle", "modal").attr("data-target", "#delete").with(
                    span().withClass("glyphicon glyphicon-trash"))));
            trBody.with(td);

            tbody.with(trBody);
            txtHtml.with(tbody);
        }
        return txtHtml.render();
        //return ret;
    }

    private Tag entityHtmlFormElement(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
//        statisticalConsolidation.countEntity(he.classe);
        if (statisticalConsolidation.countEntity(he.classe) <= THRESHOLD_ENTITY_COUNT_SELECT) {
            return unsafeHtmlFileReplace(FILE_INPUT_SELECT, he, statisticalConsolidation);
        } else {
            return unsafeHtmlFileReplace(FILE_INPUT_AUTOCOMPLETE, he, statisticalConsolidation);
        }
    }

    private Tag collectionHtmlFormElement(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        return unsafeHtmlFileReplace(FILE_INPUT_SELECT, he, statisticalConsolidation);
    }

    private Tag buildSimpleInput(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        EmptyTag input = TagCreator.input();
        setSubtypeInput(input, he.type);
        if (he.attributes != null) {
            he.attributes.entrySet().stream().forEach((at) -> {
                input.setAttribute(at.getKey(), at.getValue());
            });
        }
        input.setAttribute("class", "form-control");
        input.setAttribute(NG_MODEL, buildNgModelPath("model.entity", he.property));
        return input;
    }

    private void setSubtypeInput(EmptyTag input, String type) {
        switch (type) {
            case "boolean":
                input.setAttribute("type", CHECK_TYPE);
            case "date":
                input.setAttribute("type", DATE_TYPE);
            default:
                break;
        }
    }

    private Tag buildGenericInput(HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        Tag gnerictag = TagCreator.emptyTag(he.type.toLowerCase());
        if (he.attributes != null) {
            he.attributes.entrySet().stream().forEach((at) -> {
                gnerictag.setAttribute(at.getKey(), at.getValue());
            });
        }
        gnerictag.setAttribute("class", "form-control");
        gnerictag.setAttribute(NG_MODEL, buildNgModelPath("model.entity", he.property));
        return gnerictag;
    }

    private Tag unsafeHtmlFileReplace(String filereplace, HtmlElement he, StatisticalConsolidation statisticalConsolidation) {
        Map<String, String> propMap = new HashMap<>();
        propMap.put("property", he.property);
        propMap.put("pattern", he.pattern);
        propMap.put("classe", he.type);
        propMap.put("mdSize", he.mdSize);
        propMap.put("sort", he.sort);
        if (he.attributes != null) {
            propMap.putAll(he.attributes);
        }
        String templateStringFromFile = getTemplateStringFromFile(filereplace);
        for (Map.Entry<String, String> e : propMap.entrySet()) {
            templateStringFromFile = templateStringFromFile.replaceAll(e.getKey(), e.getValue());
        }
        return TagCreator.unsafeHtml(templateStringFromFile);
    }

    private static String getTemplateStringFromFile(String filereplace) {
        String ret = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(filereplace);
            ret = FileCopyUtils.copyToString(new InputStreamReader(classPathResource.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(AngularJSWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
