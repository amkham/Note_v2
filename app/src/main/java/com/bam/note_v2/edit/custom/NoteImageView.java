package com.bam.note_v2.edit.custom;

import android.content.Context;

import com.bam.note_v2.edit.custom.spannable.SpannableImage;
import com.bam.note_v2.utils.ImageUtils;

import java.io.IOException;
import java.io.Serializable;

public class NoteImageView extends androidx.appcompat.widget.AppCompatImageView implements Serializable, INoteBodyElement {

    private SpannableImage __spannableImage;

    public NoteImageView(Context context)
    {
        super(context);
    }

    public SpannableImage getSpannableImage() {
        return __spannableImage;
    }

    public void setSpannableImage(SpannableImage spannableImage) throws IOException {
        __spannableImage = spannableImage;

        setImageBitmap(ImageUtils.loadImg(super.getContext(), spannableImage.getValue()));

    }


    @Override
    public String getXml() {

        return __spannableImage.getXml();
    }
}
