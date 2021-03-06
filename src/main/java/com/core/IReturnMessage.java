package com.core;

import java.util.List;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IReturnMessage {
    List<String> getInfo();
    boolean hasInfo();
    List<String> getErrors();
    boolean hasErrors();
    //void append(IReturnMessage returnMessage);
    void append(String info, String error);
    void appendInfo(String info);
    void appendError(String error);
    void clear();
}
