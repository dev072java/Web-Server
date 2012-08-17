package com.softserveinc.edu.webserver;

public class Response {

    byte[] data = null;

    public byte[] getBytes() {
        return data;
    }

    public void setBytes(byte[] response) {
        this.data = response;
    }
}
