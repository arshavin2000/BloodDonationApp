package tn.esprit.blooddonationapp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Donor;

public class CustomAdapter extends ArrayAdapter<Donor> implements View.OnClickListener {

    private ArrayList<Donor> donors;
    private Context mContext;

    private static class ViewHolder {
        ImageView image  ;
        TextView firstName , lastName ;
        Button tel , blood;
    }

    public CustomAdapter(ArrayList<Donor> data, Context context) {
        super(context, R.layout.list_users, data);
        this.donors = data;
        this.mContext=context;
    }



    @Override
    public void onClick(View view) {

    }

    @Nullable
    @Override
    public Donor getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Donor donor = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_users, parent, false);
            viewHolder.firstName =  convertView.findViewById(R.id.firstName);
            viewHolder.lastName =  convertView.findViewById(R.id.lastName);
            viewHolder.image =  convertView.findViewById(R.id.image);
            viewHolder.tel =  convertView.findViewById(R.id.tel);
            viewHolder.blood =  convertView.findViewById(R.id.blood);




            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.firstName.setText(donor.getFirstName());
        viewHolder.lastName.setText(donor.getLastName());
        ProfileImage.getFacebookOrGoogleProfilePicture(donor.getUrlImage(),mContext,viewHolder.image);
        viewHolder.tel.setText(donor.getNumber());
        viewHolder.blood.setText(donor.getBloodGroup());
        /*viewHolder.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ProfileActivity.class);
                mContext.startActivity(intent);
            }
        });*/


        // Return the completed view to render on screen
        return convertView;
    }


}
