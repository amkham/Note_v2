package com.bam.note_v2.edit.customview.spannable;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

public class TextStyle implements Serializable {
    private boolean __bolt, __italic, __under;
    private float  __textSize = 1f;
    private String __backgroundColor = "#00FFFFFF";


    public TextStyle(boolean bolt, boolean italic, boolean under) {
        __bolt = bolt;
        __italic = italic;
        __under = under;
    }

    public TextStyle()
    {
        __bolt = __italic = __under = false;
    }



    public boolean isBolt() {
        return __bolt;
    }


    public boolean isItalic() {
        return __italic;
    }


    public boolean isUnder() {
        return __under;
    }

    public void onBolt() {
        __bolt = !__bolt;
    }
    public void onItalic() {
        __italic = !__italic;
    }
    public void onUnder() {
        __under = !__under;
    }

    public float getTextSize() {
        return __textSize;
    }

    public String getBackgroundColor() {
        return __backgroundColor;
    }

    public void setTextSize(float textSize) {
        __textSize = textSize;
    }

    public void setBackgroundColor(String backgroundColor) {
        __backgroundColor = backgroundColor;
    }

    public String toXml()
    {
        return MessageFormat.format(
                        "<style>" +
                                "<b>{0}</b>" +
                                "<i>{1}</i>" +
                                "<u>{2}</u>" +
                                "<size>{3}</size>" +
                                "<bcolor>{4}</bcolor>"+
                                "</style>" ,__bolt, __italic, __under, String.valueOf(__textSize), __backgroundColor);
    }


    @Override
    public boolean equals(Object _o) {
        if (this == _o) return true;
        if (_o == null || getClass() != _o.getClass()) return false;
        TextStyle _that = (TextStyle) _o;
        return __bolt == _that.__bolt && __italic == _that.__italic && __under == _that.__under &&
                Float.compare(_that.__textSize, __textSize) == 0 && Objects.equals(__backgroundColor, _that.__backgroundColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(__bolt, __italic, __under, __textSize, __backgroundColor);
    }
}
