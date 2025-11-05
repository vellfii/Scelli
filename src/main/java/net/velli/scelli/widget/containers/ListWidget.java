package net.velli.scelli.widget.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.ScelliUtil;
import net.velli.scelli.ScreenHandler;
import net.velli.scelli.widget.WidgetPos;
import net.velli.scelli.widget.interfaces.ScrollableWidget;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ListWidget extends BasicContainer<ListWidget> implements ScrollableWidget {
    protected int verticalPadding = 10, horizontalPadding = 10, itemPadding = 2;
    protected boolean barHovered = false, sideHovered = false;
    protected int barHeight = 0;
    protected float scrollAmount = 0, scaleFactor = 0, renderScrollAmount = 0, mouseAnchor = 0, scrollAnchor = 0;
    protected int maxScrollAmount = 0;
    protected boolean holdingBar = false;

    public static ListWidget create() {
        return new ListWidget();
    }

    @Override
    public void hover(float mouseX, float mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        updateBarHovered(mouseX, mouseY);
    }

    private void updateBarHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX >= renderedWidth() - 3 && mouseX < renderedWidth();
        this.sideHovered = inBoundsX && hovered();
        boolean inBoundsY = mouseY >= renderScrollAmount * scaleFactor && mouseY < renderScrollAmount * scaleFactor + barHeight;
        this.barHovered = inBoundsX && inBoundsY && hovered();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        context.fill(0, 0, Math.round(renderedWidth()), Math.round(renderedHeight()), stackOpacity(0x33000000, opacity));
        AtomicInteger currentHeight = new AtomicInteger(verticalPadding);
        getWidgets().forEach(widget -> {
            widget.position().alignment = WidgetPos.Alignment.TOPLEFT;
            widget.position().x = horizontalPadding;
            widget.position().renderX = horizontalPadding;
            widget.position().y = currentHeight.get() - Math.round(renderScrollAmount);
            widget.position().renderY = currentHeight.get() - Math.round(renderScrollAmount);
            widget.position().width = width() - horizontalPadding * 2;
            widget.position().renderWidth = renderedWidth() - horizontalPadding * 2;
            currentHeight.getAndAdd(Math.round(widget.renderedHeight()) + itemPadding);
        });
        renderWidgets(context, mouseX, mouseY, opacity);
        currentHeight.getAndAdd(-itemPadding);
        maxScrollAmount = Math.clamp(currentHeight.get() + verticalPadding - Math.round(renderedHeight()), 0, 999999999);
        scrollAmount = Math.clamp(scrollAmount, 0, maxScrollAmount);
        renderScrollAmount = Math.clamp(renderScrollAmount, 0, maxScrollAmount);
        if (maxScrollAmount > 0) renderScrollbar(context, mouseX, mouseY, opacity, delta);
        renderScrollAmount = ScelliUtil.lerp(renderScrollAmount, scrollAmount, 16f * delta);
    }

    protected void renderScrollbar(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        if (holdingBar) setScrollAmount(scrollAnchor + (mouseY - mouseAnchor) / scaleFactor);
        context.fill(Math.round(renderedWidth() - 4), 0, Math.round(renderedWidth()), Math.round(renderedHeight()), stackOpacity(0x66000000, opacity));
        scaleFactor = renderedHeight() / (renderedHeight() + maxScrollAmount);
        barHeight = Math.round(renderedHeight() * scaleFactor);
        context.fill(
                Math.round(renderedWidth() - 4),
                Math.round(renderScrollAmount * scaleFactor),
                Math.round(renderedWidth()),
                Math.round(renderScrollAmount * scaleFactor) + barHeight,
                stackOpacity(0xDDEEFFFF, opacity)
        );
    }

    @Override
    public ListWidget getThis() {
        return this;
    }

    @Override
    public void onClick(float mouseX, float mouseY, boolean active) {
        super.onClick(mouseX, mouseY, active);
        if (!active) return;
        holdingBar = barHovered || sideHovered;
        scrollAnchor = renderScrollAmount;
        mouseAnchor = mouseY;
        if (sideHovered && !barHovered) {
            if (mouseY < renderScrollAmount * scaleFactor + (float) barHeight / 2) scrollAnchor = mouseY / scaleFactor;
            else scrollAnchor = mouseY / scaleFactor - barHeight / scaleFactor;
        }
    }

    @Override
    public void onRelease(float mouseX, float mouseY, boolean active) {
        super.onRelease(mouseX, mouseY, active);
        holdingBar = false;
    }

    @Override
    public boolean onScroll(double amount) {
        boolean result = super.onScroll(amount);
        if (!result && !holdingBar) setScrollAmount((float) (scrollAmount - amount));
        return result || maxScrollAmount > 0;
    }

    public void setScrollAmount(float amount) {
        maxScrollAmount = Math.clamp(maxScrollAmount, 0, 999999999);
        scrollAmount = Math.clamp(amount, 0, maxScrollAmount);
    }

    public ListWidget withPadding( int verticalPadding, int horizontalPadding, int itemPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.itemPadding = itemPadding;
        return this;
    }
}
