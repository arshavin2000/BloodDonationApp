package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.CustomAdapter;

public class RequestList extends AppCompatActivity {

    private Button request ;

    private ListView listView;
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        listView = findViewById(R.id.list);
        request = findViewById(R.id.request);


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestList.this,RequestBlood.class);
                startActivity(intent);
            }
        });

        DonorService donorService = new DonorService(getApplicationContext(),this);
        donorService.getDonors(new CallBack() {
            @Override
            public void onSuccess(ArrayList<Donor> donors) {


                adapter = new CustomAdapter(donors,getApplicationContext());
                listView.setAdapter(adapter);
                //remove item from favourite list
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        ((BaseAdapter)adapter).notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onFail(String msg) {

                Log.d("OnFAIL", "onFail: "+ msg);

            }
        });



    }
}
