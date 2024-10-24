package com.cemi.engine.render;

public record Vertex(float x, float y, float z, float normalX, float normalY, float normalZ, float u, float v) {
    public Vertex {
        if (x == Float.NaN || y == Float.NaN || z == Float.NaN || normalX == Float.NaN || normalY == Float.NaN
                || normalZ == Float.NaN || u == Float.NaN || v == Float.NaN) {
            throw new IllegalArgumentException("Vertex cannot have NaN values");
        }
    }
}
