/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.app;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author braully
 */
public class UtilTests {
    
    @Test
    public void testSplitBeanMethod() {
        String name = "Bean.method";
        String[] split = name.split("\\.");
        assertTrue(split.length == 2);
    }
    
}
