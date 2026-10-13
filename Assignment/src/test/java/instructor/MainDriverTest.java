package instructor;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
@ResourceLock("SYSTEM_OUT")
@ResourceLock("java.util.Locale")
class MainDriverTest {
    private static final List<String> EXPECTED = List.of(
            "Rectangle: dimension=2, category=Polygon, perimeter=14.0000, area=12.0000",
            "Square: dimension=2, category=Polygon, perimeter=12.0000, area=9.0000",
            "RegularHexagon: dimension=2, category=Polygon, perimeter=12.0000, area=10.3923",
            "Ellipse: dimension=2, category=Non-polygon, perimeter=25.5270, area=47.1239",
            "Circle: dimension=2, category=Non-polygon, perimeter=12.5664, area=12.5664",
            "RectangularPrism: dimension=3, category=Polyhedron, surfaceArea=52.0000, volume=24.0000",
            "Cube: dimension=3, category=Polyhedron, surfaceArea=54.0000, volume=27.0000",
            "Sphere: dimension=3, category=Non-polyhedron, surfaceArea=50.2655, volume=33.5103",
            "Cylinder: dimension=3, category=Non-polyhedron, surfaceArea=87.9646, volume=62.8319",
            "Cone: dimension=3, category=Non-polyhedron, surfaceArea=75.3982, volume=37.6991");

    private static List<String> output() throws Exception {
        Class<?> driver = Class.forName("Main");
        assertEquals("", driver.getPackageName());
        assertTrue(Modifier.isPublic(driver.getModifiers()));
        var main = driver.getMethod("main", String[].class);
        assertTrue(Modifier.isStatic(main.getModifiers()));
        assertEquals(void.class, main.getReturnType());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream capture = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            main.invoke(null, (Object) new String[0]);
        } finally {
            System.setOut(original);
        }
        // Compare lines so both Windows and Unix line endings are accepted.
        return bytes.toString(StandardCharsets.UTF_8).lines().toList();
    }

    @Test
    void defaultPackageDriverPrintsAllTenShapesAndTheirMeasurements() throws Exception {
        assertEquals(EXPECTED, output());
    }

    @Test
    void driverUsesConsistentNumericFormattingAcrossLocalesAndRepeatedRuns() throws Exception {
        Locale original = Locale.getDefault(Locale.Category.FORMAT);
        try {
            Locale.setDefault(Locale.Category.FORMAT, Locale.GERMANY);
            assertEquals(EXPECTED, output());
            assertEquals(EXPECTED, output());
        } finally {
            Locale.setDefault(Locale.Category.FORMAT, original);
        }
    }
}
