/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.persistence;

import java.security.Principal;

/**
 *
 * @author Braully Rocha
 */
public interface IUser extends Principal {

    public Long getId();
}
