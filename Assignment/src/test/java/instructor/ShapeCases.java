package instructor;

import Shapes.Shape;
import Shapes.TwoDim.Polygon.*;
import Shapes.TwoDim.NonPolygon.*;
import Shapes.ThreeDiM.Polyhedra.*;
import Shapes.ThreeDiM.NonPolyhedra.*;
import java.util.function.DoubleFunction;
import java.util.stream.Stream;

/** Reference values are independent of calls to the student's measurement methods. */
final class ShapeCases {
    record Example(String name, Class<? extends Shape> type, double[] dimensions,
                   DoubleFunction<Shape> scaled, double boundary, double content) {
        Shape shape() { return scaled.apply(1); }
        @Override public String toString() { return name; }
    }

    static Stream<Example> examples() {
        return Stream.of(
                new Example("Rectangle", Rectangle.class, new double[]{3, 4},
                        k -> new Rectangle(3*k, 4*k), 14, 12),
                new Example("Square", Square.class, new double[]{2.5},
                        k -> new Square(2.5*k), 10, 6.25),
                new Example("RegularHexagon", RegularHexagon.class, new double[]{2},
                        k -> new RegularHexagon(2*k), 12, 6*Math.sqrt(3)),
                new Example("Ellipse", Ellipse.class, new double[]{5, 3},
                        k -> new Ellipse(5*k, 3*k), 25.526998862788762, 15*Math.PI),
                new Example("Circle", Circle.class, new double[]{2},
                        k -> new Circle(2*k), 4*Math.PI, 4*Math.PI),
                new Example("RectangularPrism", RectangularPrism.class, new double[]{2, 3, 4},
                        k -> new RectangularPrism(2*k, 3*k, 4*k), 52, 24),
                new Example("Cube", Cube.class, new double[]{2.5},
                        k -> new Cube(2.5*k), 37.5, 15.625),
                new Example("Sphere", Sphere.class, new double[]{2},
                        k -> new Sphere(2*k), 16*Math.PI, 32*Math.PI/3),
                new Example("Cylinder", Cylinder.class, new double[]{2, 5},
                        k -> new Cylinder(2*k, 5*k), 28*Math.PI, 20*Math.PI),
                new Example("Cone", Cone.class, new double[]{3, 4},
                        k -> new Cone(3*k, 4*k), 24*Math.PI, 12*Math.PI));
    }

    /** Independent reference values computed with 70 digit decimal arithmetic. */
    static Stream<Example> measurements() {
        return Stream.concat(examples(), Stream.of(
                new Example("Rectangle(0.3, 0.7)", Rectangle.class, new double[]{0.3, 0.7},
                        k -> new Rectangle(0.3*k, 0.7*k), 2.0, 0.21),
                new Example("Rectangle(7.25, 2.125)", Rectangle.class, new double[]{7.25, 2.125},
                        k -> new Rectangle(7.25*k, 2.125*k), 18.75, 15.40625),
                new Example("Square(0.3)", Square.class, new double[]{0.3},
                        k -> new Square(0.3*k), 1.2, 0.09),
                new Example("Square(7.25)", Square.class, new double[]{7.25},
                        k -> new Square(7.25*k), 29.0, 52.5625),
                new Example("RegularHexagon(0.3)", RegularHexagon.class, new double[]{0.3},
                        k -> new RegularHexagon(0.3*k), 1.8, 0.23382685902179842),
                new Example("RegularHexagon(7.25)", RegularHexagon.class, new double[]{7.25},
                        k -> new RegularHexagon(7.25*k), 43.5, 136.56138085925866),
                new Example("Ellipse(7.25, 2.125)", Ellipse.class, new double[]{7.25, 2.125},
                        k -> new Ellipse(7.25*k, 2.125*k), 31.697443570939424, 48.40016181936775),
                new Example("Ellipse(1, 0.05)", Ellipse.class, new double[]{1, 0.05},
                        k -> new Ellipse(1*k, 0.05*k), 4.019215027270929, 0.15707963267948966),
                new Example("Ellipse(3.125, 3.125)", Ellipse.class, new double[]{3.125, 3.125},
                        k -> new Ellipse(3.125*k, 3.125*k), 19.634954084936208, 30.679615757712824),
                new Example("Circle(0.3)", Circle.class, new double[]{0.3},
                        k -> new Circle(0.3*k), 1.8849555921538759, 0.2827433388230814),
                new Example("Circle(7.25)", Circle.class, new double[]{7.25},
                        k -> new Circle(7.25*k), 45.553093477052, 165.1299638543135),
                new Example("RectangularPrism(0.3, 0.7, 1.1)", RectangularPrism.class, new double[]{0.3, 0.7, 1.1},
                        k -> new RectangularPrism(0.3*k, 0.7*k, 1.1*k), 2.62, 0.231),
                new Example("RectangularPrism(7.25, 2.125, 4.5)", RectangularPrism.class, new double[]{7.25, 2.125, 4.5},
                        k -> new RectangularPrism(7.25*k, 2.125*k, 4.5*k), 115.1875, 69.328125),
                new Example("Cube(0.3)", Cube.class, new double[]{0.3},
                        k -> new Cube(0.3*k), 0.54, 0.027),
                new Example("Cube(7.25)", Cube.class, new double[]{7.25},
                        k -> new Cube(7.25*k), 315.375, 381.078125),
                new Example("Sphere(0.3)", Sphere.class, new double[]{0.3},
                        k -> new Sphere(0.3*k), 1.1309733552923256, 0.11309733552923255),
                new Example("Sphere(7.25)", Sphere.class, new double[]{7.25},
                        k -> new Sphere(7.25*k), 660.519855417254, 1596.2563172583639),
                new Example("Cylinder(0.3, 0.7)", Cylinder.class, new double[]{0.3, 0.7},
                        k -> new Cylinder(0.3*k, 0.7*k), 1.8849555921538759, 0.19792033717615698),
                new Example("Cylinder(7.25, 2.125)", Cylinder.class, new double[]{7.25, 2.125},
                        k -> new Cylinder(7.25*k, 2.125*k), 427.0602513473625, 350.9011731904162),
                new Example("Cone(0.3, 0.7)", Cone.class, new double[]{0.3, 0.7},
                        k -> new Cone(0.3*k, 0.7*k), 1.0005130440467447, 0.06597344572538566),
                new Example("Cone(7.25, 2.125)", Cone.class, new double[]{7.25, 2.125},
                        k -> new Cone(7.25*k, 2.125*k), 337.20692520204267, 116.96705773013873)));
    }
}
