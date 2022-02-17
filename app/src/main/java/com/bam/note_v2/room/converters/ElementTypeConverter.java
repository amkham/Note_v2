package com.bam.note_v2.room.converters;

import androidx.room.TypeConverter;

import com.bam.note_v2.edit.customview.spannable.SpanElementType;

public class ElementTypeConverter {

    @TypeConverter
    public String toString(SpanElementType type)
    {
        return type.getValue();
    }


    @TypeConverter
    public SpanElementType toEnum(String str)
    {
        switch (str){
            case "img": return SpanElementType.IMAGE;
            case "text": return SpanElementType.TEXT;
            default: return null;
        }
    }
}
