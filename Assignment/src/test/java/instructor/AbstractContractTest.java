package instructor;

import Shapes.Enums.ShapeCategory;
import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.ThreeDiM.Enums.ThreeDimCategory;

import Shapes.*;
import Shapes.TwoDim.*;
import Shapes.ThreeDiM.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
class AbstractContractTest {
    private static void abstractMethod(Class<?> owner, String name, Class<?> result) throws Exception {
        var method = owner.getDeclaredMethod(name);
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isAbstract(method.getModifiers()));
        assertFalse(Modifier.isStatic(method.getModifiers()));
        assertEquals(result, method.getReturnType());
    }

    @Test
    void basesDeclarePublicAbstractMeasurementContracts() throws Exception {
        assertEquals("Shapes", Shape.class.getPackageName());
        assertEquals("Shapes.TwoDim", TwoDimShape.class.getPackageName());
        assertEquals("Shapes.ThreeDiM", ThreeDimShape.class.getPackageName());
        for (Class<?> type : new Class<?>[]{Shape.class, TwoDimShape.class, ThreeDimShape.class}) {
            assertTrue(Modifier.isPublic(type.getModifiers()));
            assertTrue(Modifier.isAbstract(type.getModifiers()));
        }
        assertEquals(Object.class, Shape.class.getSuperclass());
        assertEquals(Shape.class, TwoDimShape.class.getSuperclass());
        assertEquals(Shape.class, ThreeDimShape.class.getSuperclass());
        abstractMethod(Shape.class, "getDimension", int.class);
        abstractMethod(Shape.class, "getShapeCategory", ShapeCategory.class);
        abstractMethod(TwoDimShape.class, "perimeter", double.class);
        abstractMethod(TwoDimShape.class, "area", double.class);
        abstractMethod(TwoDimShape.class, "getCategory", TwoDimCategory.class);
        abstractMethod(ThreeDimShape.class, "surfaceArea", double.class);
        abstractMethod(ThreeDimShape.class, "volume", double.class);
        abstractMethod(ThreeDimShape.class, "getCategory", ThreeDimCategory.class);
    }

    @Test
    void dimensionAndRootCategoryBelongToTheAbstractBases() throws Exception {
        TwoDimShape flat = new TwoDimShape() {
            public double perimeter() { return 7; }
            public double area() { return 11; }
            public TwoDimCategory getCategory() { return TwoDimCategory.POLYGON; }
        };
        ThreeDimShape solid = new ThreeDimShape() {
            public double surfaceArea() { return 13; }
            public double volume() { return 17; }
            public ThreeDimCategory getCategory() { return ThreeDimCategory.POLYHEDRON; }
        };
        Shape[] shapes = {flat, solid};
        assertEquals(2, shapes[0].getDimension());
        assertEquals(3, shapes[1].getDimension());
        assertEquals(ShapeCategory.TWO_DIMENSIONAL, shapes[0].getShapeCategory());
        assertEquals(ShapeCategory.THREE_DIMENSIONAL, shapes[1].getShapeCategory());
        assertEquals(7, flat.perimeter());
        assertEquals(11, flat.area());
        assertEquals(13, solid.surfaceArea());
        assertEquals(17, solid.volume());
        for (Class<?> base : new Class<?>[]{TwoDimShape.class, ThreeDimShape.class}) {
            for (String name : new String[]{"getDimension", "getShapeCategory"}) {
                var method = base.getDeclaredMethod(name);
                assertTrue(Modifier.isPublic(method.getModifiers()));
                assertTrue(Modifier.isFinal(method.getModifiers()));
                assertFalse(Modifier.isStatic(method.getModifiers()));
                assertEquals(name.equals("getDimension") ? int.class : ShapeCategory.class,
                        method.getReturnType());
            }
        }
    }
}
