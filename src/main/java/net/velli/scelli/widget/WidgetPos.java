package net.velli.scelli.widget;

import net.minecraft.client.gui.screen.Screen;
import net.velli.scelli.Scelli;
import net.velli.scelli.widget.containers.WidgetContainer;
import org.joml.Vector2f;

public class WidgetPos {
    public enum Alignment {
        TOPLEFT(""), TOP("hcenter"), TOPRIGHT("right"),
        LEFT("vcenter"), CENTER("hcenter_vcenter"), RIGHT("right_vcenter"),
        BOTTOMLEFT("bottom"), BOTTOM("bottom_hcenter"), BOTTOMRIGHT("bottom_right");
        private final String id;
        Alignment(String id) { this.id = id; }
        public String getId() { return this.id; }
    }
    public float x = 0, y = 0, renderX = 0, renderY = 0;
    public int width = 16, height = 16;
    public float renderWidth = 16, renderHeight = 16;
    public Alignment alignment = Alignment.TOPLEFT;
    public boolean hovered = false;
    public int opacity = 255;
    public float renderOpacity = 255;
    public float animationSpeed = 1;

    public Vector2f alignmentOffsets(WidgetContainer<?> renderer) {
        Vector2f result = new Vector2f(0, 0);
        Vector2f parentDimensions = new Vector2f(renderer.renderedWidth(), renderer.renderedHeight());
        if (alignment.getId().contains("hcenter")) result.x = parentDimensions.x / 2 - renderWidth / 2;
        if (alignment.getId().contains("right")) result.x = parentDimensions.x - renderWidth;
        if (alignment.getId().contains("vcenter")) result.y = parentDimensions.y / 2 - renderHeight / 2;
        if (alignment.getId().contains("bottom")) result.y = parentDimensions.y - renderHeight;
        return result;
    }
}
