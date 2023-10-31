package com.cemi.engine.system;

import org.lwjgl.glfw.GLFW;

import com.cemi.engine.Engine;

public class Settings {
    private static int width = 1280;
    private static int height = 720;
    private static String title = "Default Title";
    private static boolean debug = false;

    public static void setWidth(int width) {
        Settings.width = width;
        if (Engine.isInitialized()) {
            GLFW.glfwSetWindowSize(Engine.getWindow(), width, height);
        }
    }

    public static void setHeight(int height) {
        Settings.height = height;
        if (Engine.isInitialized()) {
            GLFW.glfwSetWindowSize(Engine.getWindow(), width, height);
        }
    }

    public static void setTitle(String title) {
        Settings.title = title;
        if (Engine.isInitialized()) {
            GLFW.glfwSetWindowTitle(Engine.getWindow(), title);
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static String getTitle() {
        return title;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Settings.debug = debug;
    }
}
