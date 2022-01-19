package com.bam.note_v2.edit.custom;

public abstract class NoteBodyElement {

    private String mContent;

    public NoteBodyElement(String element)
    {
        this.mContent = element;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
