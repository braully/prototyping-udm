package com.github.braully.sak.report;

import com.github.braully.sak.util.UtilDate;
import java.util.Date;

/**
 *
 * @author Braully Rocha
 */
public class Detail {

    private String name;
    private String prop;
    private Class type;
    private String pattern;
    private int size;

    public Detail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String format(Object object) {
        if (object != null) {
            if (type != null) {
                if (type.isAssignableFrom(String.class)) {
                    return object.toString();
                }
                if (type.isAssignableFrom(Date.class)) {
                    if (pattern != null && !pattern.isEmpty()) {
                        return UtilDate.formatData(pattern, (Date) object);
                    } else {
                        return UtilDate.formatData((Date) object);
                    }
                }
            }
            return object.toString();
        }
        return "";
    }

}
