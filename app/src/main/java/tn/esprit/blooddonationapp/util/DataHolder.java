package tn.esprit.blooddonationapp.util;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.blooddonationapp.model.Center;
import tn.esprit.blooddonationapp.model.Donor;

public class DataHolder {
    //design pattern to share arguments between fragments and activities
    private static DataHolder dataHolder = null;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        if (dataHolder == null)
            dataHolder = new DataHolder();
        return dataHolder;
    }


    private Donor donor;
    private List<Center> centers;


    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }
}
