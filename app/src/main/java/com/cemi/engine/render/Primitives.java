package com.cemi.engine.render;

import org.joml.Vector3f;

public class Primitives {
    public static class Plane extends Mesh {

        private int subdivisions;

        public Plane(float size, int subdivisions) {
            this.subdivisions = subdivisions;
            Builder builder = new Builder();
            for (float y = -size / 2.f; y <= size / 2.f; y += size / (subdivisions + 1)) {
                for (float x = -size / 2.f; x <= size / 2.f; x += size / (subdivisions + 1)) {
                    builder.withVertex(new Vector3f(x, y, 0));
                }
            }
            for (int vi = 0, y = 0; y <= subdivisions; y++, vi++) {
                for (int x = 0; x <= subdivisions; x++, vi++) {
                    builder.withTriangle(new Triangle(vi, vi + subdivisions + 2, vi + 1));
                    builder.withTriangle(new Triangle(vi + subdivisions + 2, vi + 1, vi + subdivisions + 3));
                }
            }
            setMesh(builder.build());
        }

        /**
         * Used to get the width of the plane (in points)
         * 
         * @return Width of the imaginary 2D array
         */
        public int getWidth() {
            return subdivisions + 2;
        }
    }

    public static class Cube extends Mesh {

        private int subdivisions;

        public Cube(float size, int subdivisions) {
            this.subdivisions = subdivisions;
            Builder builder = new Builder();
            float halfSize = size / 2.f;
            builder.withVertex(new Vector3f(-halfSize, halfSize, halfSize));
            builder.withVertex(new Vector3f(-halfSize, -halfSize, halfSize));
            builder.withVertex(new Vector3f(halfSize, -halfSize, halfSize));
            builder.withVertex(new Vector3f(halfSize, halfSize, halfSize));
            builder.withVertex(new Vector3f(-halfSize, halfSize, -halfSize));
            builder.withVertex(new Vector3f(halfSize, halfSize, -halfSize));
            builder.withVertex(new Vector3f(-halfSize, -halfSize, -halfSize));
            builder.withVertex(new Vector3f(halfSize, -halfSize, -halfSize));

            builder.withTriangle(new Triangle(0, 1, 3));
            builder.withTriangle(new Triangle(3, 1, 2));
            builder.withTriangle(new Triangle(4, 0, 3));
            builder.withTriangle(new Triangle(5, 4, 3));
            builder.withTriangle(new Triangle(3, 2, 7));
            builder.withTriangle(new Triangle(5, 3, 7));
            builder.withTriangle(new Triangle(6, 1, 0));
            builder.withTriangle(new Triangle(6, 0, 4));
            builder.withTriangle(new Triangle(2, 1, 6));
            builder.withTriangle(new Triangle(2, 6, 7));
            builder.withTriangle(new Triangle(7, 6, 4));
            builder.withTriangle(new Triangle(7, 4, 5));

            setMesh(builder.build());
        }

        /**
         * Used to get the width of the plane (in points)
         * 
         * @return Width of the imaginary 2D array
         */
        public int getWidth() {
            return subdivisions + 2;
        }
    }

}
