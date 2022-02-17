package com.bam.note_v2.edit.customview.spannable;

import android.text.SpannableString;

public abstract class SpannableElement {

    private String __value;
    private SpanElementType __type;


    public SpannableElement(String value, SpanElementType type) {

        __type = type;
        __value = value;
    }

    public String getValue() {
        return __value;
    }

    public void setValue(String value) {
        __value = value;
    }

    public SpanElementType getType() {
        return __type;
    }

    public void setType(SpanElementType type) {
        __type = type;
    }

    abstract public String getXml();

    abstract public SpannableString getSpan();

    public String getText() {
        return __value;
    }
}
