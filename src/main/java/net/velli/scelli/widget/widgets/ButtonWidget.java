package net.velli.scelli.widget.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.velli.scelli.Scelli;
import net.velli.scelli.ScelliUtil;
import net.velli.scelli.widget.interfaces.ClickableWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    protected ClickProcessor clickProcessor = (button, mouseX, mouseY, active) -> {};
    protected ReleaseProcessor releaseProcessor = (button, mouseX, mouseY, active) -> {};

    protected final List<Text> text = new ArrayList<>();

    public static ButtonWidget create() {
        return new ButtonWidget();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        TextRenderer textRenderer = Scelli.MC.textRenderer;
        if (hovered()) {
            context.fill(0, 0, renderedWidth(), renderedHeight(), stackOpacity(0x66333333, opacity));
            context.drawBorder(0, 0, Math.round(renderedWidth()), Math.round(renderedHeight()), stackOpacity(0xDDEEFFFF, opacity));
        } else context.fill(0, 0, Math.round(renderedWidth()), Math.round(renderedHeight()), stackOpacity(0x66000000, opacity));
        AtomicInteger textY = new AtomicInteger(Math.round(renderedHeight() / 2) - textRenderer.fontHeight / 2 * text.size() - 2 * (text.size() - 1));
        text.forEach(line -> {
            int textX = Math.round(renderedWidth() / 2) - textRenderer.getWidth(line) / 2;
            context.drawText(textRenderer, line, textX, textY.get(), stackOpacity(0xFFFFFFFF, opacity), true);
            textY.getAndAdd(textRenderer.fontHeight + 2);
        });
    }

    @Override
    public ButtonWidget getThis() {
        return this;
    }

    public ButtonWidget withClickEvent(ClickProcessor processor) {
        this.clickProcessor = processor;
        return this;
    }

    @Override
    public void onClick(float mouseX, float mouseY, boolean active) {
        clickProcessor.onClick(this, mouseX, mouseY, active);
    }

    public ButtonWidget withReleaseEvent(ReleaseProcessor processor) {
        this.releaseProcessor = processor;
        return this;
    }

    @Override
    public void onRelease(float mouseX, float mouseY, boolean active) {
        releaseProcessor.onRelease(this, mouseX, mouseY, active);
    }

    public ButtonWidget withText(List<Text> text) {
        this.text.clear();
        text.forEach(line -> {
            List<Text> newlinedLine = ScelliUtil.splitTextNewline(line);
            this.text.addAll(newlinedLine);
        });
        return this;
    }

    public ButtonWidget withText(Text text) {
        return withText(List.of(text));
    }
}
