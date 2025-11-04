package net.velli.scelli;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ScelliUtil {
    private static List<Text> newlineResult = new ArrayList<>();
    private static MutableText newlineCurrentStrand = Text.empty();

    public static List<Text> splitTextNewline(Text text) {
        if (!text.copyContentOnly().getString().isEmpty()) text = Text.empty().append(text);
        newlineResult = new ArrayList<>();
        List<Text> siblings = new ArrayList<>(text.getSiblings());
        newlineCurrentStrand = Text.empty();
        for (Text sibling : siblings) {
            newlineWrapLogic(sibling, sibling.copyContentOnly().getString());
            for (Text siblingin : sibling.getSiblings()) {
                newlineWrapLogic(siblingin, siblingin.getString());
            }
        }
        newlineResult.add(newlineCurrentStrand);
        return newlineResult;
    }

    private static void newlineWrapLogic(Text text, String partsString) {
        String[] parts = partsString.split("\n", -1);
        Style style = text.getStyle();
        List<String> partsList = new ArrayList<>(List.of(parts));
        for (String part : partsList) {
            if (partsList.indexOf(part) != 0) {
                newlineResult.add(newlineCurrentStrand);
                newlineCurrentStrand = Text.empty();
            }
            newlineCurrentStrand.append(Text.literal(part).setStyle(style));
        }
    }

    public static float lerp(float a, float b, float t) {
        t = Math.clamp(t, 0, 1);
        return a + t * (b - a);
    }
}
