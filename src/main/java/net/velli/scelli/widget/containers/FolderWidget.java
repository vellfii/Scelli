package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.WidgetPos;

import java.util.concurrent.atomic.AtomicInteger;

public class FolderWidget extends BasicContainer<FolderWidget> {
    protected int verticalPadding = 10, horizontalPadding = 10, itemPadding = 2;

    public static FolderWidget create() {
        return new FolderWidget();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        //System.out.println("we made it to the start of the render method");
        context.fill(0, 0, Math.round(renderedWidth()), Math.round(renderedHeight()), (0xFF000000 & 0x00FFFFFF) | (Math.round(102f / 255 * opacity) << 24));
        AtomicInteger currentHeight = new AtomicInteger(verticalPadding);
        getWidgets().forEach(widget -> {
            currentHeight.set(currentHeight.get() + widget.height() + itemPadding);
            widget.position().alignment = WidgetPos.Alignment.TOPLEFT;
            widget.position().x = horizontalPadding;
            widget.position().renderX = horizontalPadding;
            widget.position().y = currentHeight.get();
            widget.position().renderY = currentHeight.get();
            widget.position().width = width() - horizontalPadding * 2;
            widget.position().renderWidth = width() - horizontalPadding * 2;
        });
        renderWidgets(context, mouseX, mouseY, opacity);
    }

    @Override
    public FolderWidget getThis() {
        return this;
    }

    public FolderWidget withPadding( int verticalPadding, int horizontalPadding, int itemPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.itemPadding = itemPadding;
        return this;
    }
}
