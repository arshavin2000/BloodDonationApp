package tn.esprit.blooddonationapp;

import java.util.ArrayList;

import tn.esprit.blooddonationapp.model.Donor;

public interface CallBack {
    void onSuccess(ArrayList<Donor> donors);

    void onFail(String msg);
}
