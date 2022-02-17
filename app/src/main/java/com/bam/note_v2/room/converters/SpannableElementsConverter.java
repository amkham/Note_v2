package com.bam.note_v2.room.converters;


import android.content.Context;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.utils.NoteStructureParser;

import java.util.List;

@ProvidedTypeConverter
public class SpannableElementsConverter {

    private final Context context;

    public SpannableElementsConverter(Context context) {
        this.context = context;
    }

    @TypeConverter
    public String fromList(List<SpannableElement> elements)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<body>");
        for(SpannableElement e: elements)
        {
           stringBuilder.append(e.getXml());
        }
        stringBuilder.append("</body>");

        return  stringBuilder.toString();
    }

    @TypeConverter
    public List<SpannableElement> fromString(String xml) {
        NoteStructureParser parser = new NoteStructureParser();
        return parser.parse(xml);
    }
}
