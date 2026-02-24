package net.velli.scelli.widget.interfaces;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import net.velli.scelli.widget.widgets.Alignment;
import net.velli.scelli.widget.widgets.Widget;
import net.velli.scelli.widget.widgets.WidgetPos;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public interface WidgetContainer<T extends WidgetContainer<T>> extends ClickableWidget, ScrollableWidget, TypableWidget {
    int x();
    int y();
    int width();
    int height();
    int opacity();
    List<Widget<?>> getWidgets();
    T getWidget();

    T addWidgets(Widget<?>... widgets);
    void removeWidget(Widget<?> widget);
    void clearWidgets();

    default void renderChildren(DrawContext context, int mouseX, int mouseY) {
        for (Widget<?> widget : getWidgets()) {
            renderWidget(widget, context, mouseX, mouseY);
        }
    }

    default void renderWidget(Widget<?> widget, DrawContext context, int mouseX, int mouseY) {
        Vector2i offset = alignmentOffset(widget);
        context.getMatrices().pushMatrix();
        context.enableScissor(0, 0, width(), height());
        context.getMatrices().translate(widget.x() + offset.x(), widget.y() + offset.y());
        widget.render(context, mouseX, mouseY);
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    static Vector2i alignmentOffset(Widget<?> widget) {
        if (widget.parent == null) return new Vector2i(0, 0);
        Alignment alignment = widget.alignment();
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
            widget.hover(mouseX - offset.x() - x(), mouseY - offset.y() - y(), active);
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

    default void onScroll(int amount) {
        scrollChildren(amount);
    }

    default void scrollChildren(int amount) {
        for (Widget<?> widget : getWidgets()) {
            if (widget instanceof ScrollableWidget sw) sw.onScroll(amount);
        }
    }

    default void onType(char chr) { typeChildren(chr); }

    default void typeChildren(char chr) {
        for (Widget<?> widget : getWidgets()) {
            if (widget instanceof TypableWidget sw) sw.onType(chr);
        }
    }

    default void onKeyPressed(int keyCode, int modifiers) {
        keyPressChildren(keyCode, modifiers);
    }

    default void keyPressChildren(int keyCode, int modifiers) {
        for (Widget<?> widget : getWidgets()) {
            if (widget instanceof TypableWidget sw) sw.onKeyPressed(keyCode, modifiers);
        }
    }
}
