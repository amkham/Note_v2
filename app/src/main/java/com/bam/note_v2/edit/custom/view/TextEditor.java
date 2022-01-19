package com.bam.note_v2.edit.custom.view;

import com.bam.note_v2.edit.custom.spannable.SpannableElement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TextEditor {

    List<SpannableElement> noteText = new ArrayList<>();

    /**
     *
     */
    private SymbolChangeListener listener;

    public interface SymbolChangeListener
    {
        void change(List<SpannableElement> stylizedChars, int cursorPosition);
    }

    public void setListener(SymbolChangeListener listener) {
        this.listener = listener;
    }





    public void changeText(List<SpannableElement> symbols, int start, int end)
    {

        delete(start, end);
        add(symbols, start);

        int cursorPosition = start + symbols.size();

        listener.change(noteText, cursorPosition);
    }


    private void add(List<SpannableElement> symbols, int start)
    {
        for (int i = symbols.size() - 1; i > -1 ; i--)
        {
            noteText.add(start, symbols.get(i));
        }
    }

    public void add(SpannableElement element, int start)
    {
        noteText.add(start, element);
        listener.change(noteText, start+1);
    }

    private void delete(int start, int end)
    {

        if (noteText.size() > 0)
        {
            for (int i = start; i < end; i++)
            {
                noteText.remove(start);
            }

        }

    }



}
