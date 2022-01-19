package com.bam.note_v2.edit.custom.view;

public class TextStyle {
    private boolean bolt, italic, under;

    public TextStyle(boolean bolt, boolean italic, boolean under) {
        this.bolt = bolt;
        this.italic = italic;
        this.under = under;
    }



    public boolean isBolt() {
        return bolt;
    }


    public boolean isItalic() {
        return italic;
    }


    public boolean isUnder() {
        return under;
    }

}
