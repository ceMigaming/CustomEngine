package com.cemi.engine.math;

import com.cemi.engine.render.Renderer;
import com.cemi.engine.render.ui.UIRenderer;

public class GameObject {

    private String name;

    private Transform transform = new Transform();

    private boolean shouldRender = true;

    private boolean active = true;

    private Renderer renderer;

    private UIRenderer uiRenderer;

    public GameObject() {
        name = "gameobject";
        renderer = new Renderer("default");
        init();
    }

    public <T extends Renderer> GameObject(T renderer) {
        name = "gameobject";
        this.renderer = renderer;
        init();
    }

    public <T extends Renderer> GameObject(String name, T renderer) {
        this.name = name;
        this.renderer = renderer;
        init();
    }

    public GameObject(String name, UIRenderer renderer) {
        this.name = name;
        this.uiRenderer = renderer;
        init();
    }

    public void callUpdate() {
        update();
    }

    public void callRender() {
        render();
    }

    protected void init() {

    }

    protected void update() {

    }

    protected void render() {
        if (uiRenderer != null)
            uiRenderer.render(this);
        else if (renderer != null)
            renderer.render(this);
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public boolean shouldRender() {
        return shouldRender;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
