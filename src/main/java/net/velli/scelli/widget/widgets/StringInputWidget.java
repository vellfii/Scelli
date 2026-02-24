package net.velli.scelli.widget.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.Scelli;
import net.velli.scelli.widget.interfaces.ClickableWidget;
import net.velli.scelli.widget.interfaces.TypableWidget;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class StringInputWidget extends Widget<StringInputWidget> implements ClickableWidget, TypableWidget {
    public boolean selected = false;


    public StringInputData data = new StringInputData();
    protected ArrayList<StringInputData> history = new ArrayList<>();
    protected int historyIndex = 0;
    protected float blinkTimer = 0f;
    protected boolean guideVisible = true;
    protected boolean holding = false;

    protected ConfirmEvent processor = null;

    private int textOffset = 0;

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        context.enableScissor(0, 0, width(), height());
        blinkTimer += delta;
        if (blinkTimer >= 0.3) guideVisible = !guideVisible;
        if (blinkTimer >= 0.3) blinkTimer = 0;
        TextRenderer textRenderer = Scelli.MC.textRenderer;
        context.fill(0, 0, width(), height(), 0xFF000000);
        int leftBorder = 8;
        int rightBorder = width() - 8;
        int cursorLength = textRenderer.getWidth(data.string.substring(0, data.index)) - textOffset;
        if (cursorLength > rightBorder || cursorLength < leftBorder) textOffset = textRenderer.getWidth(data.string.substring(0, data.index)) - rightBorder;
        if (textOffset < 0 ) textOffset = 0;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(4 - textOffset, 4);
        if (data.selectionStart == data.selectionEnd) {
            context.drawText(textRenderer, data.string, 0, 0, 0xFFFFFFFF, true);
            if (guideVisible && selected) context.fill(
                    textRenderer.getWidth(data.string.substring(0, data.index)),
                    0,
                    textRenderer.getWidth(data.string.substring(0, data.index)) + 1,
                    textRenderer.fontHeight - 1,
                    0xFFFFFFFF
            );
        }
        else {
            int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
            int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
            String lower = data.string.substring(0, lowerIndex);
            String higher = data.string.substring(higherIndex);
            String middle = data.string.substring(lowerIndex, higherIndex);
            context.drawText(textRenderer, lower, 0, 0, 0xFFFFFFFF, true);
            context.fill(textRenderer.getWidth(lower) - 1, -1, textRenderer.getWidth(lower) + textRenderer.getWidth(middle), textRenderer.fontHeight, 0xDDEEFFFF);
            context.drawText(textRenderer, middle, textRenderer.getWidth(lower), 0, 0xFF000000, false);
            context.drawText(textRenderer, higher, textRenderer.getWidth(lower) + textRenderer.getWidth(middle), 0, 0xFFFFFFFF, true);
        }
        context.getMatrices().popMatrix();
        context.drawStrokedRectangle(0, 0, width(), height(), selected || isHovered() ? 0xDDEEFFFF : 0xBBDDEEEE);

        if (holding) {
            StringBuilder progress = new StringBuilder();
            for (char chr : data.string.toCharArray()) {
                progress.append(chr);
                if (textRenderer.getWidth(progress.toString()) >= mouseX - 4 + textOffset) {
                    data.index = progress.length() - 1;
                    if (data.index < data.selectionStart) data.index = progress.length();
                    data.selectionEnd = data.index;
                    break;
                }
                if (progress.length() == data.string.length()) {
                    data.index = progress.length();
                    data.selectionEnd = data.index;
                }
            }
        }
        context.disableScissor();
    }

    public StringInputWidget withConfirmEvent(ConfirmEvent processor) {
        this.processor = processor;
        return getWidget();
    }

    public void clearString() {
        data.string = "";
        data.selectionStart = 0;
        data.selectionEnd = 0;
        data.index = 0;
    }

    public StringInputWidget setString(String string) {
        this.data.string = string;
        this.data.selectionStart = Math.min(this.data.selectionStart, string.length());
        this.data.selectionEnd = Math.min(this.data.selectionEnd, string.length());
        this.data.index = Math.min(this.data.index, string.length());
        return this;
    }

    public String getString() {
        return this.data.string;
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        selected = isHovered();
        if (selected) {
            holding = true;
            TextRenderer textRenderer = Scelli.MC.textRenderer;
            StringBuilder progress = new StringBuilder();
            for (char chr : data.string.toCharArray()) {
                progress.append(chr);
                if (textRenderer.getWidth(progress.toString()) >= mouseX - 4 + textOffset) {
                    data.index = progress.length() - 1;
                    data.selectionStart = data.index;
                    data.selectionEnd = data.index;
                    logStateToHistory();
                    break;
                }
                if (progress.length() == data.string.length()) {
                    data.index = progress.length();
                    data.selectionStart = data.index;
                    data.selectionEnd = data.index;
                    logStateToHistory();
                }
            }
        }
    }

    @Override
    public void onRelease(int mouseX, int mouseY) {
        if (holding) {
            logStateToHistory();
        }
        holding = false;
    }

    @Override
    public void onType(char chr) {
        if (!selected) return;
        logStateToHistory();
        if (data.selectionStart == data.selectionEnd) {
            String lower = data.string.substring(0, data.index);
            String higher = data.string.substring(data.index);
            data.string = lower + chr + higher;
            data.index += 1;
        } else {
            int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
            int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
            String lower = data.string.substring(0, lowerIndex);
            String higher = data.string.substring(higherIndex);
            data.string = lower + chr + higher;
            data.index = lowerIndex + 1;
        }
        data.selectionStart = data.index;
        data.selectionEnd = data.index;
        blinkTimer = 0;
        guideVisible = true;
    }

    @Override
    public void onKeyPressed(int keyCode, int modifiers) {
        if (!selected) return;
        boolean shiftHeld = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
        boolean ctrlHeld = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
        if (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_RIGHT || keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            blinkTimer = 0;
            guideVisible = true;
            logStateToHistory();
        }
        data.index += keyCode == GLFW.GLFW_KEY_LEFT ? -1 : keyCode == GLFW.GLFW_KEY_RIGHT ? 1 : 0;
        data.index = Math.clamp(data.index, 0, data.string.length());
        if (!shiftHeld && (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_RIGHT)) {
            if (data.selectionStart != data.selectionEnd) {
                int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
                int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
                if (keyCode == GLFW.GLFW_KEY_LEFT) data.index = lowerIndex;
                if (keyCode == GLFW.GLFW_KEY_RIGHT) data.index = higherIndex;
            }
            data.selectionStart = data.index;
        }
        if (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_RIGHT) data.selectionEnd = data.index;
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (data.string.isEmpty() || (data.index == 0 && (data.selectionStart == 0 && data.selectionEnd == 0))) return;
            if (data.selectionStart == data.selectionEnd) {
                String lower = data.string.substring(0, data.index - 1);
                String higher = data.string.substring(data.index);
                data.string = lower + higher;
                data.index -= 1;
            } else {
                int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
                int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
                String lower = data.string.substring(0, lowerIndex);
                String higher = data.string.substring(higherIndex);
                data.string = lower + higher;
                data.index = lowerIndex;
            }
            data.selectionStart = data.index;
            data.selectionEnd = data.index;

        }
        if (keyCode == GLFW.GLFW_KEY_A && ctrlHeld) {
            logStateToHistory();
            data.selectionStart = 0;
            data.selectionEnd = data.string.length();
        }
        if (keyCode == GLFW.GLFW_KEY_Z && ctrlHeld) {
            if (history.isEmpty() || historyIndex == 0) return;
            if (historyIndex == history.size()) {
                logStateToHistory();
                historyIndex -= 1;
            }
            historyIndex -= 1;
            data = history.get(historyIndex).clone();
        }
        if (keyCode == GLFW.GLFW_KEY_Y && ctrlHeld) {
            if (history.isEmpty() || historyIndex >= history.size() - 1) return;
            historyIndex += 1;
            data = history.get(historyIndex).clone();
        }
        if (keyCode == GLFW.GLFW_KEY_X && ctrlHeld) {
            if (data.selectionStart != data.selectionEnd) {
                logStateToHistory();
                int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
                int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
                String lower = data.string.substring(0, lowerIndex);
                String higher = data.string.substring(higherIndex);
                String middle = data.string.substring(lowerIndex, higherIndex);
                GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), middle);
                data.string = lower + higher;
                data.index = lowerIndex;
                data.selectionStart = data.index;
                data.selectionEnd = data.index;
            }
        }
        if (keyCode == GLFW.GLFW_KEY_C && ctrlHeld) {
            if (data.selectionStart != data.selectionEnd) {
                int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
                int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
                String middle = data.string.substring(lowerIndex, higherIndex);
                GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), middle);
            }
        }
        if (keyCode == GLFW.GLFW_KEY_V && ctrlHeld) {
            String clipboard = GLFW.glfwGetClipboardString(GLFW.glfwGetCurrentContext());
            if (clipboard == null) return;
            logStateToHistory();
            int lowerIndex = Math.min(data.selectionStart, data.selectionEnd);
            int higherIndex = Math.max(data.selectionStart, data.selectionEnd);
            String lower = data.string.substring(0, lowerIndex);
            String higher = data.string.substring(higherIndex);
            data.string = lower + clipboard + higher;
            data.index = lower.length() + clipboard.length();
            data.selectionStart = data.index;
            data.selectionEnd = data.index;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER && !shiftHeld && !ctrlHeld) {
            if (processor != null) processor.onConfirmed(getWidget());
        }
    }

    protected void logStateToHistory() {
        history = new ArrayList<>(history.subList(0, historyIndex));
        history.add(data.clone());
        historyIndex = history.size();
    }

    public interface ConfirmEvent {
        void onConfirmed(StringInputWidget stringInputWidget);
    }

    @Override
    public StringInputWidget getWidget() {
        return this;
    }
}
