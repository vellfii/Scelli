package net.velli.scelli.widget.widgets.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.WidgetContainer;
import net.velli.scelli.widget.widgets.Widget;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerWidget extends Widget<ContainerWidget> implements WidgetContainer<ContainerWidget> {
    private final List<Widget<?>> widgets = new ArrayList<>();

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    @Override
    public ContainerWidget addWidgets(Widget<?>... widgets) {
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
    public void render(DrawContext context, int mouseX, int mouseY) {
        super.render(context, mouseX, mouseY);
        Vector2i offset = WidgetContainer.alignmentOffset(getWidget());
        context.enableScissor(
                x() + offset.x(),
                y() + offset.y(),
                x() + offset.x() + width(),
                y() + offset.y() + height()
        );
        renderChildren(context, mouseX, mouseY);
        context.disableScissor();
    }

    @Override
    public void hover(int mouseX, int mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        hoverChildren(mouseX, mouseY, isHovered());
    }

    @Override
    public ContainerWidget getWidget() {
        return this;
    }
}
