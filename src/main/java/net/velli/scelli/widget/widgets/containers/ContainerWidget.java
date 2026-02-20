package net.velli.scelli.widget.widgets.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.WidgetContainer;
import net.velli.scelli.widget.widgets.Alignment;
import net.velli.scelli.widget.widgets.Widget;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ContainerWidget<T extends ContainerWidget<T>> extends Widget<ContainerWidget<T>> implements WidgetContainer<ContainerWidget<T>> {
    private final List<Widget<?>> widgets = new CopyOnWriteArrayList<>();

    @Override
    public abstract T getWidget();

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    @Override
    public T addWidgets(Widget<?>... widgets) {
        for (Widget<?> widget : widgets) {
            if (widget.parent != null) widget.parent.removeWidget(widget);
            widget.parent = getWidget();
            this.widgets.add(widget);
        }
        return getWidget();
    }

    @Override
    public void removeWidget(Widget<?> widget) {
        if (getWidgets().contains(widget)) {
            widgets.remove(widget);
            widget.parent = null;
        }
    }

    @Override
    public void clearWidgets() {
        for (Widget<?> widget : getWidgets()) {
            widgets.remove(widget);
            widget.parent = null;
        }
    }

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        renderChildren(context, mouseX, mouseY);
    }

    @Override
    public void hover(int mouseX, int mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        hoverChildren(mouseX, mouseY, isHovered());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withPosition(int x, int y, boolean snap) {
        return (T) super.withPosition(x, y, snap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withDimensions(int width, int height, boolean snap) {
        return (T) super.withDimensions(width, height, snap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T withAlignment(Alignment alignment) {
        return (T) super.withAlignment(alignment);
    }
}
