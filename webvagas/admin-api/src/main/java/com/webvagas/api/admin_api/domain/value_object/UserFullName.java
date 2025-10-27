package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class UserFullName {
    private String firstName;
    private String lastName;

    public UserFullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "First name cannot be null.");

        Objects.requireNonNull(lastName, "Last name cannot be null.");

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserFullName))
            return false;

        UserFullName that = (UserFullName) o;

        return Objects.equals(that.firstName, this.firstName)
                && Objects.equals(that.lastName, this.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}