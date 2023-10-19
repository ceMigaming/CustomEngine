package com.cemi.engine;

import java.util.HashMap;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {
    static Vector2d deltaMouse = new Vector2d(0, 0);
    static Vector2d lastDeltaMouse = new Vector2d(0, 0);
    static Vector2d currentMousePos = new Vector2d(0, 0);
    static Vector2d lastMousePos = new Vector2d(0, 0);
    static Vector2d deltaScroll = new Vector2d(0, 0);

    static HashMap<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
    static HashMap<Integer, Boolean> keyHeldMap = new HashMap<Integer, Boolean>();
    static HashMap<Integer, Boolean> isKeyDownMap = new HashMap<Integer, Boolean>();
    static HashMap<Integer, Boolean> isKeyUpMap = new HashMap<Integer, Boolean>();

    public static void init(long window) {
        GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xPos, double yPos) {
                lastDeltaMouse.x = deltaScroll.x;
                lastDeltaMouse.y = deltaScroll.y;
                currentMousePos.x = xPos;
                currentMousePos.y = yPos;
                deltaMouse.x = currentMousePos.x - lastMousePos.x;
                deltaMouse.y = currentMousePos.y - lastMousePos.y;
                lastMousePos.x = xPos;
                lastMousePos.y = yPos;
            }
        };
        GLFW.glfwSetCursorPosCallback(window, cursorPosCallback);

        GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xOffset, double yOffset) {
                deltaScroll.x = xOffset;
                deltaScroll.y = yOffset;
            }
        };
        GLFW.glfwSetScrollCallback(window, scrollCallback);

        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS && keyHeldMap.getOrDefault(key, false) == false) {
                    keyHeldMap.put(key, true);
                    isKeyDownMap.put(key, true);
                }
                if (action == GLFW.GLFW_RELEASE && keyHeldMap.getOrDefault(key, true) == true) {
                    keyHeldMap.put(key, false);
                    isKeyUpMap.put(key, true);
                }
                keyMap.put(key, action);
            }
        };
        GLFW.glfwSetKeyCallback(window, keyCallback);
    }

    public static void update() {
        
    }

    public static Vector2d getDeltaMouse() {
        System.out.println(deltaMouse);
        System.out.println(lastDeltaMouse);
        if(deltaMouse.x == lastDeltaMouse.x && deltaMouse.y == lastDeltaMouse.y)
            return new Vector2d(0, 0);
        else
            return deltaMouse;
    }

    public static Vector2d getDeltaScroll() {
        return deltaScroll;
    }

    public static Vector2d getCurrentMousePos() {
        return currentMousePos;
    }

    public static void getLastMousePos(Vector2d lastMousePos) {
        lastMousePos.x = Input.lastMousePos.x;
        lastMousePos.y = Input.lastMousePos.y;
    }

    public static boolean isKeyDown(int key) {
        if (isKeyDownMap.getOrDefault(key, false) == true) {
            if (keyMap.getOrDefault(key, GLFW.GLFW_RELEASE) == GLFW.GLFW_PRESS) {
                isKeyDownMap.put(key, false);
                return true;
            }
        }
        return false;
    }

    public static boolean isKeyUp(int key) {
        if(isKeyUpMap.getOrDefault(key, false) == true) {
            if (keyMap.getOrDefault(key, GLFW.GLFW_RELEASE) == GLFW.GLFW_RELEASE) {
                isKeyUpMap.put(key, false);
                return true;
            }
        }
        return false;
    }

    public static boolean isKeyPressed(int key) {
        Integer v = keyMap.getOrDefault(key, GLFW.GLFW_RELEASE);
        return v == GLFW.GLFW_PRESS || v == GLFW.GLFW_REPEAT;
    }
}
