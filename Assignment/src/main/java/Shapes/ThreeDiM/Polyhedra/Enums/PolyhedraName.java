package Shapes.ThreeDiM.Polyhedra.Enums;

public enum PolyhedraName {
    RECTANGULAR_PRISM("Rectangular prism", "A solid with six rectangular faces."),
    CUBE("Cube", "A rectangular prism with six congruent square faces.");

    private final String name;
    private final String description;

    PolyhedraName(String name, String description) {
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
