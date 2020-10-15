package dn.com;

import dn.com.model.Report;

public interface ILogWriter {
    void write(final Report report);
}
