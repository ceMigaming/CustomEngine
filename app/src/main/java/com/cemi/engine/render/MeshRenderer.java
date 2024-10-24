package com.cemi.engine.render;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

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
        GL30.glUniform1i(GL30.glGetUniformLocation(shader.getID(), "texture1"), 0);
        GLStateManager.glColor3f(1, 165.0f / 255.0f, 0);
        GLStateManager.glApplyCameraView();
        GLStateManager.glApplyModel(x, y, z, xScale, yScale, zScale, pitch, yaw, roll);
        GLStateManager.glApplyProjection();
        GLStateManager.glSetUniform3f("ambientColor", 1, 1, 1);
        GLStateManager.glSetUniform1f("ambientIntensity", 0.1f);
        GLStateManager.glSetUniform3f("lightPosition", 0, 0, 0);
        GLStateManager.glSetUniform3f("lightColor", 1, 1, 1);
        Vector3f cameraPosition = Camera.getMainCamera().getTransform().getPosition();
        GLStateManager.glSetUniform3f("cameraPosition", cameraPosition.x, cameraPosition.y, cameraPosition.z);
        GLStateManager.glSetUniform1f("shininess", 32);
        GLStateManager.glSetUniform1f("specularIntensity", 0.5f);
        mesh.render();
        shader.unbind();
    }

    public Mesh getMesh() {
        return mesh;
    }
}
