package com.bam.note_v2.edit;

public interface IObserver {

    interface StyleObserver {
        void onBolt();
        void onItalic();
        void onUnder();
        void changeSize(float size);
        void changeBackground(String color);
    }


    enum Style
    {
        BOLT, UNDER, ITALIC, SIZE, TRANSPARENT;
    }

    enum Colors
    {
        RED("#FFF44336"), BLUE("#FF2196F3"), GREEN("#FF4CAF50"), PURPLE("#FFBB86FC"), ORANGE("#FFFF9800"), TRANSPARENT("#00FFFFFF");

        private final String __value;

        Colors(String value) {

            __value = value;
        }

        public String getValue() {
            return __value;
        }
    }
}
