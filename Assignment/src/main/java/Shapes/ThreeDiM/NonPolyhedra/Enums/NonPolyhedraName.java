package Shapes.ThreeDiM.NonPolyhedra.Enums;

public enum NonPolyhedraName {
    SPHERE("Sphere", "A solid whose boundary is equally distant from its center."),
    CYLINDER("Cylinder", "A closed right circular solid with two parallel circular bases."),
    CONE("Cone", "A closed right circular solid with one circular base and one apex.");

    private final String name;
    private final String description;

    NonPolyhedraName(String name, String description) {
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
