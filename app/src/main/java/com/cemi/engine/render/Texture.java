package com.cemi.engine.render;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
    int textureID;

    public Texture(String path) throws IOException {
        textureID = GL30.glGenTextures();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureID);
        PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(path));
        ByteBuffer data = ByteBuffer.allocateDirect(
                4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(data, decoder.getWidth() * 4, Format.RGBA);
        data.flip();
        GL30.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        GL30.glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL30.GL_RGBA,
                GL30.GL_UNSIGNED_BYTE, data);
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public int getTextureID() {
        return textureID;
    }
}
