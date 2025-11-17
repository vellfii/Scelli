package net.velli.scelli.widget;

public class StringInputData implements Cloneable {
    public String string = "";
    public int index = 0;
    public int selectionStart = 0;
    public int selectionEnd = 0;

    @Override
    public StringInputData clone() {
        try {
            return (StringInputData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

}
