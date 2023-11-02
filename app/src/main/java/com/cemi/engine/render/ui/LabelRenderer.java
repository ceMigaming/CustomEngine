package com.cemi.engine.render.ui;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import com.cemi.engine.render.GLStateManager;
import com.cemi.engine.render.GraphicsUtil;
import com.cemi.engine.render.text.TextRenderer;
import com.cemi.engine.system.Settings;

public class LabelRenderer extends UIRenderer {

    protected String text;

    protected int textureID;

    protected ByteBuffer buffer;

    public LabelRenderer(String text) {
        setText(text);
    }

    public LabelRenderer() {
        this("");
    }

    public LabelRenderer(BufferedImage image) {
        buffer = GraphicsUtil.createBufferFromImage(image);
    }

    public void setText(String text) {
        BufferedImage image = TextRenderer.getRenderedText(text);
        buffer = GraphicsUtil.createBufferFromImage(image);
        this.text = text;
    }

    @Override
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

}
