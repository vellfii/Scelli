package net.velli.scelli;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.velli.scelli.screen.WidgetContainerScreen;
import net.velli.scelli.widget.*;
import net.velli.scelli.widget.widgets.containers.FolderWidget;
import net.velli.scelli.widget.widgets.containers.ListWidget;
import net.velli.scelli.widget.widgets.*;

public class DebugScreen extends WidgetContainerScreen {
    protected DebugScreen(Screen previousScreen) {
        super(previousScreen);
        addWidgets(
                ListWidget.create()
                        .addWidgets(
                                TextWidget.create().withText(Text.literal("hey im a text widget")).withAlignment(TextWidget.TextAlignment.CENTER),
                                ButtonWidget.create().withText(Text.literal("button :3")).withDimensions(0, 50, true),
                                SliderWidget.create().withThresholds(-10, 10),
                                FolderWidget.create(false).withTitle(Text.literal("folder widget title! wow!"))
                                        .addWidgets(
                                                ButtonWidget.create(),
                                                ButtonWidget.create()
                                        )
                        )
                        .withAlignment(WidgetPos.Alignment.CENTER)
                        .withDimensions(200, 150, true),
                ListWidget.create()
                        .addWidgets(
                                TextWidget.create().withText(Text.literal("im text but on the right!")).withAlignment(TextWidget.TextAlignment.CENTER),
                                ButtonWidget.create().withText(Text.literal("button but right hehe :3")).withDimensions(0, 20, true)
                        )
                        .withAlignment(WidgetPos.Alignment.RIGHT)
                        .withDimensions(150, 55, true),
                ListWidget.create()
                        .addWidgets(
                                TextWidget.create().withText(Text.literal("some kinda title text up here maybe"))
                        )
                        .withAlignment(WidgetPos.Alignment.TOP)
                        .withDimensions(200, 30, true)
        );
    }
}
