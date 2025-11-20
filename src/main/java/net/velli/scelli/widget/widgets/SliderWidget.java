package net.velli.scelli.widget.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.Scelli;
import net.velli.scelli.ScelliUtil;
import net.velli.scelli.widget.widgets.containers.BasicContainer;
import net.velli.scelli.widget.interfaces.ClickableWidget;

public class SliderWidget extends BasicContainer<SliderWidget> implements ClickableWidget {

    protected int sliderProgress = 0;
    protected NumInputWidget inputBox = NumInputWidget.create().withDimensions(Scelli.MC.textRenderer.getWidth("100") + 8, 16, true);

    protected boolean barHovered = false;
    protected boolean holdingSlider = false;

    protected int lowThreshold = 0;
    protected int highThreshold = 100;

    public static SliderWidget create(int sliderProgress) {
        SliderWidget widget = new SliderWidget();
        widget.sliderProgress = sliderProgress;
        widget.addWidget(widget.inputBox);
        return widget;
    }

    public SliderWidget withThresholds(int lower, int higher) {
        if (higher <= lower) return this;
        lowThreshold = lower;
        highThreshold = higher;
        return this;
    }

    public static SliderWidget create() {
        return create(0);
    }

    private float renderedBarX = 0;

    @Override
    public void hover(float mouseX, float mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        updateBarHovered(mouseX, mouseY);
    }

    private void updateBarHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX >= 3 && mouseX < renderedWidth() - inputBox.renderedWidth() - 3;
        boolean inBoundsY = mouseY >= 3 && mouseY < renderedHeight() - 3;
        this.barHovered = inBoundsX && inBoundsY && hovered();
    }

    @Override
    protected void render(DrawContext context, float mouseX, float mouseY, int opacity, float delta) {
        TextRenderer textRenderer = Scelli.MC.textRenderer;
        int values = highThreshold - lowThreshold;
        int offset = lowThreshold;
        if (holdingSlider && !inputBox.selected) setSliderProgress(Math.round((float) (values * Math.round(mouseX - 3)) / (renderedWidth() - inputBox.renderedWidth() - 8)) + offset);
        int lowThresholdWidth = textRenderer.getWidth(String.valueOf(lowThreshold));
        int highThresholdWidth = textRenderer.getWidth(String.valueOf(highThreshold));
        inputBox.position.width = Math.max(lowThresholdWidth, highThresholdWidth) + 8;
        inputBox.position.renderWidth = inputBox.position.width;
        inputBox.position.x = renderedWidth() - inputBox.renderedWidth();
        inputBox.position.renderX = inputBox.position.x;
        if (!inputBox.selected) {
            if (inputBox.data.string.isEmpty()) inputBox.setString("0");
            inputBox.data.index = 0;
            inputBox.data.selectionStart = 0;
            inputBox.data.selectionEnd = 0;
            inputBox.setString(String.valueOf(sliderProgress));
        } else {
            if (inputBox.data.string.isEmpty() || inputBox.data.string.equals("-")) setSliderProgress(0);
            else setSliderProgress(Integer.parseInt(inputBox.data.string.length() >= 5 ? inputBox.data.string.substring(0, 5) : inputBox.data.string));
        }
        renderWidgets(context, mouseX, mouseY, Math.round((float) opacity() / 255 * opacity));
        context.fill(3, 7, renderedWidth() - renderedWidth() - 3, 9, stackOpacity(0xFF000000, opacity));


        int barX = 3 + (renderedWidth()) - inputBox.renderedWidth() - 8 * Math.round((float) (sliderProgress - offset) / values);
        renderedBarX = ScelliUtil.lerp(renderedBarX, barX, 16f * delta);
        context.fill(Math.round(renderedBarX), 3, Math.round(renderedBarX) + 2, renderedHeight() - 3, stackOpacity(0xFFFFFFFF, opacity));
    }

    @Override
    public SliderWidget getThis() {
        return this;
    }

    @Override
    public SliderWidget withDimensions(int width, int height, boolean snap) {
        position.width = width;
        if (snap) position.renderWidth = width;
        return getThis();
    }

    @Override
    public void onClick(float mouseX, float mouseY, boolean active) {
        if (barHovered) holdingSlider = active;
        super.onClick(mouseX, mouseY, active);
    }

    @Override
    public void onRelease(float mouseX, float mouseY, boolean active) {
        holdingSlider = false;
        super.onRelease(mouseX, mouseY, active);
    }

    public void setSliderProgress(int progress) {
        this.sliderProgress = Math.clamp(progress, lowThreshold, highThreshold);
    }
}
