package com.bam.note_v2.edit.customview.spannable;

import android.text.SpannableString;

import java.text.MessageFormat;

public class SpannableImage extends SpannableElement{


    public SpannableImage(String fileName) {
        super(fileName, SpanElementType.IMAGE);
    }

    @Override
    public String getXml() {
        return  MessageFormat.format(
                "<img>{0}</img>",getValue());
    }

    @Override
    public SpannableString getSpan() {
        return null;
    }

    @Override
    public String getText() {
        return "";
    }
}
