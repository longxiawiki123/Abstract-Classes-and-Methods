package Shapes.TwoDim.Enums;

public enum TwoDimCategory {
    POLYGON("Polygon", "A closed flat shape bounded by straight line segments."),
    NON_POLYGON("Non-polygon", "A closed flat shape with a curved boundary.");

    private final String name;
    private final String description;

    TwoDimCategory(String name, String description) {
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
