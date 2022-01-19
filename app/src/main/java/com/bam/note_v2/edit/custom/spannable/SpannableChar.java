package com.bam.note_v2.edit.custom.spannable;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import androidx.annotation.NonNull;

import com.bam.note_v2.edit.custom.view.TextStyle;

public class SpannableChar extends SpannableElement {


    private TextStyle textStyle;

    public SpannableChar(String content, TextStyle textStyle) {
        super(content);
        this.textStyle = textStyle;
    }


    public void setTextStyle(TextStyle textStyle) {
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

    @Override
    public SpannableString getSpan() {

        SpannableString spannableString = new SpannableString(super.getContent());


        if (textStyle.isBolt()) spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (textStyle.isItalic()) spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (textStyle.isUnder()) spannableString.setSpan(new UnderlineSpan(), 0 , 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
