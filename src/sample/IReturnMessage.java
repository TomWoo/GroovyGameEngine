package sample;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IReturnMessage {
    int getExitStatus(); // 0 indicates no error
    String getInfo();
    String getErrors();
    void setExitStatus(int exitStatus);
    void appendInfo(String info);
    void appendErrors(String errors);
}
