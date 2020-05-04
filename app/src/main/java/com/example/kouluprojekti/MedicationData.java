package com.example.kouluprojekti;

public class MedicationData {
    private String medName;
    private String time;
    private boolean isChecked;
    private int date;

    public MedicationData(String medName, String time, boolean isChecked, int date) {
        this.medName = medName;
        this.time = time;
        this.isChecked = isChecked;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
