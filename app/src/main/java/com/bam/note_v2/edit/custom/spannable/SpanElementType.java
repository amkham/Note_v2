package com.bam.note_v2.edit.custom.spannable;

public enum SpanElementType {

    IMAGE("img"), TEXT("text");

    private final String __value;

    SpanElementType(String value) {
        __value = value;
    }

    public String getValue() {
        return __value;
    }
}
