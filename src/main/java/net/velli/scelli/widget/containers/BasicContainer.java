package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.Widget;
import net.velli.scelli.widget.interfaces.ClickableWidget;

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
    protected void render(DrawContext context, float mouseX, float mouseY, float delta) {
        this.renderWidgets(context, mouseX, mouseY);
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

    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    public void BCTestMethod() {

    }
}
