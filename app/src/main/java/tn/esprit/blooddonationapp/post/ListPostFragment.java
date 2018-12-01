package tn.esprit.blooddonationapp.post;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Post;
import tn.esprit.blooddonationapp.model.User;
import tn.esprit.blooddonationapp.util.Util;


public class ListPostFragment extends Fragment {
    private RecyclerView mRecyclerView;
    MultiViewTypeAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list_post, container, false);

        AndroidNetworking.initialize(getContext());
        AndroidNetworking.get(Util.BASE_URL+"/api/posts")
                .setTag("GET_POSTS")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Post.class, new ParsedRequestListener<ArrayList<Post>>() {
                    @Override
                    public void onResponse(ArrayList<Post> posts) {
                        System.out.println("IN NETWORKING");
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
                        RecyclerView mRecyclerView = view.getRootView().findViewById(R.id.recyclerView);
                        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(posts,getContext());
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.i("TAG",""+anError.getCause());
                    }
                });




        return view;
    }





}
