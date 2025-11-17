package net.velli.scelli.widget.widgets.containers;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.velli.scelli.widget.widgets.ButtonWidget;
import net.velli.scelli.widget.widgets.TextWidget;
import net.velli.scelli.widget.WidgetPos;

import java.util.concurrent.atomic.AtomicInteger;

public class FolderWidget extends BasicContainer<FolderWidget> {
    protected int verticalPadding = 10, horizontalPadding = 10, itemPadding = 2;
    protected boolean open = true;
    protected ButtonWidget toggleButton = ButtonWidget.create()
            .withClickEvent(
                    (button,    mouseX, mouseY, active) ->  {
                        if (!(active && button.hovered())) return;
                        open = !open;
                        button.withText(open ? Text.literal("^") : Text.literal("v"));
                    }
            )
            .withPosition(2, 2, true)
            .withDimensions(16, 16, true);
    protected TextWidget title = TextWidget.create()
            .withAlignment(TextWidget.TextAlignment.CENTER)
            .withPosition(0, 6, true)
            .withText(Text.literal(""));

    public static FolderWidget create(boolean open) {
        FolderWidget folder = new FolderWidget();
        folder.open = open;
        folder.toggleButton.withText(open ? Text.literal("^") : Text.literal("v"));
        folder.getWidgets().add(folder.toggleButton);
        folder.getWidgets().add(folder.title);
        folder.position.height = open ? folder.openHeight() : 20;
        folder.position.renderHeight = open ? folder.openHeight() : 20;
        return folder;
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        this.position.height = open ? openHeight() : 20;
        context.fill(0, 0, Math.round(renderedWidth()), Math.round(renderedHeight()), stackOpacity(0x33000000, opacity));
        toggleButton.position().x = 2;
        title.position().width = Math.round(renderedWidth()) - horizontalPadding * 2;
        title.position().renderWidth = title.position().width;
        title.position().x = horizontalPadding;
        title.position().renderX = horizontalPadding;
        AtomicInteger currentHeight = new AtomicInteger(verticalPadding + 20);
        getWidgets().forEach(widget -> {
            if (getWidgets().indexOf(widget) > 1) {
                widget.position().alignment = WidgetPos.Alignment.TOPLEFT;
                widget.position().x = horizontalPadding;
                widget.position().renderX = horizontalPadding;
                widget.position().y = currentHeight.get();
                widget.position().renderY = currentHeight.get();
                widget.position().width = width() - horizontalPadding * 2;
                widget.position().renderWidth = renderedWidth() - horizontalPadding * 2;
                currentHeight.getAndAdd(Math.round(widget.renderedHeight()) + itemPadding);
            }
        });
        renderWidgets(context, mouseX, mouseY, opacity);
    }

    @Override
    public FolderWidget getThis() {
        return this;
    }

    protected int openHeight() {
        AtomicInteger lowestHeight = new AtomicInteger(verticalPadding + 20);
        getWidgets().forEach(widget -> lowestHeight.getAndAdd(Math.round(widget.renderedHeight()) + itemPadding) );
        lowestHeight.getAndAdd(verticalPadding - itemPadding);
        return lowestHeight.get();
    }

    @Override
    public void removeWidget(int index) {
        super.removeWidget(Math.clamp(index + 2, 2, getWidgets().size()));
    }

    public FolderWidget withPadding(int verticalPadding, int horizontalPadding, int itemPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.itemPadding = itemPadding;
        return this;
    }

    public FolderWidget withTitle(Text title) {
        this.title.withText(title);
        return this;
    }
}
