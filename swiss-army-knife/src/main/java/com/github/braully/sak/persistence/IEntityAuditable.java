/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.persistence;

import com.github.braully.sak.ISecurityContext;

import java.util.Date;

/**
 *
 * @author braullyrocha
 */
public interface IEntityAuditable extends IEntity {

    public void setIdUser(Long id);

    public void setDate(Date date);

    public void auditEntity(ISecurityContext ISecurityContext);
}
