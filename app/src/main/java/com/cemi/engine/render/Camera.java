package com.cemi.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.cemi.engine.Engine;
import com.cemi.engine.math.GameObject;
import com.cemi.engine.math.Transform;

public class Camera extends GameObject {

    static Camera mainCamera = new Camera();
    private Vector3f lookDir = new Vector3f(0, 0, -1);

    public Camera() {
        super();
        mainCamera = this;
        Engine.addGameObject(this);
    }

    protected Vector3f getLookDirection() {
        return lookDir;
    }

    protected void setLookDirection(Vector3f direction) {
        this.getTransform().lookAt(direction);
        lookDir = direction;
    }

    public static void setMainCamera(Camera camera) {
        Engine.addGameObject(camera);
        Engine.removeGameObject(mainCamera);
        mainCamera = camera;

    }

    @Override
    public void update() {
        super.update();
        // setLookDirection(getTransform().toEulerAngles());
    }

    public static Camera getMainCamera() {
        return mainCamera;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.lookAt(getTransform().getPosition(), getTransform().getPosition().add(lookDir, new Vector3f()),
                Transform.UP, matrix);
        return matrix;
    }

    public static Matrix4f getProjectionMatrix(float aspect, float fov, float near, float far) {
        Matrix4f matrix = new Matrix4f();
        matrix.perspective(fov, aspect, near, far);
        return matrix;
    }
}