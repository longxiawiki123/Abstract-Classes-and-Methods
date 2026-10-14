package Shapes.ThreeDiM.NonPolyhedra;

import Shapes.ThreeDiM.Enums.ThreeDimCategory;
import Shapes.ThreeDiM.ThreeDimShape;

public class Cone extends ThreeDimShape {
    private final double radius;
    private final double height;

    public Cone(double radius, double height) {
        this.radius = requirePositiveFinite(radius, "radius");
        this.height = requirePositiveFinite(height, "height");
    }

    @Override
    public double surfaceArea() {
        double slantHeight = Math.hypot(radius, height);
        // Add the circular base to the lateral surface area.
        return Math.PI * radius * (radius + slantHeight);
    }

    @Override
    public double volume() {
        return Math.PI * radius * radius * height / 3.0;
    }

    @Override
    public ThreeDimCategory getCategory() {
        return ThreeDimCategory.NON_POLYHEDRON;
    }
}
