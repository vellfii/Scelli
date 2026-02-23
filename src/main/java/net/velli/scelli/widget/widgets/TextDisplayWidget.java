package net.velli.scelli.widget.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.velli.scelli.Scelli;
import net.velli.scelli.ScelliUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextDisplayWidget extends Widget<TextDisplayWidget> {
    private List<OrderedText> lines = new ArrayList<>();
    private Alignment alignment = Alignment.LEFT;

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = Scelli.MC.textRenderer;
        int offsetY = 0;
        for (OrderedText line : lines) {
            int offsetX;
            if (Objects.equals(alignment, Alignment.LEFT)) offsetX = 0;
            else if (Objects.equals(alignment, Alignment.RIGHT)) offsetX = width() - textRenderer.getWidth(line);
            else offsetX = width() / 2 - textRenderer.getWidth(line) / 2;
            context.drawText(textRenderer, line, offsetX, offsetY, 0xFFFFFFFF, true);
            offsetY += textRenderer.fontHeight + 1;
        }
    }

    @Override
    public TextDisplayWidget getWidget() {
        return this;
    }

    public List<OrderedText> lines() {
        return lines;
    }

    public TextDisplayWidget setLines(List<Text> lines) {
        ArrayList<OrderedText> orderedLines = new ArrayList<>();
        for (Text line : lines) orderedLines.add(line.asOrderedText());
        return setLines(orderedLines);
    }

    public TextDisplayWidget setLines(ArrayList<OrderedText> lines) {
        this.lines = lines;
        withDimensions(width(), (lines.size() * (Scelli.MC.textRenderer.fontHeight + 1)) - 1, true);
        return getWidget();
    }

    public TextDisplayWidget setLines(Text... lines) {
        return setLines(List.of(lines));
    }

    public TextDisplayWidget setLines(OrderedText... lines) {
        return setLines(new ArrayList<>(List.of(lines)));
    }

    public TextDisplayWidget addLine(Text line) {
        for (Text ln : ScelliUtil.splitTextNewline(line)) addLine(ln.asOrderedText());
        return getWidget();
    }

    public TextDisplayWidget addLine(OrderedText line) {
        lines.add(line);
        return getWidget();
    }

    public TextDisplayWidget insertLine(Text line, int index) {
        for (Text ln : ScelliUtil.splitTextNewline(line)) {
            insertLine(ln.asOrderedText(), index);
            index += 1;
        }
        return getWidget();
    }

    public TextDisplayWidget insertLine(OrderedText line, int index) {
        lines.add(index, line);
        return getWidget();
    }

    public TextDisplayWidget removeLine(int index) {
        lines.remove(index);
        return getWidget();
    }

    public TextDisplayWidget withTextAlignment(Alignment alignment) {
        if (alignment.id().contains("left")) alignment = Alignment.LEFT;
        else if (alignment.id().contains("right")) alignment = Alignment.RIGHT;
        else alignment = Alignment.CENTER;
        this.alignment = alignment;
        return getWidget();
    }
}
