package com.edi.tagsBackend.models;

public class ErrorMessage {
    private String content;

    public ErrorMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
