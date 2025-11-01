package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;

import java.util.concurrent.atomic.AtomicInteger;

public class ListWidget extends BasicContainer<ListWidget> {
    protected int verticalPadding, horizontalPadding, itemPadding;



    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, float delta) {
        context.fill(0, 0, renderedWidth(), renderedHeight(), 0x66000000);
        AtomicInteger currentHeight = new AtomicInteger(0);
        getWidgets().forEach(widget -> {

        });
    }

    @Override
    public ListWidget getThis() {
        return this;
    }

    public void LWTestMethod() {

    }
}
