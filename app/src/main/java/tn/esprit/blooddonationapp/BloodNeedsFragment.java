package tn.esprit.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;

import java.util.ArrayList;

import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.model.Post;
import tn.esprit.blooddonationapp.model.Receiver;
import tn.esprit.blooddonationapp.model.Request;
import tn.esprit.blooddonationapp.post.ListPostFragment;
import tn.esprit.blooddonationapp.post.MultiViewTypeAdapter;
import tn.esprit.blooddonationapp.util.Util;


public class BloodNeedsFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_blood_needs, container, false);






       AndroidNetworking.initialize(getContext());
        AndroidNetworking.get("http://192.168.43.80:3000/api/request")
                .setTag("GET_BloodNeeds")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Request.class, new ParsedRequestListener<ArrayList<Request>>() {
                    @Override
                    public void onResponse(ArrayList<Request> requests) {
                        System.out.println("IN NETWORKING");
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
                        RecyclerView mRecyclerView = view.getRootView().findViewById(R.id.recyclerView);
                        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(requests,getContext(),0);
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.i("TAG",""+anError.getCause());
                    }
                });

//TODO TO DELETE
       ArrayList<Receiver> receivers = new ArrayList<>();

        Receiver receiver = new Receiver("A","Rades Tunis","53 815 975","4 m");
        Receiver receiver2 = new Receiver("A","Rades Tunis","53 815 975","4 m");
        receivers.add(receiver);
        receivers.add(receiver2);



        return view;
    }


}
