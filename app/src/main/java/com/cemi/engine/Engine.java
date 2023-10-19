package com.cemi.engine;

import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.cemi.engine.render.Renderer;
import com.cemi.rogue.spores.render.MeshRenderer;

public class Engine {

    private static long window;

    private static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    private static Boolean isInitialized = false;

    private static void init() {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {
                ex.printStackTrace();
                Callbacks.glfwFreeCallbacks(window);
                GLFW.glfwDestroyWindow(window);

                GLFW.glfwTerminate();
                GLFW.glfwSetErrorCallback(null).free();
                System.exit(0);
            }
        });

        initGLFW();

        for (GameObject gameObject : gameObjects) {
            Renderer renderer = gameObject.getRenderer();
            if (renderer != null) {
                if (renderer instanceof MeshRenderer) {
                    ((MeshRenderer) renderer).getMesh().init();
                }
                renderer.getShader().init();
            }
        }
        isInitialized = true;
    }

    public static void run() {
        init();
        loop();
        cleanup();

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        System.exit(0);
    }

    private static void initGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(Settings.getWidth(), Settings.getHeight(), Settings.getTitle(), MemoryUtil.NULL,
                MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true);
        });

        Input.init(window);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        LocalizationManager.init();

        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
        GLUtil.setupDebugMessageCallback();

        GL30.glClearColor(0.17f, 0.18f, 0.20f, 0.0f);
        GL30.glEnable(GL30.GL_DEPTH_TEST);
    }

    private static void loop() {

        // float pitch = (float) Math.PI / 4.f, yaw = 0, roll = (float) Math.PI / 4.f,
        // scale = 1.f;

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

            Time.update();
            Input.update();

            for (GameObject gameObject : gameObjects) {
                if (gameObject.isActive()) {
                    gameObject.callUpdate();
                    if (gameObject.shouldRender())
                        gameObject.callRender();
                }
            }

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }
    }

    private static void cleanup() {
        for (GameObject gameObject : gameObjects) {
            Renderer renderer = gameObject.getRenderer();
            if (renderer != null) {
                if (renderer instanceof MeshRenderer)
                    ((MeshRenderer) renderer).getMesh().cleanup();
                renderer.getShader().cleanup();
            }
        }
    }

    public static void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public static void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public static void removeAllGameObjects() {
        gameObjects.clear();
    }

    public static void setWindowShouldClose() {
        GLFW.glfwSetWindowShouldClose(window, true);
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

    public static long getWindow() {
        return window;
    }
}