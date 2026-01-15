package net.velli.scelli.widget.widgets;

public class WidgetPos {

    int x = 0, y = 0, width = 16, height = 16, opacity = 255;
    Alignment alignment = Alignment.TOPLEFT;

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    public enum Alignment {
        TOPLEFT("topleft"), TOP("top"), TOPRIGHT("topright"),
        LEFT("left"), CENTER(""), RIGHT("right"),
        BOTTOMLEFT("bottomleft"), BOTTOM("bottom"), BOTTOMRIGHT("bottomright");
        final String id;

        Alignment(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }
}
