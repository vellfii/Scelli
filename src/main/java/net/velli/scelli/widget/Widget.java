package net.velli.scelli.widget;

import net.minecraft.client.gui.DrawContext;

public abstract class Widget<T extends Widget<T>> {
    protected final WidgetPos position = new WidgetPos();

    private long lastRender = System.currentTimeMillis();
    protected abstract void render(DrawContext context, float mouseX, float mouseY, float delta);
    public void render(DrawContext context, float mouseX, float mouseY) {
        float delta = System.currentTimeMillis() - lastRender;
        render(context, mouseX, mouseY, delta);
        lastRender = System.currentTimeMillis();
    }

    public float x() { return position.x; }
    public float y() { return position.y; }
    public float renderedX() { return position.renderX; }
    public float renderedY() { return position.renderY; }
    public float width() { return position.width; }
    public float height() { return position.height; }
    public int renderedWidth() { return position.renderWidth; }
    public int renderedHeight() { return position.renderHeight; }

    public abstract T getThis();

    public T withPosition(float x, float y, boolean snap) {
        this.position.x = x;
        this.position.y = y;
        if (snap) {
            this.position.x = x;
            this.position.y = y;
        }
        return getThis();
    }

    public T withDimensions(int width, int height, boolean snap) {
        this.position.width = width;
        this.position.height = height;
        if (snap) {
            this.position.renderWidth = width;
            this.position.renderHeight = height;
        }
        return getThis();
    }

    public boolean isHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX > 0 && mouseX <= width();
        boolean inBoundsY = mouseY > 0 && mouseY <= height();
        return inBoundsX && inBoundsY;
    }
}
