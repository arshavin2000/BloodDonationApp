package tn.esprit.blooddonationapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import tn.esprit.blooddonationapp.BecomeDonorActivity;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.DataHolder;

class GoogleActivity {

    private Context  context;
    private Activity activity;
    private static final String TAG_GOOGLE = "GOOGLE_LOG";

    GoogleActivity(Context context, Activity activity)
    {
        this.context=context;
        this.activity =activity;

    }



     void updateUI(GoogleSignInAccount account ){
        if (account != null) {
            DonorService donorService = new DonorService(context,activity);
            donorService.isGoogleUserExist(account.getId(),account);        }
    }




     void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
            // Signed in successfully, show authenticated UI.
            Donor donor = new Donor();
            donor.setEmail(account.getEmail());
            donor.setId(account.getId());
            donor.setFirstName(account.getGivenName());
            donor.setLastName(account.getFamilyName());
            if(account.getPhotoUrl() != null)
                donor.setUrlImage(account.getPhotoUrl().toString());
            donor.setAnswer(0);
            donor.setRequest(0);
            donor.setRate(0);





        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG_GOOGLE, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }



}
