package com.flashvagas.api.admin_api.domain.value_object;

public class UserFullName {
    private String firstName;
    private String lastName;

    public UserFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserFullName))
            return false;

        UserFullName userFullName = (UserFullName) o;

        return firstName.equals(userFullName.firstName)
                && lastName.equals(userFullName.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();

        result = 31 * result + lastName.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "UserFullName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
