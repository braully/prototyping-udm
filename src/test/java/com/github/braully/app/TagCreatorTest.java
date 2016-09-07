/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.app;

import j2html.TagCreator;
import org.junit.Test;

/**
 *
 * @author strike
 */
public class TagCreatorTest {

    @Test
    public void unsafeHtmlTest() {
        System.out.println(TagCreator.unsafeHtml("<div a='b'></div>").render());
    }
}
