package com.example.kouluprojekti;

public class Appointment {
    private String topic;
    private String date;
    private String time;
    private boolean isChecked;

    public Appointment(String topic, String date, String time, boolean isChecked) {
        this.topic = topic;
        this.date = date;
        this.time = time;
        this.isChecked = isChecked;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
