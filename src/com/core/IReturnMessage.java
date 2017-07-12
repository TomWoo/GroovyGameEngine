package com.core;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IReturnMessage {
    int getExitStatus(); // 0 indicates no error
    String getInfo();
    String getErrors();
    void setExitStatus(int exitStatus);
    void append(IReturnMessage returnMessage);
    void append(String info, String errors);
    void appendInfo(String info);
    void appendErrors(String errors);
}
