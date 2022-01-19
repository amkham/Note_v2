package com.bam.note_v2.edit.custom;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.List;

public class NoteTextWatcher implements TextWatcher {


    private NoteTextChangeListener listener;

    public interface NoteTextChangeListener
    {
        void change(CharSequence sequence, int start, int end);
    }

    public void setListener(NoteTextChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


            if (before < count) {
                CharSequence subSequence = s.subSequence(start + before, start + count);
                listener.change(subSequence, start, before);

            }

            if (before > count) {
                CharSequence subSequence = s.subSequence(start, start + count);
                listener.change(subSequence, start, before);

            }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }



}
