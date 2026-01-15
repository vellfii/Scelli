package net.velli.scelli.widget.widgets;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    private ButtonWidget() {}

    public static ButtonWidget create(int x, int y, int width, int height) {
        ButtonWidget widget = new ButtonWidget();
        widget.withPosition(x, y);
        widget.withDimensions(width, height);
        return widget;
    }

    public static ButtonWidget create() {
        return create(0, 0, 16, 16);
    }

    protected ClickEvent processor;

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (isHovered()) System.out.println("wow amazing");
        if (processor != null) processor.onClick(getWidget(), mouseX, mouseY);
    }

    @Override
    public void onRelease(int mouseX, int mouseY) {
        if (processor != null) processor.onRelease(getWidget(), mouseX, mouseY);
    }

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!isHovered()) context.fill(0, 0, width(), height(), 0x66000000);
        else {
            context.fill(0, 0, width(), height(), 0x66333333);
            context.drawStrokedRectangle(0, 0, width(), height(), 0xFFFFFFFF);
        }
    }

    @Override
    public ButtonWidget getWidget() {
        return this;
    }

    public ButtonWidget withProcessor(ClickEvent processor) {
        this.processor = processor;
        return getWidget();
    }

    public interface ClickEvent {
        void onClick(ButtonWidget button, int mouseX, int mouseY);
        void onRelease(ButtonWidget button, int mouseX, int mouseY);
    }

}
