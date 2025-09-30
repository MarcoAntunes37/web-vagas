package com.webvagas.api.admin_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class UserIdentity {
    private String username;
    private String email;

    public UserIdentity(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @JsonCreator
    public static UserIdentity fromString(String username, String email) {
        return new UserIdentity(username, email);
    }

    @JsonValue
    public String toJson(String username, String email) {
        return "{\"username\":\"" + username + "\",\"email\":\"" + email + "\"}";
    }

    public UserIdentity getValue() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserIdentity))
            return false;

        UserIdentity userIdentity = (UserIdentity) o;

        return username.equals(userIdentity.username)
                && email.equals(userIdentity.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserIdentity{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
