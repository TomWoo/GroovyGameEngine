package com.core;

/**
 * Created by Tom on 6/9/2017.
 */
public class ReturnMessage implements IReturnMessage {
    private int exitStatus;
    private String info;
    private String errors;

    public ReturnMessage(int exitStatus, String info, String errors) {
        this.exitStatus = exitStatus;
        this.info = info;
        this.errors = errors;
    }

    public ReturnMessage() {
        this(0, "", "");
    }

    @Override
    public int getExitStatus() {
        return exitStatus;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getErrors() {
        return errors;
    }

    @Override
    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    @Override
    public void append(IReturnMessage returnMessage) {
        append(returnMessage.getInfo(), returnMessage.getErrors());
    }

    @Override
    public void append(String info, String errors) {
        this.info = getInfo() + "\n" + info;
        this.errors = getErrors() + "\n" + errors;

    }

    @Override
    public void appendInfo(String info) {
        append(info, "");
    }

    @Override
    public void appendErrors(String errors) {
        append("", errors);
    }

    @Override
    public String toString() {
        return "Info:\n" + info + "\nErrors:\n" + errors + "\n";
    }
}
