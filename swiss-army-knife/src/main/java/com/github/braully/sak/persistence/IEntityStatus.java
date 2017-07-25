package com.github.braully.sak.persistence;

/**
 *
 * @author Braully Rocha da Silva
 */
public interface IEntityStatus extends IEntity {

    public Status getStatus();

    public void setStatus(Status status);

    public boolean isBlocked();

    public boolean isActive();

    public void setActive(boolean bStatus);

    public void block();

    public void activate();

    public void toggleBlock();
}
