package net.velli.scelli.widget.interfaces;

import net.velli.scelli.widget.ButtonWidget;

public interface ClickableWidget {
    void onClick(float mouseX, float mouseY, boolean active);
    void onRelease(float mouseX, float mouseY, boolean active);
    boolean isHovered(float mouseX, float mouseY);

    interface ClickProcessor {
        void onClick(ButtonWidget button, float mouseX, float mouseY, boolean active);
    }
    interface ReleaseProcessor {
        void onRelease(ButtonWidget button, float mouseX, float mouseY, boolean active);
    }
}
