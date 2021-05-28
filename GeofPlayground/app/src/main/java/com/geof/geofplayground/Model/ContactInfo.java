package com.geof.geofplayground.Model;

public class ContactInfo {
    public String header;
    public String body;

    public ContactInfo(){
    }
    public ContactInfo(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
