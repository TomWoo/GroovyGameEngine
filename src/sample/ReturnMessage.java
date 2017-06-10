package sample;

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
    public void appendInfo(String info) {
        this.info = getInfo() + info;
    }

    @Override
    public void appendErrors(String errors) {
        this.errors = getErrors() + errors;
    }

    @Override
    public String toString() {
        return info + "\nErrors: " + errors + "\n";
    }
}
