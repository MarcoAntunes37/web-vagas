package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

public class ClientDescriptionItem {
    private String text;
    private String icon;

    public ClientDescriptionItem() {
    }

    public ClientDescriptionItem(String text, String icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ClientDescriptionItem))
            return false;

        return Objects.equals(text, this.text)
                && Objects.equals(icon, this.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, icon);
    }
}