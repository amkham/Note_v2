package com.bam.note_v2.edit.custom.spannable;

public abstract class SpannableElement {

    private String __value;
    private SpanElementType __type;


    public SpannableElement(String value, SpanElementType type) {

        __value = value;
        __type = type;
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

    abstract public String getText();
}
