package Shapes.TwoDim.Polygon.Enums;

public enum PolygonName {
    RECTANGLE("Rectangle", "A polygon with four right angles."),
    SQUARE("Square", "A rectangle with four equal sides."),
    REGULAR_HEXAGON("Regular hexagon", "A polygon with six equal sides and six equal angles.");

    private final String name;
    private final String description;

    PolygonName(String name, String description) {
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
