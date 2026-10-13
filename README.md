# CSE 220 Shapes: Abstract Classes

## What this assignment evaluates

* Abstract classes and methods
* Inheritance and constructor chaining
* Polymorphism through abstract references
* Shape classifications and dimensions
* Accurate shape measurements
* Input validation and encapsulation

## Project manager statement

> As a geometry application developer, I need a Java application that classifies flat
> shapes and solid shapes, calculates their associated measurements,
> and clearly identifies each shape's dimension and category.

## Required final file layout

```text
repository root/
├── .github/
│   ├── actions/
│   │   └── instructor/
│   │       ├── action.yml
│   │       └── auto-grading-config.json
│   └── workflows/
│       └── auto-grading-workflow.yml
├── Assignment/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       ├── Shapes/
│   │   │       │   ├── ThreeDiM/
│   │   │       │   │   ├── NonPolyhedra/
│   │   │       │   │   │   ├── Enums/
│   │   │       │   │   │   │   └── NonPolyhedraName.java
│   │   │       │   │   │   ├── Cone.java
│   │   │       │   │   │   ├── Cylinder.java
│   │   │       │   │   │   └── Sphere.java
│   │   │       │   │   ├── Polyhedra/
│   │   │       │   │   │   ├── Enums/
│   │   │       │   │   │   │   └── PolyhedraName.java
│   │   │       │   │   │   ├── Cube.java
│   │   │       │   │   │   └── RectangularPrism.java
│   │   │       │   │   ├── Enums/
│   │   │       │   │   │   └── ThreeDimCategory.java
│   │   │       │   │   └── ThreeDimShape.java
│   │   │       │   ├── TwoDim/
│   │   │       │   │   ├── NonPolygon/
│   │   │       │   │   │   ├── Enums/
│   │   │       │   │   │   │   └── NonPolygonName.java
│   │   │       │   │   │   ├── Circle.java
│   │   │       │   │   │   └── Ellipse.java
│   │   │       │   │   ├── Polygon/
│   │   │       │   │   │   ├── Enums/
│   │   │       │   │   │   │   └── PolygonName.java
│   │   │       │   │   │   ├── Rectangle.java
│   │   │       │   │   │   ├── RegularHexagon.java
│   │   │       │   │   │   └── Square.java
│   │   │       │   │   ├── Enums/
│   │   │       │   │   │   └── TwoDimCategory.java
│   │   │       │   │   └── TwoDimShape.java
│   │   │       │   ├── Enums/
│   │   │       │   │   └── ShapeCategory.java
│   │   │       │   └── Shape.java
│   │   │       └── Main.java
│   │   └── test/
│   │       └── java/
│   │           ├── instructor/
│   │           │   ├── AbstractContractTest.java
│   │           │   ├── ConstructorValidationTest.java
│   │           │   ├── EnumMetadataTest.java
│   │           │   ├── InheritanceTest.java
│   │           │   ├── MainDriverTest.java
│   │           │   ├── PolymorphicMeasurementTest.java
│   │           │   └── ShapeCases.java
│   │           └── student/
│   │               └── StudentTest.java
│   └── pom.xml
├── .gitignore
└── README.md
```

* Use Java 24 and Maven. Open `Assignment/pom.xml` in IntelliJ and set Main's working directory to `Assignment`.
* Create every class and enum under `Shapes` shown in the required final layout. Preserve the listed signatures and package capitalization, including `ThreeDiM`.
* Build the enums first, then the abstract classes, then the concrete shapes.
* Declare all abstract classes `public abstract` and all concrete shape classes `public`.
* Keep `Main.java` in the default package, outside `Shapes`.
* **Do not modify `Assignment/src/test/java/instructor/`.** Add or change your own tests under `Assignment/src/test/java/student/`.

## Class components

### Abstract classes

* Implement this hierarchy and keep the listed methods public and abstract:

| Class (package) | Parent | Abstract methods |
| --- | --- | --- |
| `Shape` (`Shapes`) | `Object` | `int getDimension()`, `ShapeCategory getShapeCategory()` |
| `TwoDimShape` (`Shapes.TwoDim`) | `Shape` | `double perimeter()`, `double area()`, `TwoDimCategory getCategory()` |
| `ThreeDimShape` (`Shapes.ThreeDiM`) | `Shape` | `double surfaceArea()`, `double volume()`, `ThreeDimCategory getCategory()` |

* In `TwoDimShape`, implement public final `getDimension()` returning `2` and `getShapeCategory()` returning `ShapeCategory.TWO_DIMENSIONAL`.
* In `ThreeDimShape`, implement those public final methods returning `3` and `ShapeCategory.THREE_DIMENSIONAL`.
* Implement measurements in the shapes and call them through abstract references. Keep formulas out of `Main`.

### Flat shapes Shapes.TwoDim

* Implement these classes with exactly one public constructor, using the listed `double` parameters in order:

| Package | Class | Parent | Constructor parameters |
| --- | --- | --- | --- |
| `Polygon` | `Rectangle` | `TwoDimShape` | `width`, `height` |
| `Polygon` | `Square` | `Rectangle` | `sideLength` |
| `Polygon` | `RegularHexagon` | `TwoDimShape` | `sideLength` |
| `NonPolygon` | `Ellipse` | `TwoDimShape` | `semiMajorAxis`, `semiMinorAxis` |
| `NonPolygon` | `Circle` | `Ellipse` | `radius` |

* Have `Square` pass its side length twice to `Rectangle`, and `Circle` pass its radius twice to `Ellipse`. Reuse inherited measurements.
* Return `TwoDimCategory.POLYGON` for polygon shapes and `TwoDimCategory.NON_POLYGON` for ellipse and circle.
* Treat ellipse inputs as semi axis lengths. Require `semiMajorAxis >= semiMinorAxis`, allowing equality.
* Use Ramanujan's second approximation for ellipse perimeter:

```text
a = semiMajorAxis
b = semiMinorAxis
h = ((a - b) / (a + b))²
perimeter = PI * (a + b) * (1 + 3 * h / (10 + sqrt(4 - 3 * h)))
```

### Solid shapes Shapes.ThreeDiM

* Implement these classes with exactly one public constructor, using the listed `double` parameters in order:

| Package | Class | Parent | Constructor parameters |
| --- | --- | --- | --- |
| `Polyhedra` | `RectangularPrism` | `ThreeDimShape` | `length`, `width`, `height` |
| `Polyhedra` | `Cube` | `RectangularPrism` | `sideLength` |
| `NonPolyhedra` | `Sphere` | `ThreeDimShape` | `radius` |
| `NonPolyhedra` | `Cylinder` | `ThreeDimShape` | `radius`, `height` |
| `NonPolyhedra` | `Cone` | `ThreeDimShape` | `radius`, `height` |

* Have `Cube` pass its side length three times to `RectangularPrism` and reuse inherited measurements.
* Return `ThreeDimCategory.POLYHEDRON` for prism and cube. Return `ThreeDimCategory.NON_POLYHEDRON` for sphere, cylinder, and cone.
* Model closed right circular cylinders and cones. Include bases in surface area and use perpendicular height.
* Spell the volume method `volume()`.

### Validation and calculations

* Store independent measurements in private final instance fields. Keep calculations independent of other shapes and free of mutation.
* Reject zero, negative values, `NaN`, and either infinity with `IllegalArgumentException`. Implement validation in your constructors or a shared helper in `Shape`.
* Preserve validation through parent constructors for square, circle, and cube.
* Research the formulas for each shape's perimeter, area, surface area, or volume. Use the specified approximation for ellipse perimeter.
* Use Java's provided constant `Math.PI` from `java.lang.Math` for pi. Do not substitute a rounded value such as `3.14`.
* Use `double` arithmetic, including floating point fractions. Return unrounded measurements.

Tests allow an absolute error of `max(1e-12, 1e-12 * abs(expected))` to accommodate
small floating point differences across platforms and computation order.
Results must be finite. Tests use ordinary finite sizes and do not require extreme
input overflow handling.

### Enum contents and methods

* Create an `Enums` subpackage inside each shape package and implement the enum listed below.
* Include the category or shape constants listed below.
* Store a display name and a nonblank description for every constant in private final `String` fields.
* Initialize those fields through the enum constructor.
* Provide public `String getName()` returning the display name.
* Provide public `String getDescription()` returning the required description exactly, including capitalization and punctuation.
* Override public `String toString()` to return the display name.
* Preserve the package names, enum names, constants, display names, descriptions, and order below.

| Package | Enum | Constant | Display name | Required description |
| --- | --- | --- | --- | --- |
| `Shapes.Enums` | `ShapeCategory` | `TWO_DIMENSIONAL` | `Two-dimensional` | `A flat shape with perimeter and area.` |
| `Shapes.Enums` | `ShapeCategory` | `THREE_DIMENSIONAL` | `Three-dimensional` | `A solid shape with surface area and volume.` |
| `Shapes.TwoDim.Enums` | `TwoDimCategory` | `POLYGON` | `Polygon` | `A closed flat shape bounded by straight line segments.` |
| `Shapes.TwoDim.Enums` | `TwoDimCategory` | `NON_POLYGON` | `Non-polygon` | `A closed flat shape with a curved boundary.` |
| `Shapes.ThreeDiM.Enums` | `ThreeDimCategory` | `POLYHEDRON` | `Polyhedron` | `A closed solid bounded by flat polygonal faces.` |
| `Shapes.ThreeDiM.Enums` | `ThreeDimCategory` | `NON_POLYHEDRON` | `Non-polyhedron` | `A closed solid with at least one curved surface.` |
| `Shapes.TwoDim.Polygon.Enums` | `PolygonName` | `RECTANGLE` | `Rectangle` | `A polygon with four right angles.` |
| `Shapes.TwoDim.Polygon.Enums` | `PolygonName` | `SQUARE` | `Square` | `A rectangle with four equal sides.` |
| `Shapes.TwoDim.Polygon.Enums` | `PolygonName` | `REGULAR_HEXAGON` | `Regular hexagon` | `A polygon with six equal sides and six equal angles.` |
| `Shapes.TwoDim.NonPolygon.Enums` | `NonPolygonName` | `ELLIPSE` | `Ellipse` | `A closed curve specified by two perpendicular semi-axes.` |
| `Shapes.TwoDim.NonPolygon.Enums` | `NonPolygonName` | `CIRCLE` | `Circle` | `An ellipse with equal semi-axes.` |
| `Shapes.ThreeDiM.Polyhedra.Enums` | `PolyhedraName` | `RECTANGULAR_PRISM` | `Rectangular prism` | `A solid with six rectangular faces.` |
| `Shapes.ThreeDiM.Polyhedra.Enums` | `PolyhedraName` | `CUBE` | `Cube` | `A rectangular prism with six congruent square faces.` |
| `Shapes.ThreeDiM.NonPolyhedra.Enums` | `NonPolyhedraName` | `SPHERE` | `Sphere` | `A solid whose boundary is equally distant from its center.` |
| `Shapes.ThreeDiM.NonPolyhedra.Enums` | `NonPolyhedraName` | `CYLINDER` | `Cylinder` | `A closed right circular solid with two parallel circular bases.` |
| `Shapes.ThreeDiM.NonPolyhedra.Enums` | `NonPolyhedraName` | `CONE` | `Cone` | `A closed right circular solid with one circular base and one apex.` |

### Main default package

The provided driver builds all ten shapes and prints dimensions, categories, and
measurements through `TwoDimShape` and `ThreeDimShape` references. Tests verify
its output and consistent numeric formatting across locales.

* Run the demonstration from `Assignment` with `mvn compile exec:java`.

## How grading works

* Run `mvn test` from `Assignment`. Both student and instructor tests run.

The `main` branch provides `Main`, tests, and build configuration. Students create
all source files under `Shapes`. Compilation will fail until the required classes
and enums exist. A compilation failure receives 10 points.

* Push changes to run **Auto grading** after instructor pairing with AbstractClassroom.
* Check **Score** and **Grade Token** in the run summary. A green workflow alone does not guarantee 100.

Instructor tests check abstract contracts, inheritance and minimal constructors,
polymorphic measurements and scaling, validation, enum metadata, and driver output.
Measurement tests call the abstract API and allow inherited implementations.
These layers provide feedback, not separate partial scores.

| Result | Grade |
| --- | --- |
| Build succeeds and all tests pass | **100 points** |
| Any test fails or errors, build fails, or no tests are found | **10 points** |

There are no intermediate scores or first commit bonuses. Setup errors,
cancellation, or timeouts may prevent a token. Grading is defined in
`.github/actions/instructor/action.yml`. The workflow and configuration are under
`.github/workflows/` and `.github/actions/instructor/`.

## How to submit your grade

* Choose the run whose grade you want to submit.
* Use the **Grade Token** copy button to copy the complete string beginning with `ACGT1_`.
* Paste **only that complete token** into Blackboard's text field.
* Check your token before submitting: **only one submission is allowed**.
