package com.example.ProiectDistributedSystems.DTO.Socket;

public class ResponseMesage {

    private String content;

    public ResponseMesage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
