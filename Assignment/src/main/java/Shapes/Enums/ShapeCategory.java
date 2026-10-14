package Shapes.Enums;

public enum ShapeCategory {
    TWO_DIMENSIONAL("Two-dimensional", "A flat shape with perimeter and area."),
    THREE_DIMENSIONAL("Three-dimensional", "A solid shape with surface area and volume.");

    private final String name;
    private final String description;

    ShapeCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
