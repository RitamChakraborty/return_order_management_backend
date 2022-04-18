package dev.ritam.component_processing.model;

public enum ComponentType {
    INTEGRAL_ITEM("integral-item"),
    ACCESSORY("accessory");

    private final String componentType;

    ComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentType() {
        return componentType;
    }
}
