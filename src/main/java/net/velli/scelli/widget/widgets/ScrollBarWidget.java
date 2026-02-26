package net.velli.scelli.widget.widgets;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.ScelliUtil;
import net.velli.scelli.widget.interfaces.ClickableWidget;
import net.velli.scelli.widget.widgets.containers.VerticalListWidget;

public class ScrollBarWidget extends Widget<ScrollBarWidget> implements ClickableWidget {

    int barHeight = 0;
    float scaleFactor = 0;
    int scrollAmount = 0;
    int targetScrollAmount = 0;
    float subpixelScrollAmount = 0;
    int maxScrollAmount = 0;
    int mouseAnchor = 0;
    int scrollAnchor = 0;

    boolean rotated = false;
    boolean held = false;

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!(parent instanceof VerticalListWidget vlw)) return;
        if (rotated) context.getMatrices().rotate(1.570796f);

        if (scrollAmount != targetScrollAmount) {
            subpixelScrollAmount = ScelliUtil.lerp(subpixelScrollAmount, targetScrollAmount, 16f * delta);
            scrollAmount = Math.round(subpixelScrollAmount);
        }
        barHeight = Math.round(height() * scaleFactor);
        if (held) {
            if (vlw.reversed) {
                setScrollAmount(scrollAnchor - Math.round((mouseY - mouseAnchor) / scaleFactor));
            } else {
                setScrollAmount(scrollAnchor + Math.round((mouseY - mouseAnchor) / scaleFactor));
            }
        }
        context.fill(0, 0, width(), height(), 0x66000000);
        int visScrollAmount = scrollAmount;
        if (vlw.reversed) visScrollAmount = maxScrollAmount - scrollAmount;
        context.fill(
                0,
                (int) (visScrollAmount * scaleFactor),
                width(),
                barHeight + (int) (visScrollAmount * scaleFactor),
                0xFFFFFFFF
        );
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public void setScrollAmount(int amount) {
        amount = Math.max(amount, 0);
        amount = Math.min(amount, maxScrollAmount);
        targetScrollAmount = amount;
    }

    public void resetScrollAmount() {
        scrollAmount = 0;
        targetScrollAmount = 0;
        subpixelScrollAmount = 0f;
    }

    public void setMaxScrollAmount(int amount) {
        maxScrollAmount = amount;
    }

    public int getMaxScrollAmount() {
        return maxScrollAmount;
    }

    public int scrollAmount() {
        return scrollAmount;
    }

    @Override
    public ScrollBarWidget getWidget() {
        return this;
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        held = isHovered();
        mouseAnchor = mouseY;
        scrollAnchor = scrollAmount;
    }

    @Override
    public void onRelease(int mouseX, int mouseY) {
        held = false;
    }
}
