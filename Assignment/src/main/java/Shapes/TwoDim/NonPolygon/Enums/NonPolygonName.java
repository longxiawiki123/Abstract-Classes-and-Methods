package Shapes.TwoDim.NonPolygon.Enums;

public enum NonPolygonName {
    ELLIPSE("Ellipse", "A closed curve specified by two perpendicular semi-axes."),
    CIRCLE("Circle", "An ellipse with equal semi-axes.");

    private final String name;
    private final String description;

    NonPolygonName(String name, String description) {
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
