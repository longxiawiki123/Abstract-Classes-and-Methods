package Shapes.ThreeDiM.NonPolyhedra;

import Shapes.ThreeDiM.Enums.ThreeDimCategory;
import Shapes.ThreeDiM.ThreeDimShape;

public class Cylinder extends ThreeDimShape {
    private final double radius;
    private final double height;

    public Cylinder(double radius, double height) {
        this.radius = requirePositiveFinite(radius, "radius");
        this.height = requirePositiveFinite(height, "height");
    }

    @Override
    public double surfaceArea() {
        // The closed cylinder includes both circular bases.
        return 2.0 * Math.PI * radius * (radius + height);
    }

    @Override
    public double volume() {
        return Math.PI * radius * radius * height;
    }

    @Override
    public ThreeDimCategory getCategory() {
        return ThreeDimCategory.NON_POLYHEDRON;
    }
}
