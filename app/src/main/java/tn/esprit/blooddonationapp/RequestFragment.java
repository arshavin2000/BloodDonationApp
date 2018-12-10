package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.Service.RequestService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.CustomAdapter;


public class RequestFragment extends Fragment {

    private Button request ;

    private ListView listView;
    private ListAdapter adapter;
    private TextView newRequest;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request, container, false);
        listView = view.findViewById(R.id.list);
        request = view.findViewById(R.id.request);
        newRequest  =  view.findViewById(R.id.r);


        RequestService requestService = new RequestService(getContext(),getActivity());
        requestService.getRequests(new CallBack() {
            @Override
            public void onSuccess(ArrayList<Donor> donors) {

            }

            @Override
            public void onSuccess(int k) {
                Log.d("REQUEST raj3et", "onSuccess: " + k);
                newRequest.setText(k+"");

            }

            @Override
            public void onFail(String msg) {

                Toast.makeText(getContext(),"Requests failed",Toast.LENGTH_LONG).show();

            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.container,new RequestBloodFragment()).commit();

            }
        });

        DonorService donorService = new DonorService(getContext(),getActivity());
        donorService.getDonors(new CallBack() {
            @Override
            public void onSuccess(ArrayList<Donor> donors) {


                adapter = new CustomAdapter(donors,getContext());
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
            @Override
            public void onSuccess(int i) {


            }
        });


        return view ;
    }
}
