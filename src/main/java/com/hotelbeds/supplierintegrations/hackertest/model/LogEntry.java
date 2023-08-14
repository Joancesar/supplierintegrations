package com.hotelbeds.supplierintegrations.hackertest.model;

import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogEntry {
    private String ip;
    private long epochTime;
    private LoginAction action;
    private String username;
}
