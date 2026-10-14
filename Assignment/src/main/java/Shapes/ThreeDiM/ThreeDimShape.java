package Shapes.ThreeDiM;

import Shapes.Enums.ShapeCategory;
import Shapes.Shape;
import Shapes.ThreeDiM.Enums.ThreeDimCategory;

public abstract class ThreeDimShape extends Shape {
    public abstract double surfaceArea();

    public abstract double volume();

    public abstract ThreeDimCategory getCategory();

    @Override
    public final int getDimension() {
        return 3;
    }

    @Override
    public final ShapeCategory getShapeCategory() {
        return ShapeCategory.THREE_DIMENSIONAL;
    }
}
