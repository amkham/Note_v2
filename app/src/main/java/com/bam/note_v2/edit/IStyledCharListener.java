package com.bam.note_v2.edit;

public interface IStyledCharListener {

    void register(IObserver.StyleObserver o);
    void remove(IObserver.StyleObserver o);
    void notifyObservers(IObserver.Style style);
    void notifyObservers(IObserver.Colors color);

}
