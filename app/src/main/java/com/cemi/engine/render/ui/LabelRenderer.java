package com.cemi.engine.render.ui;

import java.awt.image.BufferedImage;

import com.cemi.engine.render.text.TextRenderer;

public class LabelRenderer extends UIRenderer {

    protected String text;

    public LabelRenderer(String text) {
        this.text = text;
    }

    public void setText(String text) {
        image = TextRenderer.getRenderedText(text);
        this.text = text;
    }

    @Override
    public void render(int x, int y, int width, int height) {
        super.render(x, y, width, height);
    }

}
