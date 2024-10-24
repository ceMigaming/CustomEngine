package com.cemi.engine.render;

public class Primitives {
    public static class Plane extends Mesh {

        private int subdivisions;

        public Plane(float size, int subdivisions) {
            this.subdivisions = subdivisions;
            Builder builder = new Builder();
            float step = size / subdivisions;
            for (int i = 0; i < subdivisions; i++) {
                for (int j = 0; j < subdivisions; j++) {
                    builder.withVertex(i * step, j * step, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f);
                    builder.withVertex((i + 1) * step, j * step, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    builder.withVertex(i * step, (j + 1) * step, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);

                    builder.withVertex((i + 1) * step, j * step, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    builder.withVertex((i + 1) * step, (j + 1) * step, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
                    builder.withVertex(i * step, (j + 1) * step, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
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

        public Cube(float size /* , int subdivisions */) {
            // this.subdivisions = subdivisions;
            Builder builder = new Builder();
            float halfSize = size / 2.f;
            // front
            builder.withVertex(-halfSize, -halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f);
            builder.withVertex(halfSize, halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f);
            builder.withVertex(-halfSize, halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f);
            builder.withVertex(-halfSize, -halfSize, -halfSize, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f);

            // back
            builder.withVertex(-halfSize, -halfSize, halfSize, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, halfSize, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, halfSize, halfSize, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            builder.withVertex(halfSize, halfSize, halfSize, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            builder.withVertex(-halfSize, halfSize, halfSize, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
            builder.withVertex(-halfSize, -halfSize, halfSize, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f);

            // right
            builder.withVertex(-halfSize, halfSize, halfSize, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            builder.withVertex(-halfSize, halfSize, -halfSize, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(-halfSize, -halfSize, -halfSize, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(-halfSize, -halfSize, -halfSize, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(-halfSize, -halfSize, halfSize, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f);
            builder.withVertex(-halfSize, halfSize, halfSize, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            // left
            builder.withVertex(halfSize, halfSize, halfSize, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            builder.withVertex(halfSize, halfSize, -halfSize, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(halfSize, -halfSize, -halfSize, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, -halfSize, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, halfSize, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f);
            builder.withVertex(halfSize, halfSize, halfSize, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            // bottom
            builder.withVertex(-halfSize, -halfSize, -halfSize, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, -halfSize, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, -halfSize, halfSize, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(halfSize, -halfSize, halfSize, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(-halfSize, -halfSize, halfSize, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f);
            builder.withVertex(-halfSize, -halfSize, -halfSize, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f);

            // top
            builder.withVertex(-halfSize, halfSize, -halfSize, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f);
            builder.withVertex(halfSize, halfSize, -halfSize, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f);
            builder.withVertex(halfSize, halfSize, halfSize, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(halfSize, halfSize, halfSize, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
            builder.withVertex(-halfSize, halfSize, halfSize, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f);
            builder.withVertex(-halfSize, halfSize, -halfSize, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f);

            setMesh(builder.build());
        }

        // public Egg() {
        //     Builder builder = new Builder();
            
        // }

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
