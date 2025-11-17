package net.velli.scelli.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.velli.scelli.widget.widgets.Widget;
import net.velli.scelli.widget.interfaces.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainerScreen extends Screen implements WidgetContainer<WidgetContainerScreen> {
    private final List<Widget<?>> widgets = new ArrayList<>();

    protected WidgetContainerScreen(Screen previousScreen) {
        super(Text.literal(""));
    }

    @Override
    public float x() {
        return 0;
    }

    @Override
    public float renderedX() {
        return 0;
    }

    @Override
    public float y() {
        return 0;
    }

    @Override
    public float renderedY() {
        return 0;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public float renderedWidth() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public float renderedHeight() {
        return height;
    }

    @Override
    public int opacity() {
        return 255;
    }

    @Override
    public float renderedOpacity() {
        return 255;
    }

    @Override
    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    @Override
    public WidgetContainerScreen getThis() {
        return null;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        renderWidgets(context, mouseX, mouseY, opacity());
        hoverWidgets(mouseX, mouseY, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        onClick((float) mouseX, (float) mouseY, true);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        onRelease((float) mouseX, (float) mouseY, true);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        onScroll(verticalAmount * 15);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        onType(chr);
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        onKeyPressed(keyCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
