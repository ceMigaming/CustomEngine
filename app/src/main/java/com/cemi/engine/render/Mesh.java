package com.cemi.engine.render;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL30;

import com.cemi.engine.render.model.OBJLoader;
import com.cemi.engine.system.Settings;

public class Mesh {
    Texture texture;
    public static final int VERTEX_COMPONENTS = 8;
    public static final int BYTES_PER_FLOAT = Float.SIZE / Byte.SIZE;
    private static final int POSITION_SIZE = 3;
    private static final int NORMAL_SIZE = 3;
    private static final int TEXTURE_COORDINATES_SIZE = 2;

    private float vertices[];

    private int vaoID;
    private int vboID;

    protected Mesh() {
    }

    public Mesh(float vertices[]) {
        this.vertices = vertices;
    }

    public void init() {
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        vboID = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);

        // position
        GL30.glVertexAttribPointer(0, POSITION_SIZE, GL30.GL_FLOAT, false, VERTEX_COMPONENTS * BYTES_PER_FLOAT, 0);
        GL30.glEnableVertexAttribArray(0);

        // normal
        GL30.glVertexAttribPointer(1, NORMAL_SIZE, GL30.GL_FLOAT, false, VERTEX_COMPONENTS * BYTES_PER_FLOAT,
                POSITION_SIZE * BYTES_PER_FLOAT);
        GL30.glEnableVertexAttribArray(1);

        // texture coordinates
        GL30.glVertexAttribPointer(2, TEXTURE_COORDINATES_SIZE, GL30.GL_FLOAT, false,
                VERTEX_COMPONENTS * BYTES_PER_FLOAT,
                (POSITION_SIZE + NORMAL_SIZE) * BYTES_PER_FLOAT);
        GL30.glEnableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
        try {
            texture = new Texture("/textures/tex1.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public void setMesh(Mesh mesh) {
        this.vertices = mesh.getVertices();
    }

    public static class Builder {
        private List<Vertex> vertices = new ArrayList<Vertex>();

        public void withVertex(Vertex vertex) {
            vertices.add(vertex);
        }

        public void withVertex(float x, float y, float z, float normalX, float normalY, float normalZ, float u,
                float v) {
            vertices.add(new Vertex(x, y, z, normalX, normalY, normalZ, u, v));
        }

        public void withVertices(Vertex[] vertices) {
            this.vertices.addAll(Arrays.asList(vertices));
        }

        public Mesh build() {
            float[] vertices = new float[this.vertices.size() * VERTEX_COMPONENTS];
            for (int i = 0; i < this.vertices.size(); i++) {
                vertices[i * VERTEX_COMPONENTS] = this.vertices.get(i).x();
                vertices[i * VERTEX_COMPONENTS + 1] = this.vertices.get(i).y();
                vertices[i * VERTEX_COMPONENTS + 2] = this.vertices.get(i).z();
                vertices[i * VERTEX_COMPONENTS + 3] = this.vertices.get(i).normalX();
                vertices[i * VERTEX_COMPONENTS + 4] = this.vertices.get(i).normalY();
                vertices[i * VERTEX_COMPONENTS + 5] = this.vertices.get(i).normalZ();
                vertices[i * VERTEX_COMPONENTS + 6] = this.vertices.get(i).u();
                vertices[i * VERTEX_COMPONENTS + 7] = this.vertices.get(i).v();
            }

            return new Mesh(vertices);
        }
    }

    public static Mesh loadMesh(String path) {
        if (path.endsWith(".obj")) {
            return OBJLoader.load(path);
        }
        return null;
    }

    public void render() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        if (Settings.isDebug()) {
            GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE);
        }
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texture.getTextureID());
        GL30.glBindVertexArray(vaoID);
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, vertices.length / 8);
        GL30.glDisable(GL30.GL_DEPTH_TEST);
        GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_FILL);
    }

    public void cleanup() {
        GL30.glDeleteVertexArrays(vaoID);
        GL30.glDeleteBuffers(vboID);
    }
}
