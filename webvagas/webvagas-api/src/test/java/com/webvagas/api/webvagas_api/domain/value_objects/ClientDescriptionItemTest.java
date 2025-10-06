package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.ClientDescriptionItem;

import static org.assertj.core.api.Assertions.*;

class ClientDescriptionItemTest {
    @Test
    void shouldNotCreateClientDecriptionItemFromNullText() {
        assertThatThrownBy(() -> new ClientDescriptionItem(null, "icon"))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Text cannot be null");
    }

    @Test
    void shouldCreateClientDescriptionItem() {
        ClientDescriptionItem clientDescriptionItem = new ClientDescriptionItem("text", "icon");

        assertThat(clientDescriptionItem.getText()).isEqualTo("text");
        assertThat(clientDescriptionItem.getIcon()).isEqualTo("icon");
    }

    @Test
    void shouldBeEqualWhenAttributesAreTheSame() {
        ClientDescriptionItem item1 = new ClientDescriptionItem("text", "icon");
        ClientDescriptionItem item2 = new ClientDescriptionItem("text", "icon");

        assertThat(item1).isEqualTo(item2);
        assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenAttributesAreDifferent() {
        ClientDescriptionItem item1 = new ClientDescriptionItem("text", "icon");
        ClientDescriptionItem item2 = new ClientDescriptionItem("other", "icon");

        assertThat(item1.equals(item2)).isFalse();
    }
}