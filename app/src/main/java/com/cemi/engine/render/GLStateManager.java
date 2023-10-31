package com.cemi.engine.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import com.cemi.engine.math.Transform;
import com.cemi.engine.system.Settings;

public class GLStateManager {

    public static void glPushMatrix() {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "mvp");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        Matrix4f matrix = new Matrix4f();
        matrix.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glColor4f(float r, float g, float b, float a) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int colorLoc = GL30.glGetUniformLocation(shaderProgramID, "color");
        GL30.glUniform4f(colorLoc, r, g, b, a);
    }

    public static void glSetUniform3f(String uniform, float x, float y, float z) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform3f(loc, x, y, z);
    }

    public static void glSetUniformVec3f(String uniform, Vector3f vec) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform3f(loc, vec.x, vec.y, vec.z);
    }

    public static void glSetUniform4f(String uniform, float x, float y, float z, float w) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform4f(loc, x, y, z, w);
    }

    public static void glSetUniform2f(String uniform, float x, float y) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform2f(loc, x, y);
    }

    public static void glSetUniform1f(String uniform, float f) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform1f(loc, f);
    }

    public static void glSetUniform1i(String uniform, int f) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int loc = GL30.glGetUniformLocation(shaderProgramID, uniform);
        GL30.glUniform1i(loc, f);
    }

    public static void glApplyModel(Transform transform) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        Matrix4f model = transform.getModelMatrix();
        model.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glApplyModel(float x, float y, float z, float xScale, float yScale, float zScale, float pitch,
            float yaw, float roll) {
        glApplyModel(new Transform(new Vector3f(x, y, z), new Vector3f(pitch, yaw, roll),
                new Vector3f(xScale, yScale, zScale)));
    }

    public static void glApplyCameraView() {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int viewLoc = GL30.glGetUniformLocation(shaderProgramID, "view");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        Camera camera = Camera.getMainCamera();
        Matrix4f view = camera.getViewMatrix();
        view.get(buffer);
        GL30.glUniformMatrix4fv(viewLoc, false, buffer);
    }

    public static void glApplyProjection() {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int projectionLoc = GL30.glGetUniformLocation(shaderProgramID, "projection");
        FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f projection = Camera.getProjectionMatrix((float) Settings.getWidth() / (float) Settings.getHeight(), 70,
                0.1f, 1000);
        projection.get(projBuffer);
        GL30.glUniformMatrix4fv(projectionLoc, false, projBuffer);
    }

    public static void glTranslatef(float x, float y, float z) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        GL30.glGetUniformfv(shaderProgramID, modelLoc, buffer);
        Matrix4f matrix = new Matrix4f();
        matrix.set(buffer);
        matrix.translate(x, y, z);
        matrix.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glRotatef(float angle, float x, float y, float z) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        GL30.glGetUniformfv(shaderProgramID, modelLoc, buffer);
        Matrix4f matrix = new Matrix4f();
        matrix.set(buffer);
        matrix.rotate(angle, x, y, z);
        matrix.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glScalef(float x, float y, float z) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        GL30.glGetUniformfv(shaderProgramID, modelLoc, buffer);
        Matrix4f matrix = new Matrix4f();
        matrix.set(buffer);
        matrix.scale(x, y, z);
        matrix.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glLocalRotatef(float angle, float x, float y, float z) {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        GL30.glGetUniformfv(shaderProgramID, modelLoc, buffer);
        Matrix4f matrix = new Matrix4f();
        matrix.set(buffer);
        matrix.rotateLocal(angle, x, y, z);
        matrix.get(buffer);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

    public static void glPopMatrix() {
        int shaderProgramID = GL30.glGetInteger(GL30.GL_CURRENT_PROGRAM);
        int modelLoc = GL30.glGetUniformLocation(shaderProgramID, "model");
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        GL30.glUniformMatrix4fv(modelLoc, false, buffer);
    }

}
