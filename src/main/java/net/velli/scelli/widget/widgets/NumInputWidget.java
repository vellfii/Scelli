package net.velli.scelli.widget.widgets;

import org.lwjgl.glfw.GLFW;

public class NumInputWidget extends StringInputWidget {

    public static NumInputWidget create(int string) {
        NumInputWidget widget = new NumInputWidget();
        widget.data.string = String.valueOf(string);
        return widget;
    }

    public static NumInputWidget create() {
        NumInputWidget widget = new NumInputWidget();
        return widget;
    }

    @Override
    public void onType(char chr) {
        if (!selected) return;
        if (!isInt(String.valueOf(chr)) && chr != '-') return;
        if (chr == '-' && (data.index != 0 || data.string.startsWith("-"))) return;
        if (data.string.startsWith("-") && data.index == 0) return;
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
            if (data.string.isEmpty() || (data.index == 0 && (data.selectionStart == 0 && data.selectionEnd == 0)))
                return;
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
            if (!isInt(clipboard) && !clipboard.startsWith("-")) return;
            if (clipboard.startsWith("-") && data.index != 0) return;
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
            selected = false;
        }
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public NumInputWidget withDimensions(int width, int height, boolean snap) {
        position.width = width;
        if (snap) position.renderWidth = width;
        return this;
    }

    @Override
    public NumInputWidget getThis() {
        return this;
    }

    @Override
    public NumInputWidget setString(String string) {
        if (!isInt(string)) return this;
        this.data.string = string;
        return this;
    }
}
