package instructor;

import Shapes.Enums.ShapeCategory;
import Shapes.TwoDim.Enums.TwoDimCategory;
import Shapes.ThreeDiM.Enums.ThreeDimCategory;
import Shapes.TwoDim.Polygon.Enums.PolygonName;
import Shapes.TwoDim.NonPolygon.Enums.NonPolygonName;
import Shapes.ThreeDiM.Polyhedra.Enums.PolyhedraName;
import Shapes.ThreeDiM.NonPolyhedra.Enums.NonPolyhedraName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@Tag("instructor")
class EnumMetadataTest {
    record Metadata(Class<? extends Enum<?>> type, String constants, String displayNames,
                    String... descriptions) {
        @Override public String toString() { return type.getSimpleName(); }
    }

    @ParameterizedTest
    @MethodSource("metadata")
    void metadataMethodsArePublicInstanceMethodsReturningStrings(Metadata expected) throws Exception {
        assertTrue(expected.type().isEnum());
        assertTrue(Modifier.isPublic(expected.type().getModifiers()));
        assertTrue(expected.type().getPackageName().endsWith(".Enums"));
        for (String name : new String[]{"getName", "getDescription", "toString"}) {
            var method = name.equals("toString") ? expected.type().getDeclaredMethod(name)
                    : expected.type().getMethod(name);
            assertTrue(Modifier.isPublic(method.getModifiers()));
            assertFalse(Modifier.isStatic(method.getModifiers()));
            assertEquals(String.class, method.getReturnType());
        }
    }

    @ParameterizedTest
    @MethodSource("metadata")
    void eachConstantStoresItsMetadataInPrivateFinalStringFields(Metadata expected) throws Exception {
        var fields = Arrays.stream(expected.type().getDeclaredFields())
                .filter(field -> !field.isSynthetic() && !Modifier.isStatic(field.getModifiers())
                        && field.getType() == String.class).toList();
        assertTrue(fields.size() >= 2, "Store a display name and a description.");
        for (var field : fields) {
            assertTrue(Modifier.isPrivate(field.getModifiers()), field.getName());
            assertTrue(Modifier.isFinal(field.getModifiers()), field.getName());
            field.setAccessible(true);
        }
        String[] names = expected.displayNames().split(",");
        Enum<?>[] values = expected.type().getEnumConstants();
        for (int index = 0; index < values.length; index++) {
            java.util.List<Object> stored = new java.util.ArrayList<>();
            for (var field : fields) {
                stored.add(field.get(values[index]));
            }
            assertTrue(stored.contains(names[index]), values[index].name());
            assertTrue(stored.contains(expected.descriptions()[index]), values[index].name());
        }
    }

    static Stream<Metadata> metadata() {
        return Stream.of(
                new Metadata(ShapeCategory.class, "TWO_DIMENSIONAL,THREE_DIMENSIONAL", "Two-dimensional,Three-dimensional",
                        "A flat shape with perimeter and area.",
                        "A solid shape with surface area and volume."),
                new Metadata(TwoDimCategory.class, "POLYGON,NON_POLYGON", "Polygon,Non-polygon",
                        "A closed flat shape bounded by straight line segments.",
                        "A closed flat shape with a curved boundary."),
                new Metadata(ThreeDimCategory.class, "POLYHEDRON,NON_POLYHEDRON", "Polyhedron,Non-polyhedron",
                        "A closed solid bounded by flat polygonal faces.",
                        "A closed solid with at least one curved surface."),
                new Metadata(PolygonName.class, "RECTANGLE,SQUARE,REGULAR_HEXAGON", "Rectangle,Square,Regular hexagon",
                        "A polygon with four right angles.",
                        "A rectangle with four equal sides.",
                        "A polygon with six equal sides and six equal angles."),
                new Metadata(NonPolygonName.class, "ELLIPSE,CIRCLE", "Ellipse,Circle",
                        "A closed curve specified by two perpendicular semi-axes.",
                        "An ellipse with equal semi-axes."),
                new Metadata(PolyhedraName.class, "RECTANGULAR_PRISM,CUBE", "Rectangular prism,Cube",
                        "A solid with six rectangular faces.",
                        "A rectangular prism with six congruent square faces."),
                new Metadata(NonPolyhedraName.class, "SPHERE,CYLINDER,CONE", "Sphere,Cylinder,Cone",
                        "A solid whose boundary is equally distant from its center.",
                        "A closed right circular solid with two parallel circular bases.",
                        "A closed right circular solid with one circular base and one apex."));
    }

    @ParameterizedTest
    @MethodSource("metadata")
    void eachPackageEnumHasTheRequiredConstantsNamesAndDescriptions(Metadata expected) throws Exception {
        Enum<?>[] values = expected.type().getEnumConstants();
        assertArrayEquals(expected.constants().split(","), Arrays.stream(values).map(Enum::name).toArray());
        String[] names = expected.displayNames().split(",");
        for (int index = 0; index < values.length; index++) {
            String name = (String) expected.type().getMethod("getName").invoke(values[index]);
            String description = (String) expected.type().getMethod("getDescription").invoke(values[index]);
            assertEquals(names[index], name);
            assertEquals(name, values[index].toString());
            assertEquals(expected.descriptions()[index], description,
                    values[index].name() + " must use the required description exactly.");
        }
    }
}
