package tn.esprit.blooddonationapp.util;

import java.util.ArrayList;

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


    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }


}
