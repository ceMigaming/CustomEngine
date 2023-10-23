package com.cemi.engine.render.text;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JLabel;

public class TextRenderer {

    private final static JLabel renderer = new JLabel();

    static {
        renderer.setFont(new Font("Serif", Font.PLAIN, 48));
    }

    HashMap<String, BufferedImage> cachedGlyphs = new HashMap<>();

    public BufferedImage getRenderedGlyph(String glyph) {
        String glyphName = glyph.replaceAll("</?html>", "");
        if (cachedGlyphs.containsKey(glyphName)) {
            return cachedGlyphs.get(glyphName);
        }
        renderer.setText(glyph);
        Dimension dimension = renderer.getPreferredSize();
        BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();
        renderer.setSize(dimension);
        renderer.paint(graphics);
        cachedGlyphs.put(glyphName, bufferedImage);
        return bufferedImage;
    }

}
