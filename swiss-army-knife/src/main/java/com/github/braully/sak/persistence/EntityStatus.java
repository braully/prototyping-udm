/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.sak.persistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author braullyrocha
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class EntityStatus
        extends EntityBasic
        implements IEntityStatus {

    @Column(name = "status",
            columnDefinition = "integer default '0'")
    @Enumerated(EnumType.ORDINAL)
    protected Status status = Status.ACTIVE;

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean isActive() {
        return this.status == Status.ACTIVE;
    }

    @Override
    public boolean isBlocked() {
        return this.status == Status.BLOCKED;
    }

    @Override
    public void setActive(boolean bStatus) {
        setStatus(bStatus ? Status.ACTIVE : Status.BLOCKED);
    }

    @Override
    public void block() {
        this.status = Status.BLOCKED;
    }

    @Override
    public void activate() {
        this.status = Status.ACTIVE;
    }
}
