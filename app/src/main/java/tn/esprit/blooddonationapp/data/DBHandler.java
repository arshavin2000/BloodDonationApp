package tn.esprit.blooddonationapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.Util;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, Util.DB_NAME,null, Util.DB_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACT_TABLE = "create table "+ Util.DONOR_TABLE +"(" + Util.ID + " integer primary key,"
                +Util.FIRST_NAME_DONOR+" text, "+ Util.LAST_NAME_DONOR+" text, "+Util.EMAIL_DONOR +" text ,"+Util.NUMBER_DONOR +" text ,"+
                Util.URL_IMAGE_DONOR +" text ,"+Util.GENDER_DONOR +" text ,"+Util.BLOOD_GROUP_DONOR +" text ,"+
                Util.ID_DONOR +" text ,"+Util.REQUEST_DONOR +" integer ,"+Util.ANSWER_DONOR +" integer ,"+Util.RATE_DONOR +" integer "+")";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //drop the table
        sqLiteDatabase.execSQL("drop table if exists " + Util.DONOR_TABLE);
        //recreate the table again
        onCreate(sqLiteDatabase);

    }


    public void addDonor(Donor donor )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.ID_DONOR,donor.getId());
        contentValues.put(Util.FIRST_NAME_DONOR,donor.getFirstName());
        contentValues.put(Util.LAST_NAME_DONOR,donor.getLastName());
        contentValues.put(Util.EMAIL_DONOR,donor.getEmail());
        contentValues.put(Util.NUMBER_DONOR,donor.getNumber());
        contentValues.put(Util.URL_IMAGE_DONOR,donor.getUrlImage());
        contentValues.put(Util.GENDER_DONOR,donor.getGender());
        contentValues.put(Util.BLOOD_GROUP_DONOR,donor.getBloodGroup());
        contentValues.put(Util.REQUEST_DONOR,donor.getRequest());
        contentValues.put(Util.ANSWER_DONOR,donor.getAnswer());
        contentValues.put(Util.RATE_DONOR,donor.getRate());

        //insert row
        db.insert(Util.DONOR_TABLE,null,contentValues);
        db.close();

    }

    public Donor getDonor (String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Donor donor ;
        Cursor cursor = db.query(Util.DONOR_TABLE, new String[]{Util.ID_DONOR,Util.FIRST_NAME_DONOR , Util.LAST_NAME_DONOR, Util.EMAIL_DONOR
                ,Util.NUMBER_DONOR,Util.URL_IMAGE_DONOR,Util.GENDER_DONOR,Util.BLOOD_GROUP_DONOR,Util.REQUEST_DONOR,Util.ANSWER_DONOR,Util.RATE_DONOR}, Util.ID_DONOR+"=?"
                , new String[]{id},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        donor = new Donor();
        if(cursor != null && cursor.moveToFirst())
            {

                donor.setId(cursor.getString(0));
                donor.setFirstName(cursor.getString(1));
                donor.setLastName(cursor.getString(2));
                donor.setEmail(cursor.getString(3));
                donor.setNumber(cursor.getString(4));
                donor.setUrlImage(cursor.getString(5));
                donor.setGender(cursor.getString(6));
                donor.setBloodGroup(cursor.getString(7));
                donor.setRequest(cursor.getInt(8));
                donor.setAnswer(cursor.getInt(9));
                donor.setRate(cursor.getInt(10));



            }

        return donor ;
    }



    public void updateDonor(Donor donor)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

       // contentValues.put(Util.EMAIL_DONOR,donor.getEmail());
       // contentValues.put(Util.NUMBER_DONOR,donor.getNumber());
        contentValues.put(Util.REQUEST_DONOR,donor.getRequest());


        db.update(Util.DONOR_TABLE,contentValues,Util.ID_DONOR +"=?"
                , new String[]{String.valueOf(donor.getId())});
        //insert row


    }

   /* public boolean Exists(String searchItem) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { Util.NAME };
        String selection = Util.NAME + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(Util.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

   */

    public void deleteDonor()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        db.execSQL("delete   from "+ Util.DONOR_TABLE);
        db.close();
    }
}



