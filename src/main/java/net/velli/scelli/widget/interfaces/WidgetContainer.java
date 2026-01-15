package net.velli.scelli.widget.interfaces;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.widgets.Widget;
import net.velli.scelli.widget.widgets.WidgetPos;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public interface WidgetContainer<T extends WidgetContainer<T>> extends ClickableWidget {
    int x();
    int y();
    int width();
    int height();
    int opacity();
    List<Widget<?>> getWidgets();
    T getWidget();

    T addWidgets(Widget<?>... widgets);
    void removeWidget(Widget<?> widget);

    default void renderChildren(DrawContext context, int mouseX, int mouseY) {
        for (Widget<?> widget : getWidgets()) {
            Vector2i offset = alignmentOffset(widget);
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(widget.x() + offset.x(), widget.y() + offset.y());
            widget.render(context, mouseX, mouseY);
            context.getMatrices().popMatrix();
        }
    }

    static Vector2i alignmentOffset(Widget<?> widget) {
        if (widget.parent == null) return new Vector2i(0, 0);
        WidgetPos.Alignment alignment = widget.alignment();
        int x = 0, y = 0;
        if (!alignment.id().contains("left")) {
            if (alignment.id().contains("right")) x = widget.parent.width() - widget.width();
            else x = widget.parent.width() / 2 - widget.width() / 2;
        }
        if (!alignment.id().contains("top")) {
            if (alignment.id().contains("bottom")) y = widget.parent.height() - widget.height();
            else y = widget.parent.height() / 2 - widget.height() / 2;
        }
        return new Vector2i(x, y);
    }

    default void hoverChildren(int mouseX, int mouseY, boolean active) {
        for (Widget<?> widget : getWidgets()) {
            Vector2i offset = alignmentOffset(widget);
            widget.hover(mouseX - offset.x(), mouseY - offset.y(), active);
        }
    }

    default void onClick(int mouseX, int mouseY) {
        clickChildren(mouseX, mouseY);
    }

    default void clickChildren(int mouseX, int mouseY) {
        for (Widget<?> widget : getWidgets()) {
            Vector2i offset = alignmentOffset(widget);
            if (widget instanceof ClickableWidget cw) cw.onClick(mouseX, mouseY);
        }
    }

    default void onRelease(int mouseX, int mouseY) {
        releaseChildren(mouseX, mouseY);
    }

    default void releaseChildren(int mouseX, int mouseY) {
        for (Widget<?> widget : getWidgets()) {
            Vector2i offset = alignmentOffset(widget);
            if (widget instanceof ClickableWidget cw) cw.onRelease(mouseX + offset.x(), mouseY + offset.y());
        }
    }
}
