package com.bam.note_v2.edit.custom;

import androidx.annotation.NonNull;

public class StylizedChar extends NoteBodyElement{


    private TextStyle textStyle;

    public StylizedChar(String content, TextStyle textStyle) {
        super(content);
        this.textStyle = textStyle;
    }




    @NonNull
    @Override
    public String toString() {

        String result = super.getContent();

        if (textStyle.isBolt())  result = "<b>" + result + "</b>";
        if (textStyle.isItalic()) result = "<i>" + result + "</i>";
        if (textStyle.isUnder()) result = "<u>" + result + "</u>";

        return result;
    }
}
