package net.velli.scelli.widget.widgets;

public class WidgetPos {

    int x = 0, y = 0, width = 16, height = 16, opacity = 255;
    int targetX = 0, targetY = 0, targetWidth = 0, targetHeight = 0, targetOpacity = 255;
    float subpixelX = 0, subpixelY = 0, subpixelWidth = 0, subpixelHeight = 0, subpixelOpacity = 255;
    Alignment alignment = Alignment.TOPLEFT;

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }
}
