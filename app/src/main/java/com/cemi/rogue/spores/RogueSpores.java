package com.cemi.rogue.spores;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.cemi.engine.Engine;
import com.cemi.engine.GameObject;
import com.cemi.engine.Input;
import com.cemi.engine.Settings;
import com.cemi.engine.Time;
import com.cemi.engine.Transform;
import com.cemi.engine.render.Camera;
import com.cemi.engine.render.GLStateManager;
import com.cemi.engine.render.MeshRenderer;
import com.cemi.engine.render.Primitives;
import com.cemi.engine.render.Renderer;
import com.cemi.engine.render.Shader;
import com.cemi.engine.render.UIRenderer;

public class RogueSpores extends Engine {

    public static void main(String[] args) {
        Settings.setTitle("Rogue Spores");
        Settings.setDebug(true);

        Camera.setMainCamera(new Camera() {
            @Override
            public void update() {
                super.update();
                if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
                    getTransform().translate(getLookDirection().mul(-(float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
                    getTransform().translate(getLookDirection().mul((float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
                    getTransform().translate(getLookDirection().rotateY((float) Math.PI / 2f, new Vector3f())
                            .mul((float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
                    getTransform().translate(getLookDirection().rotateY((float) -Math.PI / 2f, new Vector3f())
                            .mul((float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    getTransform().translate(Transform.UP.mul((float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                    getTransform().translate(Transform.DOWN.mul((float) Time.getDeltaTime(), new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_E)) {
                    getLookDirection().rotateAxis(-(float) Time.getDeltaTime(), 0.f, 1.f, 0.f);
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_Q)) {
                    getLookDirection().rotateAxis((float) Time.getDeltaTime(), 0.f, 1.f, 0.f);
                }
                // getLookDirection().rotateAxis((float) Time.getDeltaTime() * (float) Input.getDeltaMouse().x, 0.f, 1.f,
                //         0.f);
                float deltaY = (float) Time.getDeltaTime() * (float) -Input.getDeltaMouse().y;
                float angle = (float) Math.atan2(getLookDirection().z, getLookDirection().x);
                // getLookDirection().rotateX((float) Math.sin(angle) * deltaY);
                // getLookDirection().rotateZ((float) Math.cos(angle) * -deltaY);
            }
        });

        Renderer waterRenderer = new MeshRenderer("water", new Shader("water")) {
            public void render(float x, float y, float z, float xScale, float yScale, float zScale, float pitch,
                    float yaw,
                    float roll) {
                shader.bind();
                GLStateManager.glColor4f(0, 0.1f, 0.2f, 1);
                GLStateManager.glSetUniform1f("uTime", (float) Time.getTime());
                GLStateManager.glSetUniform2f("uResolution", Settings.getWidth(), Settings.getHeight());
                GLStateManager.glApplyCameraView();
                GLStateManager.glApplyModel(x, y, z, xScale, yScale, zScale, pitch, yaw, roll);
                GLStateManager.glApplyProjection();
                mesh.render();
                shader.unbind();
            }
        };

        RogueSpores.addGameObject(new GameObject("ui", new UIRenderer()) {

            @Override
            protected void init() {
                super.init();
                getTransform().setPosition(0, 0, -1);

            }

            @Override
            protected void update() {
                super.update();
                if (Input.isKeyDown(GLFW.GLFW_KEY_F3)) {
                    Settings.setDebug(!Settings.isDebug());
                }
            }
        });

        RogueSpores.addGameObject(new GameObject("cube", new MeshRenderer("cube", new Primitives.Cube(1, 1))) {

            @Override
            protected void init() {
                super.init();
                getTransform().setPosition(0, 0, -10);
            }

            @Override
            protected void update() {
                super.update();
                Settings.setTitle("Rogue Spores | " + (int) Time.getFPS());
                // getTransform().translate(0, 0, -0.005f);
            }
        });
        RogueSpores.addGameObject(new GameObject("water", waterRenderer));
        Camera.getMainCamera().getTransform().setPosition(0, 0, 0);
        RogueSpores.run();
    }
}
