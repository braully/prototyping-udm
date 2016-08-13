package com.github.braully.web;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import j2html.tags.EmptyTag;
import j2html.tags.Tag;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 *
 * @author braully
 */
public class GeneratorHtmlAngularBootstrap {

    private static final String INPUT_TYPE = "input";
    private static final String CHECK_TYPE = "input";
    private static final String NG_MODEL = "ng-model";
    private static final String LABEL_CLASS = "col-sm-1";
    private static final String ROW_CLASS = "form-group";

    public String render(DescriptorHtmlEntity html) {
        return renderLocal(html, false);
    }

    public String renderOnlyChilds(DescriptorHtmlEntity html) {
        return renderLocal(html, true);
    }

    private String renderLocal(DescriptorHtmlEntity html, boolean onlychilds) {
        ContainerTag txtHtml = new ContainerTag(getHtmlType(html.type));

        if (html.elements != null) {
            for (HtmlElement he : html.elements) {
                ContainerTag parent = txtHtml;

                if (!StringUtils.isEmpty(he.label)) {
                    parent = TagCreator.div();
                    parent.withClass(ROW_CLASS);
                    txtHtml.with(parent);
                    ContainerTag labelHtml = TagCreator.label(he.label);
//                    labelHtml.withClass(LABEL_CLASS);
                    parent.with(labelHtml);
                }

                EmptyTag the = TagCreator.emptyTag(getHtmlType(he.type));
                if (he.attributes != null) {
                    he.attributes.entrySet().stream().forEach((at) -> {
                        the.setAttribute(at.getKey(), at.getValue());
                    });
                }
                the.withClass("form-control");
                the.attr(NG_MODEL, buildNgModelPath(html.property, he.property));

                parent.with(the);
            }
        }
        if (onlychilds) {
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

    private String getHtmlType(String type) {
        String ret = null;
        if (type != null) {
            switch (type.toLowerCase()) {
                case "string":
                    ret = INPUT_TYPE;
                    break;
                case "boolean":
                    ret = CHECK_TYPE;
                    break;
                case "int":
                case "integer":
                case "long":
                case "float":
                case "double":
                    ret = INPUT_TYPE;
                    break;
                default:
                    ret = type;
                    break;
            }
        }
        return ret;
    }

    private String buildNgModelPath(String... property) {
        StringBuilder sb = new StringBuilder();
        sb.append("{{ ");
        if (property != null && property.length > 0) {
            for (int i = 0; i < property.length; i++) {
                String p = property[i];
                sb.append(p);
                if (i == property.length - 2) {
                    sb.append(".");
                }
            }
        }
        sb.append(" }}");
        return sb.toString();
    }
}
