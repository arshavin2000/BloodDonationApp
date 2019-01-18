package tn.esprit.blooddonationapp;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.blooddonationapp.model.Center;

public interface CenterCallback {

    void onSuccess(List<Center> centers);
    void onFail(String msg);
}
