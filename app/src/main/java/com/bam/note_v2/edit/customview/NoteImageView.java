package com.bam.note_v2.edit.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.edit.customview.spannable.SpannableImage;
import com.bam.note_v2.utils.ImageUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteImageView extends androidx.appcompat.widget.AppCompatImageView implements Serializable, INoteBodyElement{

    private SpannableElement __spannableElement;


    private int __position;




    @Override
    public List<SpannableElement> getContent() {

        if (__spannableElement == null)
        {
            __spannableElement = new SpannableImage("i");
        }

        List<SpannableElement> _result = new ArrayList<>();
        _result.add(__spannableElement);
        return _result;
    }

    @Override
    public void setContent(List<SpannableElement> elements) {

        __spannableElement = elements.get(0);
    }


    public NoteImageView(Context context)
    {
        super(context);
        RelativeLayout.LayoutParams _layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundColor(Color.WHITE);
        setLayoutParams(_layoutParams);
        setScaleType(ScaleType.CENTER_INSIDE);
        setAdjustViewBounds(true);
    }



    public void setSpannableImage(SpannableElement element) throws IOException {
        __spannableElement = element;

        setImageBitmap(ImageUtils.loadImg(getContext(), __spannableElement.getValue()));
    }

    public int getPosition() {
        return __position;
    }

    public void setPosition(int position) {
        __position = position;
    }
}
