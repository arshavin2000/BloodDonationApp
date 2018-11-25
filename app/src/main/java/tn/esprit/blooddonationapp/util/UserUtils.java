package tn.esprit.blooddonationapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.model.Donor;

public class UserUtils {



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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void alertDialog(Activity activity,String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }



}
