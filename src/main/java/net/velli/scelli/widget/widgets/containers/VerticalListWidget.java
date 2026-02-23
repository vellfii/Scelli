package net.velli.scelli.widget.widgets.containers;

import net.minecraft.client.gui.DrawContext;
import net.velli.scelli.widget.interfaces.ScrollableWidget;
import net.velli.scelli.widget.interfaces.WidgetContainer;
import net.velli.scelli.widget.widgets.ScrollBarWidget;
import net.velli.scelli.widget.widgets.Widget;
import net.velli.scelli.widget.widgets.Widgets;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VerticalListWidget extends ContainerWidget<VerticalListWidget> {
    private final List<List<Widget<?>>> columns = new CopyOnWriteArrayList<>();
    private final List<Widget<?>> currentColumn = new CopyOnWriteArrayList<>();
    private int verticalPadding = 2;
    private int horizontalPadding = 2;
    private int itemPadding = 2;
    private int columnPadding = 2;

    private final ScrollBarWidget scrollBar = Widgets.create(ScrollBarWidget::new, 0, 0, 0, 0);

    @Override
    public void hover(int mouseX, int mouseY, boolean active) {
        super.hover(mouseX, mouseY, active);
        Vector2i offset = WidgetContainer.alignmentOffset(scrollBar);
        scrollBar.hover(mouseX - offset.x() - x(), mouseY - offset.y() - y(), active);
    }

    @Override
    public void renderMain(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width(), height(), 0x55000000);
        List<List<Widget<?>>> currentColumns = new ArrayList<>(this.columns);
        currentColumns.add(new ArrayList<>(currentColumn));

        int columnsWidth = 0;
        int maxColumnHeight = 0;
        int currentX = horizontalPadding;
        for (List<Widget<?>> column : currentColumns) {
            int columnWidth = 0;
            int columnHeight = -itemPadding;
            for (Widget<?> widget : column) {
                columnWidth = Math.max(columnWidth, widget.width());
                columnHeight += widget.height() + itemPadding;
            }
            maxColumnHeight = Math.max(maxColumnHeight, columnHeight);
            columnsWidth += columnWidth + columnPadding;
        }
        scrollBar.withPosition(width() - 4, 0, true);
        scrollBar.withDimensions(4, height(), true);
        int trueHeight = maxColumnHeight + verticalPadding * 2;
        scrollBar.setMaxScrollAmount(trueHeight - height());
        if (trueHeight > height()) {
            scrollBar.setScaleFactor((float) height() / trueHeight);
            renderWidget(scrollBar, context, mouseX, mouseY);
        }
        for (List<Widget<?>> column : currentColumns) {
            int currentY = verticalPadding;
            int columnWidth = 0;
            for (Widget<?> widget : column) columnWidth = Math.max(columnWidth, widget.width());
            for (Widget<?> widget : column) {
                int offsetX = (columnWidth - widget.width()) / 2;
                offsetX = Math.max(0, offsetX + (width() - columnsWidth) / 2);
                widget.withPosition(currentX + offsetX, currentY - scrollBar.scrollAmount(), true);
                currentY += widget.height() + itemPadding;
            }

            currentX += columnWidth + columnPadding;
        }
        renderChildren(context, mouseX, mouseY);
    }

    @Override
    public VerticalListWidget getWidget() {
        return this;
    }

    @Override
    public VerticalListWidget addWidgets(Widget<?>... widgets) {
        currentColumn.addAll(Arrays.asList(widgets));
        return super.addWidgets(widgets);
    }

    public VerticalListWidget withPadding(int horizontalPadding, int verticalPadding, int itemPadding, int columnPadding) {
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        this.itemPadding = itemPadding;
        this.columnPadding = columnPadding;
        return getWidget();
    }

    @Override
    public void removeWidget(Widget<?> widget) {
        for (List<Widget<?>> column : columns) {
            column.remove(widget);
        }
        super.removeWidget(widget);
    }

    @Override
    public void clearWidgets() {
        columns.clear();
        currentColumn.clear();
        super.clearWidgets();
    }

    public VerticalListWidget newColumn() {
        columns.add(new ArrayList<>(currentColumn));
        currentColumn.clear();
        return getWidget();
    }

    @Override
    public void onScroll(int amount) {
        super.onScroll(amount);
        scrollBar.setScrollAmount(scrollBar.scrollAmount() - amount * 25);
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        super.onClick(mouseX, mouseY);
        scrollBar.onClick(mouseX, mouseY);
    }

    @Override
    public void onRelease(int mouseX, int mouseY) {
        super.onRelease(mouseX, mouseY);
        scrollBar.onRelease(mouseX, mouseY);
    }
}
