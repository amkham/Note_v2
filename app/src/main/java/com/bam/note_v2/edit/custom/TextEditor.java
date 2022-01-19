package com.bam.note_v2.edit.custom;

import java.util.ArrayList;
import java.util.List;

public class TextEditor {

    List<StylizedChar> noteText = new ArrayList<>();

    private NoteView mEditTextView;


    public TextEditor(NoteView editText)
    {
        mEditTextView = editText;
    }


    public void addChars(List<StylizedChar> symbols, int start)
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


    public void changeText(List<StylizedChar> symbols, int start, int end)
    {

        deleteChars(start, end);
        addChars(symbols, start);

        int cursorPosition = start + symbols.size();

        mEditTextView.updateText(getHtmlText(), cursorPosition);
    }


    public String getHtmlText()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (StylizedChar s : noteText)
        {
            stringBuilder.append(s.toString());
        }

        return  stringBuilder.toString();
    }


}
