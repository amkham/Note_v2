package com.bam.note_v2.edit.custom.spannable;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

public abstract class SpannableElement {

    private String mContent;

    public SpannableElement(String element)
    {
        this.mContent = element;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public abstract SpannableString getSpan();
}
