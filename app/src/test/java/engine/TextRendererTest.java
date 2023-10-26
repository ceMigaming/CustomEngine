package engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import com.cemi.engine.render.text.TextRenderer;

class TextRendererTest {

    @Test
    public void renderGlyphTest() {
        TextRenderer textRenderer = new TextRenderer();
        BufferedImage glyph = textRenderer.getRenderedGlyph("<html>a<sup>2</sup></html>");
        try {
            ImageIO.write(glyph, "png", new File(
                    "C:/Users/Maciek/Downloads/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("C:/Users/Maciek/Downloads/image.png");
        assertTrue(file.exists());
    }

    @Test
    public void renderStringTest() {
        TextRenderer textRenderer = new TextRenderer();
        BufferedImage glyph = textRenderer.getRenderedText("<html>a<sup>2</sup> + b<sup>2</sup> = c<sup>2</sup></html>");
        try {
            ImageIO.write(glyph, "png", new File(
                    "C:/Users/Maciek/Downloads/image2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("C:/Users/Maciek/Downloads/image2.png");
        assertTrue(file.exists());
    }

}
