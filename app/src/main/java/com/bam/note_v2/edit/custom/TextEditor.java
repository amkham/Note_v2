package com.bam.note_v2.edit.custom;

import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

public class TextEditor {

    List<SymbolStyle> noteText = new ArrayList<>();

    private NoteView mEditTextView;


    public TextEditor(NoteView editText)
    {
        mEditTextView = editText;
    }


    public void addChars(List<SymbolStyle> symbols, int start)
    {
       for (int i = symbols.size() - 1; i > -1 ; i--)
       {
           noteText.add(start, symbols.get(i));
       }
    }

    public void deleteChars(int start, int end)
    {

        if (noteText.size() > 0)
        {
            for (int i = start; i < end; i++)
            {
                noteText.remove(start);
            }

        }

    }


    public void changeText(List<SymbolStyle> symbols, int start, int end)
    {

        deleteChars(start, end);
        addChars(symbols, start);

        int cursorPosition = start + symbols.size();

        mEditTextView.updateText(getText(), cursorPosition);
    }


    public String getText()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (SymbolStyle s : noteText)
        {
            stringBuilder.append(s.toString());
        }

        return  stringBuilder.toString();
    }



}
