package com.bam.note_v2.edit.customview;

import com.bam.note_v2.edit.customview.spannable.SpannableElement;

import java.util.List;

public interface INoteBodyElement  {

    List<SpannableElement> getContent();

    void setContent(List<SpannableElement> elements);

    int getPosition();
}
