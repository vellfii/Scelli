package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.Widget;
import net.velli.scelli.widget.interfaces.ClickableWidget;
import net.velli.scelli.widget.interfaces.ScrollableWidget;
import org.joml.Vector2f;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public interface WidgetContainer<T extends WidgetContainer<T>> extends ClickableWidget, ScrollableWidget {
    float x();
    float renderedX();
    float y();
    float renderedY();
    int width();
    float renderedWidth();
    int height();
    float renderedHeight();

    List<Widget<?>> getWidgets();
    default T addWidget(Widget<?> widget) { getWidgets().add(widget); return getThis(); }
    default T addWidgets(List<Widget<?>> widgets) { getWidgets().addAll(widgets); return getThis(); }
    default T addWidgets(Widget<?>... widgets) { getWidgets().addAll(List.of(widgets)); return getThis(); }
    default T setWidgets(List<Widget<?>> widgets) {
        clearWidgets();
        addWidgets(widgets);
        return getThis();
    }
    default void removeWidget(Widget<?> widget) { getWidgets().remove(widget); }
    default void removeWidget(int index) { getWidgets().remove(index); }
    default void clearWidgets() { getWidgets().clear(); }
    T getThis();

    default void renderWidgets(DrawContext context, float mouseX, float mouseY) {
        getWidgets().forEach(widget -> {
            context.getMatrices().pushMatrix();
            Vector2f alignmentOffsets = widget.position().alignmentOffsets(this);
            context.getMatrices().translate(Math.round(alignmentOffsets.x), Math.round(alignmentOffsets.y));
            context.getMatrices().translate(Math.round(widget.renderedX()), Math.round(widget.renderedY()));
            context.enableScissor(0, 0, Math.round(widget.renderedWidth()), Math.round(widget.renderedHeight()));
            widget.render(context, mouseX - widget.renderedX() - alignmentOffsets.x, mouseY - widget.renderedY() - alignmentOffsets.y);
            context.disableScissor();
            context.getMatrices().popMatrix();
        });
    }

    default void hoverWidgets(float mouseX, float mouseY, boolean active) {
        getWidgets().forEach(widget -> {
            boolean hovered = isHovered(mouseX, mouseY) && active;
            Vector2f alignmentOffsets = widget.position().alignmentOffsets(this);
            widget.hover(mouseX - widget.renderedX() - alignmentOffsets.x,
                    mouseY - widget.renderedY() - alignmentOffsets.y,
                    hovered);
        });
    }

    default boolean isHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX > 0 && mouseX <= width();
        boolean inBoundsY = mouseY > 0 && mouseY <= height();
        return inBoundsX && inBoundsY;
    }

    default boolean onScroll(double amount) {
        AtomicBoolean status = new AtomicBoolean(false);
        getWidgets().forEach(widget -> {
            if (widget instanceof ScrollableWidget sc && widget.hovered()) {
                status.set(sc.onScroll(amount));
            }
        });
        return status.get();
    }

    default void onClick(float mouseX, float mouseY, boolean active) {
        getWidgets().forEach(widget -> {
            if (widget instanceof ClickableWidget cw) {
                boolean hovered = isHovered(mouseX, mouseY) && active;
                Vector2f alignmentOffsets = widget.position().alignmentOffsets(this);
                cw.onClick(mouseX - widget.x() - alignmentOffsets.x,
                        mouseY - widget.y() - alignmentOffsets.y,
                        hovered);
            }
        });
    }

    default void onRelease(float mouseX, float mouseY, boolean active) {
        getWidgets().forEach(widget -> {
            if (widget instanceof ClickableWidget cw) {
                boolean hovered = isHovered(mouseX, mouseY) && active;
                Vector2f alignmentOffsets = widget.position().alignmentOffsets(this);
                cw.onRelease(mouseX - widget.x() - alignmentOffsets.x,
                        mouseY - widget.y() - alignmentOffsets.y,
                        hovered);
            }
        });
    }
}
