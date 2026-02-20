package net.velli.scelli.widget.widgets;

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
