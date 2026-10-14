package Shapes.ThreeDiM.NonPolyhedra;

import Shapes.ThreeDiM.Enums.ThreeDimCategory;
import Shapes.ThreeDiM.ThreeDimShape;

public class Sphere extends ThreeDimShape {
    private final double radius;

    public Sphere(double radius) {
        this.radius = requirePositiveFinite(radius, "radius");
    }

    @Override
    public double surfaceArea() {
        return 4.0 * Math.PI * radius * radius;
    }

    @Override
    public double volume() {
        return (4.0 / 3.0) * Math.PI * radius * radius * radius;
    }

    @Override
    public ThreeDimCategory getCategory() {
        return ThreeDimCategory.NON_POLYHEDRON;
    }
}
