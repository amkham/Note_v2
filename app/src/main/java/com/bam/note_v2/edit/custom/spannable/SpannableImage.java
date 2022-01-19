package com.bam.note_v2.edit.custom.spannable;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.bam.note_v2.edit.custom.spannable.SpannableElement;

public class SpannableImage extends SpannableElement {

    private Context context;

    public SpannableImage(Context context, Uri uri)
    {
        super(uri.toString());
        this.context = context;
    }


    @Override
    public SpannableString getSpan() {

        SpannableString spannableString = new SpannableString("i");
        ImageSpan imageSpan = new ImageSpan(context, Uri.parse(super.getContent()));

        spannableString.setSpan(imageSpan, 0 ,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
