package de.kisters.stablemanagement.stable.model.entity;

public enum BuildingType {
    HALL(5), ROUND_PEN(1), OUTDOOR_ARENA(8);

    private int capacity;

    BuildingType(int capacity) {
        this.capacity = capacity;
    }
}
