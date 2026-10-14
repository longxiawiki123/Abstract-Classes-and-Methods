package Shapes.TwoDim;

import Shapes.Enums.ShapeCategory;
import Shapes.Shape;
import Shapes.TwoDim.Enums.TwoDimCategory;

public abstract class TwoDimShape extends Shape {
    public abstract double perimeter();

    public abstract double area();

    public abstract TwoDimCategory getCategory();

    @Override
    public final int getDimension() {
        return 2;
    }

    @Override
    public final ShapeCategory getShapeCategory() {
        return ShapeCategory.TWO_DIMENSIONAL;
    }
}
