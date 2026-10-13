package student;

import Shapes.TwoDim.TwoDimShape;
import Shapes.TwoDim.Polygon.Square;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {
    @Test
    void aSquareCanBeUsedThroughTheAbstractContract() {
        TwoDimShape shape = new Square(3);
        assertEquals(2, shape.getDimension());
        assertEquals(12, shape.perimeter(), 12e-12);
        assertEquals(9, shape.area(), 9e-12);
    }
}
