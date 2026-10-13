package instructor;

import Shapes.TwoDim.NonPolygon.Ellipse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
class ConstructorValidationTest {
    static Stream<ShapeCases.Example> examples() { return ShapeCases.examples(); }

    @ParameterizedTest
    @MethodSource("examples")
    void everyIndependentMeasurementRejectsNonpositiveAndNonfiniteValues(ShapeCases.Example example) {
        var constructor = example.type().getConstructors()[0];
        for (int index = 0; index < example.dimensions().length; index++) {
            for (double invalid : new double[]{0, -0.0, -1, -0.25, -Double.MIN_VALUE,
                    -Double.MAX_VALUE, Double.NaN,
                    Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
                Object[] arguments = java.util.Arrays.stream(example.dimensions()).boxed().toArray();
                arguments[index] = invalid;
                InvocationTargetException failure = assertThrows(InvocationTargetException.class,
                        () -> constructor.newInstance(arguments));
                assertInstanceOf(IllegalArgumentException.class, failure.getCause());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("examples")
    void constructorsAcceptAllPositiveFiniteScalesWithoutPrematureRounding(ShapeCases.Example example) {
        var constructor = example.type().getConstructors()[0];
        for (double positive : new double[]{Double.MIN_VALUE, Double.MIN_NORMAL,
                1e-9, 0.25, 1, 12, Double.MAX_VALUE}) {
            Object[] arguments = new Object[example.dimensions().length];
            java.util.Arrays.fill(arguments, positive);
            assertDoesNotThrow(() -> constructor.newInstance(arguments),
                    example.name() + " must accept positive finite dimensions: " + positive);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1e-9, 0.25, 1, 2, 10, 1e6})
    void ellipseOrderingIsStrictAndEqualAxesAreValidAtDifferentScales(double value) {
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(value, Math.nextUp(value)));
        assertDoesNotThrow(() -> new Ellipse(value, value));
        assertDoesNotThrow(() -> new Ellipse(Math.nextUp(value), value));
    }

    @Test
    void ellipseRejectsReversedAxesAndAcceptsEqualAxes() {
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(2, 3));
        assertDoesNotThrow(() -> new Ellipse(2, 2));
    }
}
