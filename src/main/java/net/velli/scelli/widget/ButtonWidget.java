package net.velli.scelli.widget;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    ClickProcessor processor = () -> {};

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, float delta) {

    }

    @Override
    public ButtonWidget getThis() {
        return this;
    }

    @Override
    public void onClick(float mouseX, float mouseY, boolean active) {

    }

    @Override
    public void onRelease(float mouseX, float mouseY, boolean active) {

    }
}
