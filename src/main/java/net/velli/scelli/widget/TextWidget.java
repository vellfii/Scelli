package net.velli.scelli.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.velli.scelli.Scelli;
import net.velli.scelli.ScelliUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TextWidget extends Widget<TextWidget> {
    protected List<Text> text = new ArrayList<>();
    public enum TextAlignment {
        LEFT, CENTER, RIGHT
    }
    protected TextAlignment textAlignment = TextAlignment.LEFT;
    protected int padding = 2;

    public static TextWidget create() {
        return new TextWidget();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, float delta) {
        TextRenderer textRenderer = Scelli.MC.textRenderer;
        AtomicInteger textY = new AtomicInteger(0);
        text.forEach(line -> {
            textRenderer.wrapLines(line, Math.round(renderedWidth())).forEach(wrappedLine -> {
                int textX = 0;
                if (textAlignment == TextAlignment.CENTER) textX = Math.round(renderedWidth() / 2) - textRenderer.getWidth(wrappedLine) / 2;
                if (textAlignment == TextAlignment.RIGHT) textX = Math.round(renderedWidth() - textRenderer.getWidth(wrappedLine));
                context.drawText(textRenderer, wrappedLine, textX, textY.get(), 0xFFFFFFFF, true);
                textY.getAndAdd(textRenderer.fontHeight + padding);
            });
        });
        this.position.height = textY.get() - padding;
        this.position.renderHeight = textY.get() - padding;
    }

    @Override
    public TextWidget getThis() {
        return this;
    }


    public TextWidget withText(List<Text> text) {
        this.text.clear();
        text.forEach(line -> {
            List<Text> newlinedLine = ScelliUtil.splitTextNewline(line);
            this.text.addAll(newlinedLine);
        });
        return this;
    }

    public TextWidget withText(Text text) {
        return withText(List.of(text));
    }

    public TextWidget withAlignment(TextAlignment alignment) {
        this.textAlignment = alignment;
        return this;
    }

    public TextWidget withPadding(int padding) {
        this.padding = padding;
        return this;
    }
}
