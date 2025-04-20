package com.example.tpo7.Models;

import java.io.Serializable;

public class Code implements Serializable {
    private final String formattedCode;
    private final long timestamp;
    private final long ttl;

    public Code(String formattedCode, long timestamp, long ttl) {
        this.formattedCode = formattedCode;
        this.timestamp = timestamp;
        this.ttl = ttl;
    }

    public String getFormattedCode() {
        return formattedCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getTtl() {
        return ttl;
    }
}
