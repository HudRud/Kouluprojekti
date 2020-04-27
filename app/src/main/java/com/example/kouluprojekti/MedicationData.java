package com.example.kouluprojekti;

public class MedicationData {
    private String medName;

    public MedicationData(String medName) {
        this.medName = medName;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }
}
