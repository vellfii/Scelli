package net.velli.scelli.widget.interfaces;

public interface ClickableWidget {
    void onClick(float mouseX, float mouseY, boolean active);
    void onRelease(float mouseX, float mouseY, boolean active);

    interface ClickProcessor {
        void onClick();
    }
}
