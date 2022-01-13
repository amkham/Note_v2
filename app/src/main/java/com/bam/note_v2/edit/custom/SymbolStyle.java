package com.bam.note_v2.edit.custom;

import androidx.annotation.NonNull;

public class SymbolStyle {

    private char symbol;

    private TextStyle textStyle;

    public SymbolStyle(char symbol, TextStyle textStyle) {
        this.symbol = symbol;
        this.textStyle = textStyle;
    }

    public SymbolStyle()
    {}

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }


    @NonNull
    @Override
    public String toString() {

        String result = String.valueOf(symbol);

        if (textStyle.isBolt())  result = "<b>" + result + "</b>";
        if (textStyle.isItalic()) result = "<i>" + result + "</i>";
        if (textStyle.isUnder()) result = "<u>" + result + "</u>";

        return result;
    }
}
