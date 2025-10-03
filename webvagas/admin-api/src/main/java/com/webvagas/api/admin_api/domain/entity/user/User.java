package com.webvagas.api.admin_api.domain.entity.user;

import com.webvagas.api.admin_api.domain.value_object.UserAttributes;
import com.webvagas.api.admin_api.domain.value_object.UserFullName;
import com.webvagas.api.admin_api.domain.value_object.UserId;
import com.webvagas.api.admin_api.domain.value_object.UserIdentity;

public class User {
    private UserId id;
    private UserFullName fullName;
    private UserIdentity identity;
    private UserAttributes attributes;

    public User(UserId id, UserFullName fullName, UserIdentity identity, UserAttributes attributes) {
        this.id = id;
        this.fullName = fullName;
        this.identity = identity;
        this.attributes = attributes;
    }

    public UserId getUserId() {
        return id;
    }

    public UserFullName getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return fullName.getFirstName();
    }

    public String getLastName() {
        return fullName.getLastName();
    }

    public UserIdentity getIdentity() {
        return identity;
    }

    public UserAttributes getAttributes() {
        return attributes;
    }
}
