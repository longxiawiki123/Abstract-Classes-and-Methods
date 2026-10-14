package Shapes.ThreeDiM.Polyhedra;

import Shapes.ThreeDiM.Enums.ThreeDimCategory;
import Shapes.ThreeDiM.ThreeDimShape;

public class RectangularPrism extends ThreeDimShape {
    private final double length;
    private final double width;
    private final double height;

    public RectangularPrism(double length, double width, double height) {
        this.length = requirePositiveFinite(length, "length");
        this.width = requirePositiveFinite(width, "width");
        this.height = requirePositiveFinite(height, "height");
    }

    @Override
    public double surfaceArea() {
        return 2.0 * (length * width + length * height + width * height);
    }

    @Override
    public double volume() {
        return length * width * height;
    }

    @Override
    public ThreeDimCategory getCategory() {
        return ThreeDimCategory.POLYHEDRON;
    }
}
