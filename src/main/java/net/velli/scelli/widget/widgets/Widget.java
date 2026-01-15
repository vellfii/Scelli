package net.velli.scelli.widget.widgets;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.WidgetContainer;

public abstract class Widget<T extends Widget<T>> {

    private final WidgetPos pos = new WidgetPos();
    public WidgetContainer<?> parent;

    protected boolean hovered = false;

    private long lastRender = System.currentTimeMillis();
    public abstract void renderMain(DrawContext context, int mouseX, int mouseY, float delta);
    public void render(DrawContext context, int mouseX, int mouseY) {
        float delta = (float) (System.currentTimeMillis() - lastRender) / 1000;
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
    public WidgetPos.Alignment alignment() { return pos.alignment; }

    public T withPosition(int x, int y) {
        pos.x = x;
        pos.y = y;
        return getWidget();
    }

    public T withDimensions(int width, int height) {
        pos.width = width;
        pos.height = height;
        return getWidget();
    }

    public T withAlignment(WidgetPos.Alignment alignment) {
        pos.alignment = alignment;
        return getWidget();
    }

    public boolean isHovered() {
        return hovered;
    }
}
