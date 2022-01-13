package com.bam.note_v2.edit.custom;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends androidx.appcompat.widget.AppCompatEditText {


    private final TextEditor mTextEditor;

    boolean bolt, italic, under;

    boolean changeStatus;



    public NoteView(Context context) {
        super(context);

        mTextEditor = new TextEditor(this);
        this.addTextChangedListener(textWatcher);

    }


    public void onBolt() {
        bolt = !bolt;
    }


    public void onItalic() {
        italic = !italic;
    }


    public void onUnder() {
        under = !under;
    }

    



    public void  updateText(String txt, int cursorPosition)
    {
        changeStatus = true;
        this.setText(Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT));
        this.setSelection(cursorPosition);
    }




    TextWatcher textWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!changeStatus) {


                if (before < count) {
                    CharSequence txt = s.subSequence(start + before, start + count);
                    mTextEditor.changeText(toList(txt), start, before);

                }

                if (before > count) {
                    CharSequence txt = s.subSequence(start, start + count);
                    mTextEditor.changeText(toList(txt), start, before);

                }

            }

            changeStatus = false;

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private List<SymbolStyle> toList(CharSequence sequence)
    {
        List<SymbolStyle> result = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++)
        {
            result.add(new SymbolStyle(sequence.charAt(i), new TextStyle(bolt, italic, under)));
        }

        return result;
    }


    }
