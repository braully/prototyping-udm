/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 *
 * @author strike
 */
public class UtilEncode {

    private static final String SEED = "segredo";

    public static String encodeSimples(String str) {
        String ret = null;
        if (str != null && !str.trim().isEmpty()) {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(SEED);
            ret = encryptor.encrypt(str);
        }
        return ret;
    }

    public static String decodeSimples(String str) {
        String ret = null;
        if (str != null && !str.trim().isEmpty()) {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(SEED);
            ret = encryptor.decrypt(str);
        }
        return ret;
    }
}
