package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.Widget;
import net.velli.scelli.widget.interfaces.ClickableWidget;

import java.util.ArrayList;
import java.util.List;

public class BasicContainer<T extends BasicContainer<T>>
        extends Widget<BasicContainer<T>>
        implements WidgetContainer {
    protected List<Widget<?>> widgets = new ArrayList<>();

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, float delta) {
        this.renderWidgets(context, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withPosition(float x, float y, boolean snap) {
        super.withPosition(x, y, snap);
        return (T) getThis();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withDimensions(int width, int height, boolean snap) {
        super.withDimensions(width, height, snap);
        return (T) getThis();
    }

    public BasicContainer<T> getThis() {
        return this;
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    public void BCTestMethod() {

    }

    @Override
    public void onClick(float mouseX, float mouseY, boolean active) {
        getWidgets().forEach(widget -> {
            if (widget instanceof ClickableWidget cw) {
                boolean hovered = widget.isHovered(mouseX, mouseY) && active;
                cw.onClick(mouseX, mouseY, hovered);
            }
        });
    }

    @Override
    public void onRelease(float mouseX, float mouseY, boolean active) {
        getWidgets().forEach(widget -> {
            if (widget instanceof ClickableWidget cw) {
                boolean hovered = widget.isHovered(mouseX, mouseY) && active;
                cw.onRelease(mouseX, mouseY, hovered);
            }
        });
    }
}
