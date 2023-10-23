package com.cemi.engine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private Vector3f scale = new Vector3f(1, 1, 1);

    public static final Vector3f FORWARD = new Vector3f(0, 0, 1);
    public static final Vector3f BACK = new Vector3f(0, 0, -1);
    public static final Vector3f UP = new Vector3f(0, 1, 0);
    public static final Vector3f DOWN = new Vector3f(0, -1, 0);
    public static final Vector3f LEFT = new Vector3f(-1, 0, 0);
    public static final Vector3f RIGHT = new Vector3f(1, 0, 0);

    public Transform() {
    }

    public Transform(Vector3f position) {
        this.position = position;
    }

    public Transform(Vector3f position, Quaternionf rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Transform(Vector3f position, Quaternionf rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation.identity();
        this.rotation.rotateXYZ(rotation.x, rotation.y, rotation.z);
        this.scale = scale;
    }

    public void translate(Vector3f translation) {
        position.add(translation);
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z);
    }

    public void translate(Vector3f translation, Vector3f axis) {
        position.add(translation.x * axis.x, translation.y * axis.y, translation.z * axis.z);
    }

    public void rotate(Quaternionf rotation) {
        this.rotation.mul(rotation);
    }

    public void rotate(float angle, Vector3f axis) {
        this.rotation.rotateAxis(angle, axis);
    }

    public void rotate(float x, float y, float z) {
        this.rotation.rotateXYZ(x, y, z);
    }

    public void scale(Vector3f scale) {
        this.scale.mul(scale);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f toEulerAngles() {
        return rotation.getEulerAnglesXYZ(new Vector3f());
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public void fromEulerAngles(Vector3f angles) {
        rotation.identity();
        rotation.rotateX(angles.x);
        rotation.rotateY(angles.y);
        rotation.rotateZ(angles.z);
    }

    public void fromEulerAngles(float x, float y, float z) {
        rotation.identity();
        rotation.rotateX(x);
        rotation.rotateY(y);
        rotation.rotateZ(z);
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }

    public Transform copy() {
        return new Transform(position, rotation, scale);
    }

    public void lookAt(Vector3f direction) {
        rotation.identity();
        rotation.lookAlong(direction, new Vector3f(0, 1, 0));
    }

    public Matrix4f getModelMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }

}
