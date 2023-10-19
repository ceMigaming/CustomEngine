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
            for (float x = -halfSize; x <= halfSize; x += size) {
                for (float y = -halfSize; y <= halfSize; y += size) {
                    for (float z = -halfSize; z <= halfSize; z += size) {
                        builder.withVertex(new Vector3f(x, y, z));
                    }
                }
            }
            for (int vi = 0; vi < 8; vi++) {
                int baseIndex = vi * 4;
                builder.withTriangle(new Triangle(baseIndex, baseIndex + 1, baseIndex + 2));
                // builder.withTriangle(new Triangle(baseIndex + 2, baseIndex + 1, baseIndex + 3));
                // builder.withTriangle(new Triangle(baseIndex + 2, baseIndex + 3, baseIndex + 6));
                // builder.withTriangle(new Triangle(baseIndex + 6, baseIndex + 3, baseIndex + 7));
                // builder.withTriangle(new Triangle(baseIndex + 6, baseIndex + 7, baseIndex + 5));
                // builder.withTriangle(new Triangle(baseIndex + 5, baseIndex + 7, baseIndex + 4));
                // builder.withTriangle(new Triangle(baseIndex + 5, baseIndex + 4, baseIndex));
                // builder.withTriangle(new Triangle(baseIndex, baseIndex + 4, baseIndex + 1));
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

}
