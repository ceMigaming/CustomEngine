package com.cemi.engine.render;

public class Triangle {
    private int v1, v2, v3;

    public Triangle(int v1, int v2, int v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public int[] getVertices() {
        return new int[] { v1, v2, v3 };
    }

    public void setVertices(int[] vertices) {
        this.v1 = vertices[0];
        this.v2 = vertices[1];
        this.v3 = vertices[2];
    }

    public void setVertices(int v1, int v2, int v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public int getA() {
        return v1;
    }

    public int getB() {
        return v2;
    }

    public int getC() {
        return v3;
    }
}
