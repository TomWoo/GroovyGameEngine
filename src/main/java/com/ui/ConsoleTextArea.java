package com.ui;

//import javafx.beans.DefaultProperty;
//import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

/**
 * Created by Tom on 6/24/2017.
 */
// Reference: http://stackoverflow.com/questions/29699040/javafx-how-to-restrict-manipulation-of-textarea-to-last-row
public class ConsoleTextArea extends TextArea { // TODO: use rich-text, GroovyFX
//    public ConsoleTextArea() {
//        super();
//        setFont(Font.font("Consolas"));
//    }

    @Override
    public void replaceText(int start, int end, String text) {
        String current = getText();
        // only insert if no new lines after insert position:
        if (!current.substring(start).contains("\n")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        String current = getText();
        int selectionStart = getSelection().getStart();
        if (!current.substring(selectionStart).contains("\n")) {
            super.replaceSelection(text);
        }
    }

    public String getLastLine() {
        String[] lines = this.getText().split("\n");
        return lines[lines.length-1];
    }

    public void print(String text) {
        this.appendText(text);
    }

    public void println(String text) {
        this.print(text + "\n");
    }

    public void println() {
        this.println("");
    }
}
