package net.velli.scelli.widget;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.ScelliUtil;

public abstract class Widget<T extends Widget<T>> {
    protected final WidgetPos position = new WidgetPos();

    private long lastRender = System.currentTimeMillis();
    protected abstract void render(DrawContext context, float mouseX, float mouseY, float delta);
    public void hover(float mouseX, float mouseY, boolean active) {
        this.position.hovered = isHovered(mouseX, mouseY) && active;
    }
    public void render(DrawContext context, float mouseX, float mouseY) {
        float delta = (float) (System.currentTimeMillis() - lastRender) / 1000;
        render(context, mouseX, mouseY, delta);
        position.renderX = ScelliUtil.lerp(renderedX(), x(), 16f * delta);
        position.renderY = ScelliUtil.lerp(renderedY(), y(), 16f * delta);
        position.renderWidth = ScelliUtil.lerp(renderedWidth(), width(), 16f * delta);
        position.renderHeight = ScelliUtil.lerp(renderedHeight(), height(), 16f * delta);
        lastRender = System.currentTimeMillis();
    }

    public WidgetPos position() { return position; }
    public float x() { return position.x; }
    public float y() { return position.y; }
    public float renderedX() { return position.renderX; }
    public float renderedY() { return position.renderY; }
    public int width() { return position.width; }
    public int height() { return position.height; }
    public float renderedWidth() { return position.renderWidth; }
    public float renderedHeight() { return position.renderHeight; }
    public boolean hovered() { return position.hovered; }
    public WidgetPos.Alignment alignment() { return position.alignment; }

    public abstract T getThis();

    public T withPosition(float x, float y, boolean snap) {
        this.position.x = x;
        this.position.y = y;
        if (snap) {
            this.position.renderX = x;
            this.position.renderY = y;
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

    public T withAlignment(WidgetPos.Alignment alignment) {
        this.position.alignment = alignment;
        return getThis();
    }

    public boolean isHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX >= 0 && mouseX < renderedWidth();
        boolean inBoundsY = mouseY >= 0 && mouseY < renderedHeight();
        return inBoundsX && inBoundsY;
    }
}
