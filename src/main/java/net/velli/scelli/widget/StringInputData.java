package net.velli.scelli.widget;

public class StringInputData {
    public String string = "";
    public int index = 0;
    public int selectionStart = 0;
    public int selectionEnd = 0;

    public StringInputData clone() {
        StringInputData data = new StringInputData();
        data.string = string;
        data.index = index;
        data.selectionStart = selectionStart;
        data.selectionEnd = selectionEnd;
        return data;
    }
}
