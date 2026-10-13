import Shapes.TwoDim.TwoDimShape;
import Shapes.TwoDim.Polygon.Rectangle;
import Shapes.TwoDim.Polygon.Square;
import Shapes.TwoDim.Polygon.RegularHexagon;
import Shapes.TwoDim.NonPolygon.Ellipse;
import Shapes.TwoDim.NonPolygon.Circle;
import Shapes.ThreeDiM.ThreeDimShape;
import Shapes.ThreeDiM.Polyhedra.RectangularPrism;
import Shapes.ThreeDiM.Polyhedra.Cube;
import Shapes.ThreeDiM.NonPolyhedra.Sphere;
import Shapes.ThreeDiM.NonPolyhedra.Cylinder;
import Shapes.ThreeDiM.NonPolyhedra.Cone;

/** Demonstrates dynamic dispatch through the abstract measurement contracts. */
public class Main {
    public static void main(String[] args) {
        TwoDimShape[] flatShapes = {new Rectangle(3, 4), new Square(3),
                new RegularHexagon(2), new Ellipse(5, 3), new Circle(2)};
        for (TwoDimShape shape : flatShapes) {
            System.out.printf(java.util.Locale.ROOT,
                    "%s: dimension=%d, category=%s, perimeter=%.4f, area=%.4f%n",
                    shape.getClass().getSimpleName(), shape.getDimension(),
                    shape.getCategory(), shape.perimeter(), shape.area());
        }

        ThreeDimShape[] solidShapes = {new RectangularPrism(2, 3, 4), new Cube(3),
                new Sphere(2), new Cylinder(2, 5), new Cone(3, 4)};
        for (ThreeDimShape shape : solidShapes) {
            System.out.printf(java.util.Locale.ROOT,
                    "%s: dimension=%d, category=%s, surfaceArea=%.4f, volume=%.4f%n",
                    shape.getClass().getSimpleName(), shape.getDimension(),
                    shape.getCategory(), shape.surfaceArea(), shape.volume());
        }
    }
}
