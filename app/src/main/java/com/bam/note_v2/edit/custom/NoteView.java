package com.bam.note_v2.edit.custom;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends androidx.appcompat.widget.AppCompatEditText {


    private final TextEditor mTextEditor;

    boolean bolt, italic, under;

    boolean changeStatus;



    public NoteView(Context context) {
        super(context);

        changeStatus = false;
        mTextEditor = new TextEditor(this);

        this.addTextChangedListener(initTextWatcher());

        this.setBackground(null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        this.setPadding(5,5,5,5);

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


    public String getStylizedText()
    {
        return mTextEditor.getHtmlText();
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


    private NoteTextWatcher initTextWatcher()
    {
        NoteTextWatcher noteTextWatcher = new NoteTextWatcher();
        noteTextWatcher.setListener(new NoteTextWatcher.NoteTextChangeListener() {
            @Override
            public void change(CharSequence sequence, int start, int end) {
                mTextEditor.changeText(toList(sequence), start, end);
            }
        });

        return noteTextWatcher;
    }

    private List<StylizedChar> toList(CharSequence sequence)
    {
        List<StylizedChar> result = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++)
        {
            result.add(new StylizedChar(String.valueOf(sequence.charAt(i)), new TextStyle(bolt, italic, under)));
        }

        return result;
    }


    }
