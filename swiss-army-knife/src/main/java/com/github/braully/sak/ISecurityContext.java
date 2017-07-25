package com.github.braully.sak;

import com.github.braully.sak.persistence.IUser;

public interface ISecurityContext {

    IUser getUser();

    boolean hasRole(String string);

    boolean isLoggedIn();

}
