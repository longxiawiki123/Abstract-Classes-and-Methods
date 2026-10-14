package Shapes.TwoDim.NonPolygon;

import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.TwoDim.TwoDimShape;

public class Ellipse extends TwoDimShape {
    private final double semiMajorAxis;
    private final double semiMinorAxis;

    public Ellipse(double semiMajorAxis, double semiMinorAxis) {
        this.semiMajorAxis = requirePositiveFinite(semiMajorAxis, "semiMajorAxis");
        this.semiMinorAxis = requirePositiveFinite(semiMinorAxis, "semiMinorAxis");
        if (semiMajorAxis < semiMinorAxis) {
            throw new IllegalArgumentException("semiMajorAxis must be at least semiMinorAxis.");
        }
    }

    @Override
    public double perimeter() {
        // Ramanujan's second approximation; the inputs are semi-axis lengths.
        double ratio = (semiMajorAxis - semiMinorAxis) / (semiMajorAxis + semiMinorAxis);
        double h = ratio * ratio;
        return Math.PI * (semiMajorAxis + semiMinorAxis)
                * (1.0 + 3.0 * h / (10.0 + Math.sqrt(4.0 - 3.0 * h)));
    }

    @Override
    public double area() {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

    @Override
    public TwoDimCategory getCategory() {
        return TwoDimCategory.NON_POLYGON;
    }
}
