package com.cemi.game;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.cemi.engine.Engine;
import com.cemi.engine.math.GameObject;
import com.cemi.engine.math.Transform;
import com.cemi.engine.render.Camera;
import com.cemi.engine.render.GLStateManager;
import com.cemi.engine.render.Mesh;
import com.cemi.engine.render.MeshRenderer;
import com.cemi.engine.render.Primitives;
import com.cemi.engine.render.Renderer;
import com.cemi.engine.render.Shader;
import com.cemi.engine.system.Input;
import com.cemi.engine.system.Settings;
import com.cemi.engine.system.Time;

public class Game extends Engine {

    public static void main(String[] args) {
        Settings.setTitle("Game");
        Settings.setDebug(true);

        Camera.setMainCamera(new Camera() {
            private float moveSpeed = 1;

            @Override
            public void update() {
                super.update();
                if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
                    getTransform().translate(
                            getLookDirection().mul(-(float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
                    getTransform()
                            .translate(getLookDirection().mul((float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
                    getTransform().translate(getLookDirection().rotateY((float) Math.PI / 2f, new Vector3f())
                            .mul((float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
                    getTransform().translate(getLookDirection().rotateY((float) -Math.PI / 2f, new Vector3f())
                            .mul((float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    getTransform().translate(Transform.UP.mul((float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                    getTransform()
                            .translate(Transform.DOWN.mul((float) Time.getDeltaTime() * moveSpeed, new Vector3f()));
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
                    getLookDirection().rotateAxis(-(float) Time.getDeltaTime(), 0.f, 1.f, 0.f);
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
                    getLookDirection().rotateAxis((float) Time.getDeltaTime(), 0.f, 1.f, 0.f);
                }
                // if (Input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
                // getLookDirection().rotateAxis(-(float) Time.getDeltaTime(),
                // getLookDirection().z,
                // 0.f, getLookDirection().x);
                // }
                // if (Input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
                // getLookDirection().rotateAxis((float) Time.getDeltaTime(),
                // getLookDirection().z,
                // 0.f, getLookDirection().x);
                // }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
                    moveSpeed = 4f;
                } else {
                    moveSpeed = 1;
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
                    Game.setWindowShouldClose();
                }
            }
        });

        Renderer waterRenderer = new MeshRenderer("water", new Shader("water")) {
            public void render(float x, float y, float z, float xScale, float yScale,
                    float zScale, float pitch,
                    float yaw,
                    float roll) {
                shader.bind();
                GLStateManager.glColor4f(0, 0.1f, 0.2f, 1);
                GLStateManager.glSetUniform1f("uTime", (float) Time.getTime());
                GLStateManager.glSetUniform2f("uResolution", Settings.getWidth(),
                        Settings.getHeight());
                GLStateManager.glApplyCameraView();
                GLStateManager.glApplyModel(x, y, z, xScale, yScale, zScale, pitch, yaw,
                        roll);
                GLStateManager.glApplyProjection();
                mesh.render();
                shader.unbind();
            }
        };

        Game.addGameObject(new GameObject("cube", new MeshRenderer("cube", new Primitives.Cube(2))) {

            @Override
            protected void init() {
                super.init();
                getTransform().setPosition(0, 0, -3);
            }

            @Override
            protected void update() {
                super.update();
                Settings.setTitle("Game | " + (int) Time.getFPS() + " FPS");
                if (Input.isKeyDown(GLFW.GLFW_KEY_F3)) {
                    Settings.setDebug(!Settings.isDebug());
                }
                getTransform().rotate(0, (float) Time.getDeltaTime(), 0);
            }
        });
        Game.addGameObject(new GameObject("water", waterRenderer) {
            @Override
            protected void init() {
                getTransform().setPosition(-2, -0.5f, 0);
                super.init();
            }
        });
        Camera.getMainCamera().getTransform().setPosition(0, 0, 0);

        // Game.addGameObject(new GameObject("model", new MeshRenderer("model", Mesh.loadMesh("/models/egg.obj"))) {

        //     @Override
        //     protected void init() {
        //         super.init();
        //         getTransform().setPosition(0, 0, 3);
        //     }
        // });

        new Game().run();
    }
}
