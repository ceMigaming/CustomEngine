package com.cemi.engine.render;

import org.lwjgl.opengl.GL11;

public class MeshRenderer extends Renderer {

    protected Mesh mesh;

    public MeshRenderer() {
        super("mesh");
        mesh = new Primitives.Plane(1, 1);
    }

    public MeshRenderer(Shader shader) {
        super("mesh", shader);
        mesh = new Primitives.Plane(1, 1);
    }

    public MeshRenderer(String name, Shader shader) {
        super(name, shader);
        mesh = new Primitives.Plane(1, 1);
    }

    public MeshRenderer(String name) {
        super(name);
        mesh = new Primitives.Plane(1, 1);
    }

    public MeshRenderer(Mesh mesh) {
        super("mesh");
        this.mesh = mesh;
    }

    public MeshRenderer(Mesh mesh, Shader shader) {
        super("mesh", shader);
        this.mesh = mesh;
    }

    public MeshRenderer(String name, Mesh mesh) {
        super(name);
        this.mesh = mesh;
    }

    public MeshRenderer(String name, Mesh mesh, Shader shader) {
        super(name, shader);
        this.mesh = mesh;
    }

    @Override
    public void render(float x, float y, float z, float xScale, float yScale, float zScale, float pitch, float yaw,
            float roll) {
        shader.bind();
        GLStateManager.glColor4f(1, 1, 1, 1);
        GLStateManager.glApplyCameraView();
        GLStateManager.glApplyModel(x, y, z, xScale, yScale, zScale, pitch, yaw, roll);
        GLStateManager.glApplyProjection();
        mesh.render();
        shader.unbind();
    }

    public Mesh getMesh() {
        return mesh;
    }
}
