package instructor;

import Shapes.Shape;
import Shapes.TwoDim.TwoDimShape;
import Shapes.ThreeDiM.ThreeDimShape;
import Shapes.TwoDim.Polygon.*;
import Shapes.TwoDim.NonPolygon.*;
import Shapes.ThreeDiM.Polyhedra.*;
import Shapes.ThreeDiM.NonPolyhedra.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
class InheritanceTest {
    static Stream<ShapeCases.Example> examples() { return ShapeCases.examples(); }

    static Stream<Arguments> parents() {
        return Stream.of(
                Arguments.of(Rectangle.class, TwoDimShape.class, "Shapes.TwoDim.Polygon"),
                Arguments.of(Square.class, Rectangle.class, "Shapes.TwoDim.Polygon"),
                Arguments.of(RegularHexagon.class, TwoDimShape.class, "Shapes.TwoDim.Polygon"),
                Arguments.of(Ellipse.class, TwoDimShape.class, "Shapes.TwoDim.NonPolygon"),
                Arguments.of(Circle.class, Ellipse.class, "Shapes.TwoDim.NonPolygon"),
                Arguments.of(RectangularPrism.class, ThreeDimShape.class, "Shapes.ThreeDiM.Polyhedra"),
                Arguments.of(Cube.class, RectangularPrism.class, "Shapes.ThreeDiM.Polyhedra"),
                Arguments.of(Sphere.class, ThreeDimShape.class, "Shapes.ThreeDiM.NonPolyhedra"),
                Arguments.of(Cylinder.class, ThreeDimShape.class, "Shapes.ThreeDiM.NonPolyhedra"),
                Arguments.of(Cone.class, ThreeDimShape.class, "Shapes.ThreeDiM.NonPolyhedra"));
    }

    @ParameterizedTest
    @MethodSource("parents")
    void everyShapeUsesItsRequiredPackageAndDirectParent(Class<?> shape, Class<?> parent,
                                                        String packageName) {
        assertEquals(packageName, shape.getPackageName());
        assertEquals(parent, shape.getSuperclass());
    }

    @ParameterizedTest
    @MethodSource("examples")
    void eachShapeHasOnePublicMinimalConstructorAndAnAbstractBase(ShapeCases.Example example) {
        assertTrue(Modifier.isPublic(example.type().getModifiers()));
        assertFalse(Modifier.isAbstract(example.type().getModifiers()));
        var constructors = example.type().getConstructors();
        assertEquals(1, constructors.length);
        assertEquals(example.dimensions().length, constructors[0].getParameterCount());
        for (Class<?> parameter : constructors[0].getParameterTypes()) {
            assertEquals(double.class, parameter);
        }
        Shape shape = example.shape();
        assertTrue(shape instanceof TwoDimShape || shape instanceof ThreeDimShape);
        for (Class<?> type = example.type(); type != Object.class; type = type.getSuperclass()) {
            Arrays.stream(type.getDeclaredFields()).filter(field -> !field.isSynthetic()
                    && !Modifier.isStatic(field.getModifiers())).forEach(field -> {
                assertTrue(Modifier.isPrivate(field.getModifiers()), field.getName());
                assertTrue(Modifier.isFinal(field.getModifiers()), field.getName());
            });
        }
    }
}
