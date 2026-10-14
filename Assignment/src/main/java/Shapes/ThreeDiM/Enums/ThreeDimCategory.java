package Shapes.ThreeDiM.Enums;

public enum ThreeDimCategory {
    POLYHEDRON("Polyhedron", "A closed solid bounded by flat polygonal faces."),
    NON_POLYHEDRON("Non-polyhedron", "A closed solid with at least one curved surface.");

    private final String name;
    private final String description;

    ThreeDimCategory(String name, String description) {
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
