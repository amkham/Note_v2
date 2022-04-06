package com.bam.note_v2.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bam.note_v2.R;

public class TextSizeMenuAdapter extends ArrayAdapter<String> {

    private final String[] __strings;
    private int __selectedItem = 1;

    public TextSizeMenuAdapter(@NonNull Context context, int resource, @NonNull String[] strings, int selectedItem) {
        super(context, resource, strings);

        __strings = strings;
        __selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater _layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _view = _layoutInflater.inflate(R.layout.text_size_menu_item, parent, false);
        TextView _textView = _view.findViewById(R.id.text_size_menu_item);
        _textView.setText(__strings[position]);

        if (position == __selectedItem){
            _textView.setBackgroundColor(getContext().getColor(R.color.teal_700));
        }

        return _view;

    }

}
