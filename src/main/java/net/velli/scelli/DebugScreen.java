package net.velli.scelli;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.velli.scelli.screen.WidgetContainerScreen;
import net.velli.scelli.widget.widgets.Alignment;
import net.velli.scelli.widget.widgets.ButtonWidget;
import net.velli.scelli.widget.widgets.TextDisplayWidget;
import net.velli.scelli.widget.widgets.Widgets;
import net.velli.scelli.widget.widgets.containers.VerticalListWidget;

public class DebugScreen extends WidgetContainerScreen {
    protected DebugScreen(Screen previousScreen) {
        super(previousScreen);
        this.addWidgets(
                Widgets.create(VerticalListWidget::new, 0, 0, 350, 250)
                        .addWidgets(
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Displays")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20).withText(Text.literal("button")),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Screens")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20)
                                ).newColumn()
                        .addWidgets(
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Options")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Misc")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 50, 20)
                        ).newColumn()
                        .addWidgets(
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Options")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(TextDisplayWidget::new, 0, 0, 75, 10)
                                        .addLine(Text.literal("Misc")).withTextAlignment(Alignment.CENTER),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20),
                                Widgets.create(ButtonWidget::new, -8, 0, 65, 20)
                        )
                        .withAlignment(Alignment.CENTER)
                        .withPadding(0, 5, 2, 4)


        );
    }
}
