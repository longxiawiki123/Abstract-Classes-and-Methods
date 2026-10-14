package Shapes.TwoDim.Polygon;

import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.TwoDim.TwoDimShape;

public class RegularHexagon extends TwoDimShape {
    private final double sideLength;

    public RegularHexagon(double sideLength) {
        this.sideLength = requirePositiveFinite(sideLength, "sideLength");
    }

    @Override
    public double perimeter() {
        return 6.0 * sideLength;
    }

    @Override
    public double area() {
        return (3.0 * Math.sqrt(3.0) / 2.0) * sideLength * sideLength;
    }

    @Override
    public TwoDimCategory getCategory() {
        return TwoDimCategory.POLYGON;
    }
}
