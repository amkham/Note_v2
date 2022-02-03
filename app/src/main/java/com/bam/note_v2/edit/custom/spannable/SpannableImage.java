package com.bam.note_v2.edit.custom.spannable;

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
    public String getText() {
        return "";
    }
}
