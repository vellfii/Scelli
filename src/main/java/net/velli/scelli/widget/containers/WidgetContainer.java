package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.Widget;
import net.velli.scelli.widget.interfaces.ClickableWidget;

import java.util.List;

public interface WidgetContainer extends ClickableWidget {
    List<Widget<?>> getWidgets();
    default void addWidget(Widget<?> widget) { getWidgets().add(widget); }
    default void addWidgets(List<Widget<?>> widgets) { getWidgets().addAll(widgets); }
    default void addWidgets(Widget<?>... widgets) { getWidgets().addAll(List.of(widgets)); }
    default void removeWidget(Widget<?> widget) { getWidgets().remove(widget); }
    default void removeWidget(int index) { getWidgets().remove(index); }
    default void clearWidgets() { getWidgets().clear(); }

    default void renderWidgets(DrawContext context, float mouseX, float mouseY) {
        getWidgets().forEach(widget -> {
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(widget.renderedX(), widget.renderedY());
            context.enableScissor(0, 0, widget.renderedWidth(), widget.renderedHeight());
            widget.render(context, mouseX, mouseY);
            context.disableScissor();
            context.getMatrices().popMatrix();
        });
    }
}
