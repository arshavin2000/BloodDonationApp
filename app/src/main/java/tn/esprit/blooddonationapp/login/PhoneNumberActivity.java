package tn.esprit.blooddonationapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;

public  class PhoneNumberActivity {

    private Context context;
    private static final String TAG = "MOBILE-PHONE";
    private  static final String MY_PREFS_NAME ="LOGIN";
    private Activity activity;

    public PhoneNumberActivity(Context context,Activity activity)
    {
        this.context= context;
        this.activity =activity;

    }



    public void getCurrentAccount(){
        com.facebook.accountkit.AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            //Handle Returning User
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {

                @Override
                public void onSuccess(final Account account) {

                    // Get Account Kit ID
                    SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("login", true);
                    editor.apply();
                    String accountKitId = account.getId();
                    Log.e("Account Kit Id", accountKitId);

                    if(account.getPhoneNumber()!=null) {
                        Log.e("CountryCode", "" + account.getPhoneNumber().getCountryCode());
                        Log.e("PhoneNumber", "" + account.getPhoneNumber().getPhoneNumber());

                        // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = phoneNumber.toString();
                        Log.e("NumberString", phoneNumberString);
                        Donor donor = new Donor();
                        donor.setId(accountKitId);
                        donor.setNumber(phoneNumberString);
                        DonorService donorService = new DonorService(context,activity);
                        donorService.isUserExist(accountKitId,donor);
                        //finish();


                    }

                    if(account.getEmail()!=null)
                        Log.e("Email",account.getEmail());
                }

                @Override
                public void onError(final AccountKitError error) {
                    // Handle Error
                    Log.e(TAG,error.toString());
                }
            });

        } else {
            //Handle new or logged out user
            Log.e(TAG,"Logged Out");
            Toast.makeText(context,"Logged Out User",Toast.LENGTH_SHORT).show();
        }
    }

}
