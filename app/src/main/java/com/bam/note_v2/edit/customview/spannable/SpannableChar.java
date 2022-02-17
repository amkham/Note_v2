package com.bam.note_v2.edit.customview.spannable;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

public class SpannableChar extends SpannableElement implements Serializable {


    private final TextStyle __textStyle;


    public SpannableChar(String value, TextStyle textStyle) {
        super(value, SpanElementType.TEXT);
        __textStyle = textStyle;
    }


    public SpannableString getSpan() {

        SpannableString spannableString = new SpannableString(getValue());


        if (__textStyle.isBolt()) spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (__textStyle.isItalic()) spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (__textStyle.isUnder()) spannableString.setSpan(new UnderlineSpan(), 0 , 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new RelativeSizeSpan(__textStyle.getTextSize()), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BackgroundColorSpan(Color.parseColor(__textStyle.getBackgroundColor())), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpannableChar that = (SpannableChar) o;
        return __textStyle.equals(that.__textStyle) && super.getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(__textStyle);
    }

    public String getXml() {
        return MessageFormat.format(
                "<char>" +
                        "<value>{0}</value> " +
                        __textStyle.toXml() +
                        "</char>", super.getValue());
    }



}
