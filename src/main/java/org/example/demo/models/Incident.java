package org.example.demo.models;

import java.time.LocalDateTime;

public class Incident {
    private int reference;
    private LocalDateTime time;
    private String status;

    // Constructeurs
    public Incident(int reference, LocalDateTime time, String status) {
        this.reference = reference;
        this.time = time;
        this.status = status;
    }

    // Getters et Setters
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
