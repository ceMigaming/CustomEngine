package com.cemi.engine.render;

import org.lwjgl.opengl.GL30;

import com.cemi.engine.GameObject;
import com.cemi.engine.Settings;

public class UIRenderer {

    static Shader uiShader = Shader.UI_SHADER;

    public UIRenderer() {
        uiShader = Shader.UI_SHADER;
    }

    public UIRenderer(Shader shader) {
        uiShader = shader;
    }

    private static float vertices[] = {
            -1, -1, 0,
            1, -1, 0,
            1, 1, 0,
            -1, 1, 0
    };
    private static int indices[] = {
            0, 1, 2,
            2, 3, 0
    };
    private static float textureCoords[] = {
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };
    private static float normals[] = {
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1 };

    private static int vaoID;
    private static int vboID;
    private static int iboID;

    public static void init() {
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

    public void render(int x, int y, int width, int height) {

        float mappedWidth = (float) width / Settings.getWidth();
        float mappedHeight = (float) height / Settings.getHeight();

        float mappedX = (float) x / Settings.getWidth() * 2 - 1 + mappedWidth;
        float mappedY = (float) y / Settings.getHeight() * 2 + 1 - mappedHeight;

        uiShader.bind();
        GLStateManager.glColor4f(1, 1, 1, 1);
        GL30.glBindVertexArray(vaoID);
        GL30.glEnableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
        GL30.glDrawElements(GL30.GL_TRIANGLES, indices.length, GL30.GL_UNSIGNED_INT, 0);
        if (Settings.isDebug()) {
            GLStateManager.glColor4f(1, 0, 1, 1);
            GL30.glDrawElements(GL30.GL_LINE_LOOP, indices.length, GL30.GL_UNSIGNED_INT, 0);
        }
        GLStateManager.glApplyModel(mappedX, mappedY, 0, mappedWidth, mappedHeight, 1.f, 0.f, 0.f, 0.f);
        uiShader.unbind();
    }

    public void render(GameObject gameObject) {
        render((int) gameObject.getTransform().getPosition().x, (int) gameObject.getTransform().getPosition().y,
                (int) gameObject.getTransform().getScale().x, (int) gameObject.getTransform().getScale().y);
    }

    public Shader getShader() {
        return uiShader;
    }
}
