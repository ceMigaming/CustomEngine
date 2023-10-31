package com.cemi.engine.system;

public class Time {
    private static double lastTime = 0;
    private static double deltaTime = 0;

    public static void update() {
        double currentTime = System.nanoTime() / 1000000000.0;
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getFPS() {
        return 1.0 / deltaTime;
    }

    public static double getTime() {
        return System.nanoTime() / 1000000000.0;
    }
}
