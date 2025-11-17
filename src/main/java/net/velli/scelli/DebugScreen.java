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
            ListWidget.create().addWidgets(
                    ListWidget.create().addWidgets(
                            ButtonWidget.create().withDimensions(0, 50, true).withText(Text.literal("this is a\ntest of the text!")),
                            StringInputWidget.create(),
                            NumInputWidget.create(),
                            SliderWidget.create().withThresholds(-50, 50),
                            ListWidget.create().addWidgets(
                                    ButtonWidget.create().withDimensions(0, 10, true),
                                    TextWidget.create().withText(Text.literal("this is a\ntest of the\ntext widget! also im going to make this suuuper long to see if it's possible for me to wrap the lines and such teehee"))
                                    .withPadding(0)
                            )
                            .withDimensions(0, 100, true)
                    )
                    .withAnimationSpeed(100f)
                    .withDimensions(0, 200, true)
            )
            .withAnimationSpeed(1f)
            .withPosition(0, 500, true)
            .withPosition(0, 0, false)
            .withPadding(5, 20, 2)
            .withDimensions(300, 150, true)
            .withAlignment(WidgetPos.Alignment.CENTER)
        );
    }
}
