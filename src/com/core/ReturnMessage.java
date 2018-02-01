package com.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 6/9/2017.
 */
public class ReturnMessage implements IReturnMessage {
    private int length = 0;
    private List<String> infoLog = new ArrayList<>();
    private List<String> errorLog = new ArrayList<>();

    public ReturnMessage() {}

    public ReturnMessage(String info, String error) {
        this.infoLog.add(info);
        this.errorLog.add(error);
    }

    @Override
    public List<String> getInfo() {
        return new ArrayList<>(infoLog);
    }

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errorLog);
    }

//    @Override
//    public void append(IReturnMessage returnMessage) {
//        append(returnMessage.getInfo(), returnMessage.getErrors());
//    }

    @Override
    public void append(String info, String error) {
        infoLog.add(info);
        errorLog.add(error);
        length++;
    }

    @Override
    public void appendInfo(String info) {
        append(info, "");
    }

    @Override
    public void appendError(String error) {
        append("", error);
    }

    @Override
    public void clear() {
        infoLog.clear();
        errorLog.clear();
        length = 0;
    }

    @Override
    public String toString() {
        String info = length>0 ? ("Info list:\n" + String.join("\n", infoLog) + "\n") : "";
        String errors = length>0 ? ("Error list:\n" + String.join("\n", errorLog) + "\n") : "";
        return "EVAL\n" + info + errors;
    }
}
