package dn.com.model;

public class LogLine {

    public String timestamp;
    public String thread;
    public String logLevel;
    public String serviceName;
    public String message;

    @Override
    public String toString() {
        return "LogLine{" +
                "timestamp='" + timestamp + '\'' +
                ", thread='" + thread + '\'' +
                ", logLevel='" + logLevel + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
