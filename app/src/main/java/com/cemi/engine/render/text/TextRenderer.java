package com.cemi.engine.render.text;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JButton;

public class TextRenderer {

    private static final JLabel renderer = new JLabel();

    static {
        renderer.setFont(new Font("Serif", Font.PLAIN, 48));
    }

    private static final HashMap<String, BufferedImage> cachedGlyphs = new HashMap<>();

    public static BufferedImage getRenderedGlyph(String glyph) {
        glyph = "<html>" + glyph.replaceAll("</?html>", "") + "</html>";
        String glyphName = glyph.replaceAll("</?html>", "");
        if (cachedGlyphs.containsKey(glyphName)) {
            return cachedGlyphs.get(glyphName);
        }
        renderer.setText(glyph);
        Dimension dimension = renderer.getPreferredSize();
        if (dimension.getWidth() == 0) {
            dimension.width = 12;
        }
        if (dimension.getHeight() == 0) {
            dimension.height = 12;
        }
        BufferedImage bufferedImage =
                new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();
        renderer.setSize(dimension);
        renderer.paint(graphics);
        cachedGlyphs.put(glyphName, bufferedImage);
        return bufferedImage;
    }

    public static BufferedImage getRenderedText(String text) {
        // seperate the text into glyphs using regex <(.*)>(.*)</\1>
        // then render each glyph and combine them into one image
        text = text.replaceAll("</?html>", "");
        String[] glyphs = Pattern.compile("(<([^>]*)>([^<>]*)<\\/\\2>)|.").matcher(text).results()
                .map(MatchResult::group).toArray(String[]::new);
        BufferedImage[] glyphImages = new BufferedImage[glyphs.length];
        int width = 0;
        int height = 0;
        for (int i = 0; i < glyphs.length; i++) {
            glyphImages[i] = getRenderedGlyph(glyphs[i]);
            width += glyphImages[i].getWidth();
            height = Math.max(height, glyphImages[i].getHeight());
        }
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();
        int x = 0;
        for (BufferedImage glyphImage : glyphImages) {
            graphics.drawImage(glyphImage, x, height - glyphImage.getHeight(), null);
            x += glyphImage.getWidth();
        }
        return bufferedImage;
    }

}
