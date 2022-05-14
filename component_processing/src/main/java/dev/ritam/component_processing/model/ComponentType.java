package dev.ritam.component_processing.model;

public enum ComponentType {
    INTEGRAL_ITEM("integral-item"),
    ACCESSORY("accessory");

    private final String component;

    ComponentType(String component) {
        this.component = component;
    }

    public String getComponent() {
        return component;
    }
}
