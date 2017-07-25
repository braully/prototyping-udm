/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Braully Rocha
 */
public class UtilPath {

    /**
     * Absolute path from relative.
     * @param classLoader
     * @param relative
     * @return
     */
    public static synchronized String getPath(ClassLoader classLoader, String relative) {
        return classLoader.getResource(relative).getPath();
    }

    /**
     * Absolute path from relative.
     * @param relative
     * @return
     */
    public static synchronized String getPath(String relative) {
        return getPath(Thread.currentThread().getContextClassLoader(), relative);
    }

    /**
     * Load Properties from file name, in relative path.
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        return props;
    }
}
