package net.velli.scelli.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.velli.scelli.widget.interfaces.WidgetContainer;
import net.velli.scelli.widget.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainerScreen extends Screen implements WidgetContainer<WidgetContainerScreen> {
    private final List<Widget<?>> widgets = new ArrayList<>();

    protected WidgetContainerScreen(Screen previousScreen) {
        super(Text.literal(""));
    }

    @Override
    public int x() {
        return 0;
    }

    @Override
    public int y() {
        return 0;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int opacity() {
        return 255;
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    @Override
    public WidgetContainerScreen getWidget() {
        return this;
    }

    public WidgetContainerScreen addWidgets(Widget<?>... widgets) {
        for (Widget<?> widget : widgets) {
            if (widget.parent != null) widget.parent.removeWidget(widget);
            widget.parent = getWidget();
            this.widgets.add(widget);
        }
        return getWidget();
    }

    @Override
    public void removeWidget(Widget<?> widget) {
        if (getWidgets().contains(widget)) {
            widgets.remove(widget);
            widget.parent = null;
        }
    }

    @Override
    public void clearWidgets() {
        for (Widget<?> widget : getWidgets()) {
            widgets.remove(widget);
            widget.parent = null;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        hoverChildren(mouseX, mouseY, true);
        renderChildren(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        onClick((int) click.x(), (int) click.y());
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        onRelease((int) click.x(), (int) click.y());
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        onScroll((int) verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean charTyped(CharInput input) {
        onType(input.asString().charAt(0));
        return super.charTyped(input);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        onKeyPressed(input.key(), input.modifiers());
        return super.keyPressed(input);
    }
}
