package com.cemi.engine.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import com.cemi.engine.Settings;

public class Mesh {
    private float vertices[];
    private int indices[];
    private float textureCoords[];
    private float normals[];

    private int vaoID;
    private int vboID;
    private int iboID;

    protected Mesh() {
    }

    public Mesh(float vertices[], int indices[], float textureCoords[], float normals[]) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
        this.normals = normals;
    }

    public Mesh(float vertices[], int indices[]) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(Mesh mesh) {
        this.vertices = mesh.getVertices();
        this.indices = mesh.getIndices();
        this.textureCoords = mesh.getTextureCoords();
        this.normals = mesh.getNormals();
    }

    public void init() {
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        GL30.glEnableVertexAttribArray(0);

        vboID = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);

        iboID = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);

        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

        GL30.glBindVertexArray(0);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public void setMesh(Mesh mesh) {
        this.vertices = mesh.getVertices();
        this.indices = mesh.getIndices();
        this.textureCoords = mesh.getTextureCoords();
        this.normals = mesh.getNormals();
    }

    public static class Builder {
        private List<Vector3f> vertices = new ArrayList<Vector3f>();
        private List<Triangle> triangles = new ArrayList<Triangle>();

        public void withVertex(Vector3f vertex) {
            vertices.add(vertex);
        }

        public void withVertices(Vector3f[] vertices) {
            this.vertices.addAll(Arrays.asList(vertices));
        }

        public void withTriangle(Triangle triangle) {
            triangles.add(triangle);
        }

        public void withTriangles(Triangle[] triangles) {
            this.triangles.addAll(Arrays.asList(triangles));
        }

        public Mesh build() {
            float[] vertices = new float[this.vertices.size() * 3];
            for (int i = 0; i < this.vertices.size(); i++) {
                vertices[i * 3] = this.vertices.get(i).x;
                vertices[i * 3 + 1] = this.vertices.get(i).y;
                vertices[i * 3 + 2] = this.vertices.get(i).z;
            }
            int[] indices = new int[triangles.size() * 3];
            for (int i = 0; i < triangles.size(); i++) {
                indices[i * 3] = triangles.get(i).getA();
                indices[i * 3 + 1] = triangles.get(i).getB();
                indices[i * 3 + 2] = triangles.get(i).getC();
            }
            float[] textureCoords = new float[vertices.length * 2 / 3];
            float[] normals = new float[vertices.length];
            /*
             * for (int i = 0; i < textureCoords.length; i++) {
             * textureCoords[i * 2] = vertices[i * 3];
             * textureCoords[i * 2 + 1] = vertices[i * 3 + 1];
             * }
             * 
             * for (int i = 0; i < normals.length; i++) {
             * normals[i] = 0;
             * }
             */

            return new Mesh(vertices, indices, textureCoords, normals);
        }
    }

    public static Mesh loadMesh(String fileName) {
        return null;
    }

    public void render() {
        GL30.glBindVertexArray(vaoID);
        GL30.glEnableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
        GL30.glDrawElements(GL30.GL_TRIANGLES, indices.length, GL30.GL_UNSIGNED_INT, 0);
        if(Settings.isDebug()) {
            GLStateManager.glColor4f(1, 0, 1, 1);
            GL30.glDrawElements(GL30.GL_LINE_LOOP, indices.length, GL30.GL_UNSIGNED_INT, 0);
        }
    }

    public void cleanup() {
        GL30.glDeleteVertexArrays(vaoID);
        GL30.glDeleteBuffers(vboID);
        GL30.glDeleteBuffers(iboID);
    }
}
