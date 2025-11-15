package net.velli.scelli.widget;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.ScelliUtil;

public abstract class Widget<T extends Widget<T>> {
    protected final WidgetPos position = new WidgetPos();

    private long lastRender = System.currentTimeMillis();
    protected abstract void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta);
    public void hover(float mouseX, float mouseY, boolean active) {
        this.position.hovered = isHovered(mouseX, mouseY) && active;
    }
    public void render(DrawContext context, float mouseX, float mouseY, int opacity) {
        float delta = (float) (System.currentTimeMillis() - lastRender) / 1000;
        opacity = Math.round(position.renderOpacity / 255 * opacity);
        render(context, mouseX, mouseY, opacity, delta);
        float t = 16f * delta * animationSpeed();
        position.renderX = ScelliUtil.lerp(renderedX(), x(), t);
        position.renderY = ScelliUtil.lerp(renderedY(), y(), t);
        position.renderWidth = ScelliUtil.lerp(renderedWidth(), width(), t);
        position.renderHeight = ScelliUtil.lerp(renderedHeight(), height(), t);
        position.renderOpacity = ScelliUtil.lerp(renderedOpacity(), opacity(), t);
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
    public int opacity() { return position.opacity; }
    public float renderedOpacity() { return position.renderOpacity; }
    public boolean hovered() { return position.hovered; }
    public float animationSpeed() { return position.animationSpeed; }
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

    public T withOpacity(int opacity, boolean snap) {
        this.position.opacity = Math.clamp(opacity, 0, 255);
        if (snap) this.position.renderOpacity = Math.clamp(opacity, 0, 255);
        return getThis();
    }

    public T withAlignment(WidgetPos.Alignment alignment) {
        this.position.alignment = alignment;
        return getThis();
    }

    public T withAnimationSpeed(float speed) {
        this.position.animationSpeed = Math.clamp(speed, 0, 100);
        return getThis();
    }

    public boolean isHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX >= 0 && mouseX < renderedWidth();
        boolean inBoundsY = mouseY >= 0 && mouseY < renderedHeight();
        return inBoundsX && inBoundsY;
    }

    public static int stackOpacity(int color, int opacity) {
        return (color & 0x00FFFFFF) | (Math.round((float) ((color >> 24) & 0xFF) / 255 * opacity) << 24);
    }
}
