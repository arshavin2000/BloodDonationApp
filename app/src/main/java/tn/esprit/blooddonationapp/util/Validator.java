package tn.esprit.blooddonationapp.util;


public class Validator {


    public  static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public  static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() !=8) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }

}
