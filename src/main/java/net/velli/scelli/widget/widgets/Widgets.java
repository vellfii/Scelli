package net.velli.scelli.widget.widgets;

import java.util.function.Supplier;

public abstract class Widgets {
    public static <T extends Widget<T>> T create(Supplier<T> supplier, int x, int y, int width, int height) {
        return supplier.get().withPosition(x, y, true).withDimensions(width, height, true);
    }

    public static <T extends Widget<T>> T create(Supplier<T> supplier) {
        return create(supplier, 0, 0, 16, 16);
    }
}
