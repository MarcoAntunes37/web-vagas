package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class UserIdentity {
    private String username;
    private String email;

    public UserIdentity(String username, String email) {
        Objects.requireNonNull(username, "Username cannot be null.");

        Objects.requireNonNull(email, "Email cannot be null.");

        if (username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty.");

        if (email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty.");

        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

        UserIdentity that = (UserIdentity) o;

        return Objects.equals(that.username, this.username)
                && Objects.equals(that.email, this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

}
