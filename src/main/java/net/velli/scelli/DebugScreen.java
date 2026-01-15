package net.velli.scelli;

import net.minecraft.client.gui.screen.Screen;
import net.velli.scelli.screen.WidgetContainerScreen;
import net.velli.scelli.widget.widgets.ButtonWidget;

public class DebugScreen extends WidgetContainerScreen {
    protected DebugScreen(Screen previousScreen) {
        super(previousScreen);
        this.addWidgets(
                ButtonWidget.create()
        );
    }
}
