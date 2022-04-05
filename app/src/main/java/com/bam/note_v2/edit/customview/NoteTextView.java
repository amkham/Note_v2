package com.bam.note_v2.edit.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.bam.note_v2.R;
import com.bam.note_v2.edit.IObserver;
import com.bam.note_v2.edit.customview.spannable.SpannableChar;
import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.edit.customview.spannable.TextStyle;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class NoteTextView extends EditText implements INoteBodyElement, IObserver.StyleObserver {




    private List<SpannableElement> __spannableElements;


    private boolean __bolt = false, __under = false, __italic = false;
    private float __size = 1f;

    private String __backgroundColor = "#00FFFFFF";;


    private int __position;

    private String _oldText;

    private final TextWatcher __watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            _oldText = s.subSequence(start, start +count).toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                CharSequence sequence = s.subSequence(start, start + count);
                if (!_oldText.equals(sequence.toString()))
                {
                    replace(toSpannableElementsList(sequence), start, start + before);
                }





        }
        @Override
        public void afterTextChanged(Editable s) {
            System.out.println();
        }
    };


    public NoteTextView(@NonNull Context context) {
        super(context);
        setBackgroundResource(R.color.transparent);
       setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

    }


    @Override
    public int getPosition() {
        return __position;
    }

    public void setPosition(int position) {
        __position = position;
    }

    public void addElements(List<SpannableElement> elements)
    {
        __spannableElements.addAll(elements);
    }

    @Override
    public List<SpannableElement> getContent() {

        if (__spannableElements == null)
        {
            __spannableElements = new ArrayList<>();
        }

        return __spannableElements;
    }

    @Override
    public void setContent(List<SpannableElement> spannableElements) {
        __spannableElements = spannableElements;

        addTextChangedListener(__watcher);

        if (spannableElements.size() > 0)  {
            setInEditText(__spannableElements, spannableElements.size());
        }

    }



    @Override
    public void onBolt() {
        __bolt = !__bolt;
    }
    @Override
    public void onItalic() {
        __italic =!__italic;
    }
    @Override
    public void onUnder() {
        __under =!__under;
    }

    @Override
    public void changeSize(float size) {

        __size = size/14;
    }

    @Override
    public void changeBackground(String color) {

        __backgroundColor = color;
    }


    private void replace(List<SpannableChar> elements, int start, int end) {


            preparationList(elements.size(), start, end);

            int index = start;
            for (SpannableChar e : elements) {

                SpannableElement spannableElement = getContent().get(index);

                if (e.getValue().equals(spannableElement.getValue())) {
                    getContent().get(index).setValue(spannableElement.getValue());
                } else {
                    getContent().set(index, e);
                }
                index++;
            }

            setInEditText(getContent(), start + elements.size());

    }

    private void preparationList(int requiredSize, int start, int end) {

        int _size = end - start;

        while (requiredSize != _size) {
            if (requiredSize > _size) {
                TextStyle _textStyle = new TextStyle(__bolt, __italic, __under);
                _textStyle.setTextSize(__size);
                _textStyle.setBackgroundColor(__backgroundColor);
                getContent().add(end, new SpannableChar(" ", _textStyle));
                _size++;
            }

            if (requiredSize < _size) {
                getContent().remove(start + requiredSize);

                _size--;
            }
        }
    }

    private List<SpannableChar> toSpannableElementsList(CharSequence sequence) {
        List<SpannableChar> _result = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {

            TextStyle _textStyle = new TextStyle(__bolt, __italic, __under);
            _textStyle.setTextSize(__size);
            _textStyle.setBackgroundColor(__backgroundColor);
            _result.add(new SpannableChar(String.valueOf(sequence.charAt(i)), _textStyle));
        }

        return _result;
    }

    private void setInEditText(List<SpannableElement> elements, int position)
    {
        removeTextChangedListener(__watcher);
        SpannableStringBuilder _builder = convertToSpannableStringBuilder(elements);
        setText(_builder, BufferType.EDITABLE);
        addTextChangedListener(__watcher);
        setSelection(position);

    }

    private SpannableStringBuilder convertToSpannableStringBuilder(List<SpannableElement> elements)
    {
        SpannableStringBuilder _builder = new SpannableStringBuilder();
        for (SpannableElement e : elements) {
            _builder.append(e.getSpan());
        }
        return _builder;
    }




}
