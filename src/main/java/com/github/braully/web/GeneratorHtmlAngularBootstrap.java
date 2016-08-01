package com.github.braully.web;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import java.util.Map;

/**
 *
 * @author braully
 */
public class GeneratorHtmlAngularBootstrap {

    private static final String INPUT_TYPE = "input";
    private static final String CHECK_TYPE = "input";

    public String render(HtmlAngularBootstrap html) {

        ContainerTag txtHtml = new ContainerTag(getHtmlType(html.type));

        if (html.elements != null) {
            for (HtmlElement he : html.elements) {
                Tag the = TagCreator.emptyTag(getHtmlType(he.type));
                if (he.attributes != null) {
                    for (Map.Entry<String, String> at : he.attributes.entrySet()) {
                        the.setAttribute(at.getKey(), at.getValue());
                    }
                }
                txtHtml.with(the);
                //Tag form = form().with(input().attr("ng-model", "{{partner.name}}"));
            }
        }
        return txtHtml.render();
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

}
