package com.cemi.engine.render;

import org.lwjgl.opengl.GL30;

import com.cemi.engine.system.FileUtils;

public class Shader {
    protected int id;
    private final String path;

    public static final Shader DEFAULT = new Shader("builtin/default");
    public static final Shader UI_SHADER = new Shader("builtin/uiShader");

    public Shader(String path) {
        this.path = path;
    }

    public void init() {
        id = FileUtils.createShader(path);
    }

    public void bind() {
        GL30.glUseProgram(id);
    }

    public void unbind() {
        GL30.glUseProgram(0);
    }

    public void cleanup() {
        GL30.glDeleteProgram(id);
    }

    public int getID() {
        return id;
    }
}
