package com.cemi.engine.render;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class GraphicsUtil {
    public static ByteBuffer createBufferFromImage(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
                image.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() *
                image.getHeight() * 4);

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                int pixel = pixels[h * image.getWidth() + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        // TODO check if 100% correct
        buffer.flip();
        return buffer;
    }
}
