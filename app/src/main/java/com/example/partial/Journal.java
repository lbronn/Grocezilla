package com.example.partial;

import com.google.firebase.Timestamp;

public class Journal {
    String jTitle;
    String jContent;
    Timestamp timestamp;

    public Journal() {
    }

    public String getjTitle() {
        return jTitle;
    }

    public void setjTitle(String jTitle) {
        this.jTitle = jTitle;
    }

    public String getjContent() {
        return jContent;
    }

    public void setjContent(String jContent) {
        this.jContent = jContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
