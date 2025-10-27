package com.webvagas.api.admin_api.domain.entity.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.admin_api.domain.value_object.UserAttributes;
import com.webvagas.api.admin_api.domain.value_object.UserFullName;
import com.webvagas.api.admin_api.domain.value_object.UserId;
import com.webvagas.api.admin_api.domain.value_object.UserIdentity;

public class UserTest {
    private UserId id;
    private UserFullName fullName;
    private UserIdentity identity;
    private UserAttributes attributes;

    @BeforeEach
    void Setup() {
        id = new UserId(UUID.randomUUID());
        fullName = new UserFullName("John", "Doe");
        identity = new UserIdentity("johndoe", "johndoe@localhost");
        attributes = new UserAttributes(new String[] {"en"}, new String[] {"1234567890"});
    }

    @Test
    public void shouldCreateUserCorrectly() {
        User user = new User(id, fullName, identity, attributes);

        assertThat(user.getUserId()).isEqualTo(id);
        assertThat(user.getFullName()).isEqualTo(fullName);
        assertThat(user.getIdentity()).isEqualTo(identity);
        assertThat(user.getAttributes()).isEqualTo(attributes);
    }
}