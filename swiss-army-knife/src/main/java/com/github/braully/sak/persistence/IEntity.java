/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.persistence;

import java.io.Serializable;

/**
 *
 * @author strike
 */
public interface IEntity extends Serializable {

    public Long getId();

    public void setId(Long id);

    default public boolean isPersisted() {
        return getId() != null && getId() > 0;
    }
}
