package tn.esprit.blooddonationapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.model.Donor;

public class UserUtils {

    private  static final String MY_PREFS_NAME ="ID";


    public static void saveUser( Context context, String id){

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putString("id",id);
        editor.apply();
    }


    public static Donor getUser(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        DBHandler dbHandler = new DBHandler(context);
        return dbHandler.getDonor(prefs.getString("id",""));

    }
}
