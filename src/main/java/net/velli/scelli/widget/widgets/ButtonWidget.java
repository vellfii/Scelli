package net.velli.scelli.widget.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.velli.scelli.Scelli;
import net.velli.scelli.widget.interfaces.ClickableWidget;
import net.velli.scelli.widget.interfaces.WidgetContainer;

import java.util.List;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget, WidgetContainer<ButtonWidget> {
    protected ClickEvent processor;
    private final TextDisplayWidget text = Widgets.create(TextDisplayWidget::new, 0, 0, width(), height()).withTextAlignment(Alignment.CENTER);

    @Override
    public void onClick(int mouseX, int mouseY) {
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
        text.withPosition(0, height() / 2 - Scelli.MC.textRenderer.fontHeight / 2, true);
        renderWidget(text, context, mouseX, mouseY);
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return List.of(text);
    }

    @Override
    public ButtonWidget getWidget() {
        return this;
    }

    @Override
    public void clearWidgets() {

    }

    @Override
    public void removeWidget(Widget<?> widget) {

    }

    @Override
    public ButtonWidget addWidgets(Widget<?>[] widgets) {
        return getWidget();
    }

    public ButtonWidget withClickEvent(ClickEvent processor) {
        this.processor = processor;
        return getWidget();
    }

    public interface ClickEvent {
        void onClick(ButtonWidget button, int mouseX, int mouseY);
        void onRelease(ButtonWidget button, int mouseX, int mouseY);
    }

    @Override
    public ButtonWidget withDimensions(int width, int height, boolean snap) {
        text.withDimensions(width, height, snap);
        return super.withDimensions(width, height, snap);
    }

    public ButtonWidget withText(Text text) {
        this.text.setLines(text);
        return getWidget();
    }
}
