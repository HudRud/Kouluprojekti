package com.example.kouluprojekti;

public class MedicationData {
    private String medName;
    private String time;
    private boolean isChecked;

    public MedicationData(String medName, String time, boolean isChecked) {
        this.medName = medName;
        this.time = time;
        this.isChecked = isChecked;
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
}
