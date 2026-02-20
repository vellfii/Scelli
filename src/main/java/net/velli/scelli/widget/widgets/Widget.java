package net.velli.scelli.widget.widgets;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.ScelliUtil;
import net.velli.scelli.widget.interfaces.WidgetContainer;

public abstract class Widget<T extends Widget<T>> {
    private final WidgetPos pos = new WidgetPos();
    public WidgetContainer<?> parent;

    protected boolean hovered = false;

    private long lastRender = System.currentTimeMillis();
    public abstract void renderMain(DrawContext context, int mouseX, int mouseY, float delta);
    public void render(DrawContext context, int mouseX, int mouseY) {
        float delta = (float) (System.currentTimeMillis() - lastRender) / 1000;
        if (pos.x != pos.targetX) {
            pos.subpixelX = ScelliUtil.lerp(pos.subpixelX, pos.targetX, 16f * delta);
            pos.x = Math.round(pos.subpixelX);
        }
        if (pos.y != pos.targetY) {
            pos.subpixelY = ScelliUtil.lerp(pos.subpixelY, pos.targetY, 16f * delta);
            pos.y = Math.round(pos.subpixelY);
        }
        if (pos.width != pos.targetWidth) {
            pos.subpixelWidth = ScelliUtil.lerp(pos.subpixelWidth, pos.targetWidth, 16f * delta);
            pos.width = Math.round(pos.subpixelWidth);
        }
        if (pos.height != pos.targetHeight) {
            pos.subpixelHeight = ScelliUtil.lerp(pos.subpixelHeight, pos.targetHeight, 16f * delta);
            pos.height = Math.round(pos.subpixelHeight);
        }
        renderMain(context, mouseX, mouseY, delta);
        lastRender = System.currentTimeMillis();
    }

    public void hover(int mouseX, int mouseY, boolean active) {
        hovered = active && pos.isHovered(mouseX, mouseY);
    }

    public abstract T getWidget();

    public int x() { return pos.x; }
    public int y() { return pos.y; }
    public int width() { return pos.width; }
    public int height() { return pos.height; }
    public int opacity() { return pos.opacity; }
    public Alignment alignment() { return pos.alignment; }

    public T withPosition(int x, int y, boolean snap) {
        pos.targetX = x;
        pos.targetY = y;
        if (snap) {
            pos.x = x;
            pos.y = y;
            pos.subpixelX = x;
            pos.subpixelY = y;
        }
        return getWidget();
    }

    public T withDimensions(int width, int height, boolean snap) {
        pos.targetWidth = width;
        pos.targetHeight = height;
        if (snap) {
            pos.width = width;
            pos.height = height;
            pos.subpixelWidth = width;
            pos.subpixelHeight = height;
        }
        return getWidget();
    }

    public T withAlignment(Alignment alignment) {
        pos.alignment = alignment;
        return getWidget();
    }

    public T withOpacity(int opacity, boolean snap) {
        pos.targetOpacity = opacity;
        if (snap) {
            pos.opacity = opacity;
            pos.subpixelOpacity = opacity;
        }
        return getWidget();
    }

    public boolean isHovered() {
        return hovered;
    }
}
