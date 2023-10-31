package com.cemi.engine.render;

import com.cemi.engine.math.GameObject;

public class Renderer {
    protected String name;

    protected Shader shader;

    public Renderer(String name) {
        this.name = name;
        this.shader = Shader.DEFAULT;
    }

    public Renderer(String name, Shader shader) {
        this.name = name;
        this.shader = shader;
    }

    public void render(float x, float y, float z, float xScale, float yScale, float zScale, float pitch, float yaw,
            float roll) {

    }

    public void render(float x, float y, float z, float xScale, float yScale, float zScale) {
        render(x, y, z, xScale, yScale, zScale, 0, 0, 0);
    }

    public void render(float x, float y, float z) {
        render(x, y, z, 1, 1, 1);
    }

    public void render(GameObject gameObject) {
        render(gameObject.getTransform().getPosition().x, gameObject.getTransform().getPosition().y,
                gameObject.getTransform().getPosition().z, gameObject.getTransform().getScale().x,
                gameObject.getTransform().getScale().y, gameObject.getTransform().getScale().z,
                (float) gameObject.getTransform().toEulerAngles().x,
                (float) gameObject.getTransform().toEulerAngles().y,
                (float) gameObject.getTransform().toEulerAngles().z);
    }

    public Shader getShader() {
        return shader;
    }
}
