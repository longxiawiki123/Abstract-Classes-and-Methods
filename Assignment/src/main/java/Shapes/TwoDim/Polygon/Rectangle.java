package Shapes.TwoDim.Polygon;

import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.TwoDim.TwoDimShape;

public class Rectangle extends TwoDimShape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = requirePositiveFinite(width, "width");
        this.height = requirePositiveFinite(height, "height");
    }

    @Override
    public double perimeter() {
        return 2.0 * (width + height);
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public TwoDimCategory getCategory() {
        return TwoDimCategory.POLYGON;
    }
}
