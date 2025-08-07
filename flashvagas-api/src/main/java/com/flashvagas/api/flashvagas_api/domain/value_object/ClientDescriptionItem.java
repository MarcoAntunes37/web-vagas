package com.flashvagas.api.flashvagas_api.domain.value_object;

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

        ClientDescriptionItem clientDescriptionItem = (ClientDescriptionItem) o;
        return text.equals(clientDescriptionItem.text) &&
                icon.equals(clientDescriptionItem.icon);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + icon.hashCode();
        return result;
    }
}