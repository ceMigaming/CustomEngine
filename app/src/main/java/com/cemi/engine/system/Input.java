package com.cemi.engine.system;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_MIDDLE;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_BACKSPACE;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_COPY;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_CUT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_DEL;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_DOWN;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_ENTER;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_PASTE;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_DOWN;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_UP;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SHIFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TAB;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_LINE_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_LINE_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_REDO;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_UNDO;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_WORD_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_WORD_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_UP;
import static org.lwjgl.nuklear.Nuklear.nk_input_button;
import static org.lwjgl.nuklear.Nuklear.nk_input_key;
import static org.lwjgl.nuklear.Nuklear.nk_input_motion;
import static org.lwjgl.nuklear.Nuklear.nk_input_scroll;
import static org.lwjgl.nuklear.Nuklear.nk_input_unicode;

import java.util.HashMap;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.nuklear.NkVec2;
import org.lwjgl.system.MemoryStack;

import com.cemi.engine.Engine;

public class Input {
    static Vector2d deltaMouse = new Vector2d(0, 0);
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
                currentMousePos.x = xPos;
                currentMousePos.y = yPos;
                nk_input_motion(Engine.getNkContext(), (int) xPos, (int) yPos);
            }
        };
        glfwSetCursorPosCallback(window, cursorPosCallback);

        GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xOffset, double yOffset) {
                deltaScroll.x = xOffset;
                deltaScroll.y = yOffset;
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    NkVec2 scroll = NkVec2.malloc(stack)
                            .x((float) xOffset)
                            .y((float) yOffset);
                    nk_input_scroll(Engine.getNkContext(), scroll);
                }
            }
        };
        glfwSetScrollCallback(window, scrollCallback);

        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS && keyHeldMap.getOrDefault(key, false) == false) {
                    keyHeldMap.put(key, true);
                    isKeyDownMap.put(key, true);
                }
                if (action == GLFW_RELEASE && keyHeldMap.getOrDefault(key, true) == true) {
                    keyHeldMap.put(key, false);
                    isKeyUpMap.put(key, true);
                }
                keyMap.put(key, action);
                boolean press = action == GLFW_PRESS;
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        // glfwSetWindowShouldClose(window, true);
                        break;
                    case GLFW_KEY_DELETE:
                        nk_input_key(Engine.getNkContext(), NK_KEY_DEL, press);
                        break;
                    case GLFW_KEY_ENTER:
                        nk_input_key(Engine.getNkContext(), NK_KEY_ENTER, press);
                        break;
                    case GLFW_KEY_TAB:
                        nk_input_key(Engine.getNkContext(), NK_KEY_TAB, press);
                        break;
                    case GLFW_KEY_BACKSPACE:
                        nk_input_key(Engine.getNkContext(), NK_KEY_BACKSPACE, press);
                        break;
                    case GLFW_KEY_UP:
                        nk_input_key(Engine.getNkContext(), NK_KEY_UP, press);
                        break;
                    case GLFW_KEY_DOWN:
                        nk_input_key(Engine.getNkContext(), NK_KEY_DOWN, press);
                        break;
                    case GLFW_KEY_HOME:
                        nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_START, press);
                        nk_input_key(Engine.getNkContext(), NK_KEY_SCROLL_START, press);
                        break;
                    case GLFW_KEY_END:
                        nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_END, press);
                        nk_input_key(Engine.getNkContext(), NK_KEY_SCROLL_END, press);
                        break;
                    case GLFW_KEY_PAGE_DOWN:
                        nk_input_key(Engine.getNkContext(), NK_KEY_SCROLL_DOWN, press);
                        break;
                    case GLFW_KEY_PAGE_UP:
                        nk_input_key(Engine.getNkContext(), NK_KEY_SCROLL_UP, press);
                        break;
                    case GLFW_KEY_LEFT_SHIFT:
                    case GLFW_KEY_RIGHT_SHIFT:
                        nk_input_key(Engine.getNkContext(), NK_KEY_SHIFT, press);
                        break;
                    case GLFW_KEY_LEFT_CONTROL:
                    case GLFW_KEY_RIGHT_CONTROL:
                        if (press) {
                            nk_input_key(Engine.getNkContext(), NK_KEY_COPY,
                                    glfwGetKey(window, GLFW_KEY_C) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_PASTE,
                                    glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_CUT,
                                    glfwGetKey(window, GLFW_KEY_X) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_UNDO,
                                    glfwGetKey(window, GLFW_KEY_Z) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_REDO,
                                    glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_WORD_LEFT,
                                    glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_WORD_RIGHT,
                                    glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_LINE_START,
                                    glfwGetKey(window, GLFW_KEY_B) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_TEXT_LINE_END,
                                    glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS);
                        } else {
                            nk_input_key(Engine.getNkContext(), NK_KEY_LEFT,
                                    glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_RIGHT,
                                    glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
                            nk_input_key(Engine.getNkContext(), NK_KEY_COPY, false);
                            nk_input_key(Engine.getNkContext(), NK_KEY_PASTE, false);
                            nk_input_key(Engine.getNkContext(), NK_KEY_CUT, false);
                            nk_input_key(Engine.getNkContext(), NK_KEY_SHIFT, false);
                        }
                        break;
                }
            }
        };
        glfwSetKeyCallback(window, keyCallback);

        glfwSetCharCallback(window, (win, codepoint) -> nk_input_unicode(Engine.getNkContext(), codepoint));

        glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
            try (MemoryStack stack = org.lwjgl.system.MemoryStack.stackPush()) {
                java.nio.DoubleBuffer cx = stack.mallocDouble(1);
                java.nio.DoubleBuffer cy = stack.mallocDouble(1);

                glfwGetCursorPos(window, cx, cy);

                int x = (int) cx.get(0);
                int y = (int) cy.get(0);

                int nkButton;
                switch (button) {
                    case GLFW_MOUSE_BUTTON_RIGHT:
                        nkButton = NK_BUTTON_RIGHT;
                        break;
                    case GLFW_MOUSE_BUTTON_MIDDLE:
                        nkButton = NK_BUTTON_MIDDLE;
                        break;
                    default:
                        nkButton = NK_BUTTON_LEFT;
                }
                nk_input_button(Engine.getNkContext(), nkButton, x, y, action == GLFW_PRESS);
            }
        });
    }

    public static void update() {
        deltaMouse.x = lastMousePos.x - currentMousePos.x;
        deltaMouse.y = lastMousePos.y - currentMousePos.y;
        lastMousePos.x = currentMousePos.x;
        lastMousePos.y = currentMousePos.y;
    }

    public static Vector2d getDeltaMouse() {
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
            if (keyMap.getOrDefault(key, GLFW_RELEASE) == GLFW_PRESS) {
                isKeyDownMap.put(key, false);
                return true;
            }
        }
        return false;
    }

    public static boolean isKeyUp(int key) {
        if (isKeyUpMap.getOrDefault(key, false) == true) {
            if (keyMap.getOrDefault(key, GLFW_RELEASE) == GLFW_RELEASE) {
                isKeyUpMap.put(key, false);
                return true;
            }
        }
        return false;
    }

    public static boolean isKeyPressed(int key) {
        Integer v = keyMap.getOrDefault(key, GLFW_RELEASE);
        return v == GLFW_PRESS || v == GLFW_REPEAT;
    }

}
