package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.ClientDescriptionItem;
import com.webvagas.api.webvagas_api.domain.value_object.ClientDescriptions;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

public class ClientDescriptionTest {
    @Test
    void shouldNotCreateClientDescriptionFromNullItems() {
        assertThatThrownBy(() -> new ClientDescriptions(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Items cannot be null");
    }

    @Test
    void shouldNotCreateClientDescriptionFromEmptyItems() {
        assertThatThrownBy(() -> new ClientDescriptions(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Items cannot be empty");
    }

    @Test
    void shouldCreateClientDescription() {
        ClientDescriptions clientDescriptions = new ClientDescriptions(
                List.of(new ClientDescriptionItem("text", "icon")));

        assertThat(clientDescriptions.getItems()).isNotEmpty();
    }

    @Test
    void shouldBeEqualWhenAttributesAreTheSame() {
        ClientDescriptions clientDescriptions1 = new ClientDescriptions(
                List.of(new ClientDescriptionItem("text", "icon")));
        ClientDescriptions clientDescriptions2 = new ClientDescriptions(
                List.of(new ClientDescriptionItem("text", "icon")));

        assertThat(clientDescriptions1).isEqualTo(clientDescriptions2);
        assertThat(clientDescriptions1.hashCode()).isEqualTo(clientDescriptions2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenAttributesAreDifferent() {
        ClientDescriptions clientDescriptions1 = new ClientDescriptions(
                List.of(new ClientDescriptionItem("text", "icon")));
        ClientDescriptions clientDescriptions2 = new ClientDescriptions(
                List.of(new ClientDescriptionItem("other", "icon")));

        assertThat(clientDescriptions1).isNotEqualTo(clientDescriptions2);
        assertThat(clientDescriptions1.hashCode()).isNotEqualTo(clientDescriptions2.hashCode());
    }
}
