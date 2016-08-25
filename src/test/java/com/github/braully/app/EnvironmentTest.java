/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.app;

import com.github.braully.config.SpringMainConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author braully
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringMainConfig.class})
public class EnvironmentTest {

    @Test
    public void testEnvironment() {
        System.out.println("testEnvironment()");
        System.err.println("testEnvironment()");
    }

}
