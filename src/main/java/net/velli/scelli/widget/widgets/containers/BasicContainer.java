package net.velli.scelli.widget.widgets.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.WidgetContainer;
import net.velli.scelli.widget.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

public class BasicContainer<T extends BasicContainer<T>>
        extends Widget<BasicContainer<T>>
        implements WidgetContainer<T> {
    protected List<Widget<?>> widgets = new ArrayList<>();

    public static BasicContainer<?> create() {
        return new BasicContainer<>();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        this.renderWidgets(context, mouseX, mouseY, Math.round((float) opacity() / 255 * opacity));
    }

    @Override
    public void hover(float mouseX, float mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        this.hoverWidgets(mouseX, mouseY, active);
    }

    @Override
    public T withPosition(float x, float y, boolean snap) {
        super.withPosition(x, y, snap);
        return getThis();
    }

    @Override
    public T withDimensions(int width, int height, boolean snap) {
        super.withDimensions(width, height, snap);
        return getThis();
    }

    @Override
    public BasicContainer<T> withOpacity(int opacity, boolean snap) {
        super.withOpacity(opacity, snap);
        return getThis();
    }

    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

    @Override
    public int opacity() {
        return position.opacity;
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }
}
