package com.example.kouluprojekti;

public class NotificationArray {

    private String alarmDate;
    private String alarmTime;

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public NotificationArray(String alarmDate, String alarmTime) {
        this.alarmDate = alarmDate;
        this.alarmTime = alarmTime;
    }
}
