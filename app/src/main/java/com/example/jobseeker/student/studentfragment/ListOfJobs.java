package com.example.jobseeker.student.studentfragment;

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
import com.example.jobseeker.adapter.JobListAdapter;
import com.example.jobseeker.model.Jobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListOfJobs extends Fragment {
    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    RecyclerView myRecycleView;
    ArrayList<Jobs> list;
    FirebaseAuth mAuth;
    JobListAdapter jobListAdapter;


    public ListOfJobs() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_job_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRecycleView = view.findViewById(R.id.student_joblist_recycleView);
        myRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<Jobs>();
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("Users");
        myRef.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Jobs myjobs = dataSnapshot1.getValue(Jobs.class);
                    list.add(myjobs);
                }
                jobListAdapter = new JobListAdapter(getActivity(),list);
                myRecycleView.setAdapter(jobListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "oops something wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
