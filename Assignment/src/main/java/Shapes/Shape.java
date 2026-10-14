package Shapes;

import Shapes.Enums.ShapeCategory;

public abstract class Shape {
    public abstract int getDimension();

    public abstract ShapeCategory getShapeCategory();

    /** Validates a dimension before a constructor stores it. */
    protected static double requirePositiveFinite(double value, String measurementName) {
        if (!Double.isFinite(value) || value <= 0) {
            throw new IllegalArgumentException(measurementName + " must be positive and finite.");
        }
        return value;
    }
}
