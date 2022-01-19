package com.bam.note_v2.edit.custom.view;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bam.note_v2.edit.custom.spannable.SpannableChar;
import com.bam.note_v2.edit.custom.spannable.SpannableElement;
import com.bam.note_v2.edit.custom.spannable.SpannableImage;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends androidx.appcompat.widget.AppCompatEditText {


    private TextEditor mTextEditor;

    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

    boolean bolt, italic, under;



    public NoteView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.setText(spannableStringBuilder);
        this.addTextChangedListener(initTextWatcher());
        initTextEditor();



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


    public void setImage(SpannableElement image, int start)
    {
        mTextEditor.add(image, start);
    }



    private void initTextEditor() {
        mTextEditor = new TextEditor();
        mTextEditor.setListener((stylizedChars, cursorPosition) -> {
            spannableStringBuilder.clear();
            for (SpannableElement e : stylizedChars)
            {
                spannableStringBuilder.append(e.getSpan());
            }

            this.setText(spannableStringBuilder, BufferType.EDITABLE);
            this.setSelection(cursorPosition);

        });
    }

    private NoteTextWatcher initTextWatcher() {
        NoteTextWatcher noteTextWatcher = new NoteTextWatcher();
        noteTextWatcher.setListener((sequence, start, end) -> mTextEditor.changeText(toList(sequence), start, end));

        return noteTextWatcher;
    }

    private List<SpannableElement> toList(CharSequence sequence) {
        List<SpannableElement> result = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {
            result.add(new SpannableChar(String.valueOf(sequence.charAt(i)), new TextStyle(bolt, italic, under)));
        }

        return result;
    }


    private SpannableStringBuilder createSpannableText(List<SpannableElement> list) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        return null;
    }

}
