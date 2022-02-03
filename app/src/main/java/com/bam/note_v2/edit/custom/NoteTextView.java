package com.bam.note_v2.edit.custom;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bam.note_v2.edit.custom.spannable.SpannableChar;
import com.bam.note_v2.edit.custom.spannable.TextStyle;

import java.util.ArrayList;
import java.util.List;

public class NoteTextView extends androidx.appcompat.widget.AppCompatEditText implements INoteBodyElement {


    private List<SpannableChar> __spannableElements = new ArrayList<>();

    private final TextStyle __textStyle = new TextStyle();

    private final TextWatcher __watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (before < count) {
                CharSequence sequence = s.subSequence(start, start + count);
                replace(toSpannableElementsList(sequence), start, start + before);
            }
            if (before > count) {
                CharSequence subSequence = s.subSequence(start, start + count);
                replace(toSpannableElementsList(subSequence), start, start + before);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    {
        addTextChangedListener(__watcher);
    }

    public NoteTextView(@NonNull Context context) {
        super(context);
    }

    public List<SpannableChar> getSpannableElements() {
        return __spannableElements;
    }

    public void setSpannableElements(List<SpannableChar> spannableElements) {
        __spannableElements = spannableElements;
        setInEditText(__spannableElements);

    }

    public void onBolt() {
        __textStyle.onBolt();
    }
    public void onItalic() {
        __textStyle.onItalic();
    }
    public void onUnder() {
        __textStyle.onUnder();
    }


    @Override
    public String getXml() {

        StringBuilder _stringBuilder = new StringBuilder();

        for (SpannableChar c : __spannableElements)
        {
            _stringBuilder.append(c.getXml());
        }
        return _stringBuilder.toString();
    }


    private void replace(List<SpannableChar> elements, int start, int end) {

        preparationList(elements.size(), start, end);

        int index = start;
        for (SpannableChar e : elements) {

            SpannableChar spannableElement = __spannableElements.get(index);

            if (e.getValue().equals(spannableElement.getValue())) {
                __spannableElements.get(index).setValue(spannableElement.getValue());
            } else {
                __spannableElements.set(index, e);
            }
            index++;
        }

        setInEditText(__spannableElements);

    }

    private void preparationList(int requiredSize, int start, int end) {

        int _size = end - start;

        while (requiredSize != _size) {
            if (requiredSize > _size) {
                __spannableElements.add(end, new SpannableChar("", __textStyle));
                _size++;
            }

            if (requiredSize < _size) {
                __spannableElements.remove(start + requiredSize);
                _size--;
            }
        }
    }

    private List<SpannableChar> toSpannableElementsList(CharSequence sequence) {
        List<SpannableChar> _result = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {

            _result.add(new SpannableChar(String.valueOf(sequence.charAt(i)), __textStyle));
        }

        return _result;
    }

    private void setInEditText(List<SpannableChar> elements)
    {
        SpannableStringBuilder _builder = convertToSpannableStringBuilder(elements);

       removeTextChangedListener(__watcher);
        setText(_builder, TextView.BufferType.SPANNABLE);
        setSelection(_builder.length());
        addTextChangedListener(__watcher);
    }

    private SpannableStringBuilder convertToSpannableStringBuilder(List<SpannableChar> elements)
    {
        SpannableStringBuilder _builder = new SpannableStringBuilder();
        for (SpannableChar e : elements) {
            _builder.append(e.getSpan());
        }
        return _builder;
    }


}
