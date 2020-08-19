package com.example.jobseeker.recruiter.recruiterfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobseeker.R;
import com.example.jobseeker.adapter.ReceuiterJobsAdapter;
import com.example.jobseeker.model.Jobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class myJobs extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    RecyclerView myRecycleView;
    ArrayList<Jobs> list;
    FirebaseAuth mAuth;
    ReceuiterJobsAdapter receuiterJobsAdapter;
    public myJobs() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myjobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        final String recruiterUID = mAuth.getCurrentUser().getUid();
        myRecycleView = view.findViewById(R.id.viewRecruiterJobs);
        myRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<Jobs>();
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("Users");
        myRef.child("Jobs").orderByChild("recruiterid").equalTo(recruiterUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Jobs myjobs = dataSnapshot1.getValue(Jobs.class);
                        list.add(myjobs);
                }
                receuiterJobsAdapter = new ReceuiterJobsAdapter(getActivity(),list);
                myRecycleView.setAdapter(receuiterJobsAdapter);
             }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "oops something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
