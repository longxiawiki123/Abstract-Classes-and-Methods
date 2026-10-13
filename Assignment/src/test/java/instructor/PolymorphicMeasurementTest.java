package instructor;

import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.ThreeDiM.Enums.ThreeDimCategory;

import Shapes.Shape;
import Shapes.Enums.ShapeCategory;
import Shapes.TwoDim.*;
import Shapes.ThreeDiM.*;
import Shapes.TwoDim.Polygon.*;
import Shapes.TwoDim.NonPolygon.*;
import Shapes.ThreeDiM.Polyhedra.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
class PolymorphicMeasurementTest {
    private static final double ABSOLUTE_EPSILON = 1e-12;
    private static final double RELATIVE_EPSILON = 1e-12;

    static Stream<ShapeCases.Example> examples() { return ShapeCases.examples(); }
    static Stream<ShapeCases.Example> measurements() { return ShapeCases.measurements(); }

    private static void close(double expected, double actual) {
        assertTrue(Double.isFinite(actual), "Measurements must be finite.");
        // Accept ordinary double rounding differences without accepting rounded formulas.
        double tolerance = Math.max(ABSOLUTE_EPSILON, Math.abs(expected) * RELATIVE_EPSILON);
        assertEquals(expected, actual, tolerance);
    }

    @Test
    void comparisonAllowsSmallFloatingPointDifferencesAtDifferentScales() {
        for (double expected : new double[]{1e-6, 1, Math.PI, 1e6}) {
            close(expected, Math.nextUp(expected));
            close(expected, Math.nextDown(expected));
        }
        close(1e-6, 1e-6 + 5e-13);
        close(1, 1 + 5e-13);
        close(1e6, 1e6 + 5e-7);
    }

    @Test
    void comparisonRejectsMeaningfulErrorsAndNonfiniteResults() {
        assertThrows(AssertionError.class, () -> close(1e-6, 1e-6 + 1e-10));
        assertThrows(AssertionError.class, () -> close(1, 1 + 1e-10));
        assertThrows(AssertionError.class, () -> close(1e6, 1e6 + 1e-4));
        assertThrows(AssertionError.class, () -> close(Math.PI, 3.14));
        for (double invalid : new double[]{Double.NaN, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY}) {
            assertThrows(AssertionError.class, () -> close(1, invalid));
        }
    }

    @ParameterizedTest
    @MethodSource("measurements")
    void measurementsDispatchThroughAbstractReferences(ShapeCases.Example example) {
        Shape shape = example.shape();
        if (shape instanceof TwoDimShape flat) {
            close(example.boundary(), flat.perimeter());
            close(example.content(), flat.area());
            assertEquals(2, shape.getDimension());
            assertEquals(ShapeCategory.TWO_DIMENSIONAL, shape.getShapeCategory());
            assertEquals(shape.getClass().getPackageName().endsWith(".Polygon")
                    ? TwoDimCategory.POLYGON : TwoDimCategory.NON_POLYGON, flat.getCategory());
        } else {
            ThreeDimShape solid = assertInstanceOf(ThreeDimShape.class, shape);
            close(example.boundary(), solid.surfaceArea());
            close(example.content(), solid.volume());
            assertEquals(3, shape.getDimension());
            assertEquals(ShapeCategory.THREE_DIMENSIONAL, shape.getShapeCategory());
            assertEquals(shape.getClass().getPackageName().endsWith(".Polyhedra")
                    ? ThreeDimCategory.POLYHEDRON : ThreeDimCategory.NON_POLYHEDRON, solid.getCategory());
        }
    }

    @ParameterizedTest
    @MethodSource("examples")
    void measurementsDoNotChangeWithCallOrderOrOtherInstances(ShapeCases.Example example) {
        Shape original = example.shape();
        for (double scale : new double[]{0.3, 2.75, 0.5}) {
            if (original instanceof TwoDimShape flat) {
                close(example.content(), flat.area());
                close(example.boundary(), flat.perimeter());
                TwoDimShape other = (TwoDimShape) example.scaled().apply(scale);
                close(example.content()*scale*scale, other.area());
                close(example.boundary()*scale, other.perimeter());
                close(example.boundary(), flat.perimeter());
                close(example.content(), flat.area());
            } else {
                ThreeDimShape solid = (ThreeDimShape) original;
                close(example.content(), solid.volume());
                close(example.boundary(), solid.surfaceArea());
                ThreeDimShape other = (ThreeDimShape) example.scaled().apply(scale);
                close(example.content()*scale*scale*scale, other.volume());
                close(example.boundary()*scale*scale, other.surfaceArea());
                close(example.boundary(), solid.surfaceArea());
                close(example.content(), solid.volume());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("examples")
    void measurementsObeyDimensionalScalingAndAreStable(ShapeCases.Example example) {
        Shape original = example.shape();
        for (double scale : new double[]{0.125, 0.7, 2, 11}) {
            Shape shape = example.scaled().apply(scale);
            if (shape instanceof TwoDimShape flat) {
                close(example.boundary()*scale, flat.perimeter());
                close(example.content()*scale*scale, flat.area());
            } else {
                ThreeDimShape solid = (ThreeDimShape) shape;
                close(example.boundary()*scale*scale, solid.surfaceArea());
                close(example.content()*scale*scale*scale, solid.volume());
            }
        }
        if (original instanceof TwoDimShape flat) {
            close(example.boundary(), flat.perimeter());
            close(example.content(), flat.area());
        } else {
            ThreeDimShape solid = (ThreeDimShape) original;
            close(example.boundary(), solid.surfaceArea());
            close(example.content(), solid.volume());
        }
    }

    @Test
    void specializationsAgreeWithParentsAtEqualDimensions() {
        for (double value : new double[]{0.25, 1, 3.75, 20}) {
            TwoDimShape square = new Square(value);
            TwoDimShape rectangle = new Rectangle(value, value);
            close(rectangle.perimeter(), square.perimeter());
            close(rectangle.area(), square.area());
            TwoDimShape circle = new Circle(value);
            TwoDimShape ellipse = new Ellipse(value, value);
            close(ellipse.perimeter(), circle.perimeter());
            close(ellipse.area(), circle.area());
            ThreeDimShape cube = new Cube(value);
            ThreeDimShape prism = new RectangularPrism(value, value, value);
            close(prism.surfaceArea(), cube.surfaceArea());
            close(prism.volume(), cube.volume());
        }
    }

    @Test
    void ellipseUsesTheSpecifiedApproximationForDifferentEccentricities() {
        TwoDimShape elongated = new Ellipse(10, 1);
        close(40.63927210018871, elongated.perimeter());
        close(10*Math.PI, elongated.area());
        TwoDimShape nearlyCircular = new Ellipse(4, 3.9);
        close(24.819576148206227, nearlyCircular.perimeter());
    }

    @Test
    void independentDimensionsAreNotInterchangedOrIgnored() {
        TwoDimShape rectangle = new Rectangle(0.5, 9);
        close(19, rectangle.perimeter());
        close(4.5, rectangle.area());
        ThreeDimShape prism = new RectangularPrism(0.5, 2, 9);
        close(47, prism.surfaceArea());
        close(9, prism.volume());
        ThreeDimShape cylinder = new Shapes.ThreeDiM.NonPolyhedra.Cylinder(0.5, 9);
        close(9.5*Math.PI, cylinder.surfaceArea());
        close(2.25*Math.PI, cylinder.volume());
        ThreeDimShape cone = new Shapes.ThreeDiM.NonPolyhedra.Cone(4, 3);
        close(36*Math.PI, cone.surfaceArea());
        close(16*Math.PI, cone.volume());
    }
}
