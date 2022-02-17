package com.bam.note_v2.utils;

import android.util.Xml;

import com.bam.note_v2.edit.customview.spannable.SpannableChar;
import com.bam.note_v2.edit.customview.spannable.SpannableElement;
import com.bam.note_v2.edit.customview.spannable.SpannableImage;
import com.bam.note_v2.edit.customview.spannable.TextStyle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class NoteStructureParser {


    private static final String ns = null;



    public List<SpannableElement> parse(String xml)  {

        try {
            StringReader stringReader = new StringReader(xml);
            XmlPullParser parser = Xml.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stringReader);
            parser.nextTag();
            return readFeed(parser);
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<SpannableElement> readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {

       List<SpannableElement> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "body");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()){
                case "char":  entries.add(readChar(parser));break;
                case "img":  entries.add(readImg(parser)); break;
            }
        }
        return entries;
    }


    private SpannableElement readImg(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "img");
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "img");

        return new SpannableImage(value);

    }



    private SpannableElement readChar(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "char");

        String value = "";
        TextStyle textStyle = new TextStyle();


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()){
                case "value":  value = readValue(parser); break;
                case "style":  textStyle = readTextStyle(parser); break;
            }
        }
        return new SpannableChar(value, textStyle);
    }

    private String readValue(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "value");
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "value");
        return value;
    }

    private TextStyle readTextStyle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "style");

        boolean bolt = false, italic = false, under = false;
        float size = 1f;
        String b_color = "#00FFFFFF";

        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()){
                case "b": bolt = readBolt(parser); break;
                case "i": italic = readItalic(parser); break;
                case "u": under = readUnder(parser); break;
                case "size": size = readSize(parser); break;
                case "bcolor": b_color = readBackgroundColor(parser); break;
            }

        }

        TextStyle _textStyle = new TextStyle(bolt, italic, under);
        _textStyle.setTextSize(size);
        _textStyle.setBackgroundColor(b_color);
        return  _textStyle;
    }

    private String readBackgroundColor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "bcolor");
        String back = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "bcolor");
        return back;
    }

    private float readSize(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "size");
        float size = Float.parseFloat(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "size");
        return size;
    }

    private boolean readUnder(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "u");
        boolean under = Boolean.parseBoolean(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "u");
        return under;
    }

    private boolean readItalic(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "i");
        boolean bolt = Boolean.parseBoolean(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "i");
        return bolt;
    }

    private boolean readBolt(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "b");
        boolean bolt = Boolean.parseBoolean(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "b");
        return bolt;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }




}
