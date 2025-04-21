package com.example.tpo7.Models;

import java.io.Serializable;

public class Code implements Serializable {
    private final String formattedCode;
    private final long timestamp;
    private final long duration;

    public Code(String formattedCode, long timestamp, long duration) {
        this.formattedCode = formattedCode;
        this.timestamp = timestamp;
        this.duration = duration;
    }

    public String getFormattedCode() {
        return formattedCode;
    }
}
