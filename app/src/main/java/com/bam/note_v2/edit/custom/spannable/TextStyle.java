package com.bam.note_v2.edit.custom.spannable;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

public class TextStyle implements Serializable {
    private boolean __bolt, __italic, __under;



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

    public String toXml()
    {
        return MessageFormat.format(
                        "<style>" +
                                "<b>{1}</b>" +
                                "<i>{2}</i>" +
                                "<u>{3}</u>" +
                                "</style>" ,__bolt, __italic, __under);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextStyle textStyle = (TextStyle) o;
        return __bolt == textStyle.__bolt && __italic == textStyle.__italic && __under == textStyle.__under;
    }

    @Override
    public int hashCode() {
        return Objects.hash(__bolt, __italic, __under);
    }
}
