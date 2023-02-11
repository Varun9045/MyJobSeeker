package com.varunkumar123.jobseeker.recruiter.recruiterfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.adapter.SearchTechAdapter;
import com.varunkumar123.jobseeker.model.SearchByTech;

import java.util.ArrayList;
import java.util.List;


public class SearchByTechnology extends Fragment {
    RecyclerView recyclerView;

    public SearchByTechnology() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_technology, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.search_by_tech_rec);
        dummyModule();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SearchTechAdapter(list));
    }
    List<SearchByTech> list = new ArrayList<>();
    private void dummyModule(){
        SearchByTech s1 =new SearchByTech();
        s1.setName("Full Name");
        s1.setEmail("Email");
        s1.setJobTitle("Job title");
        s1.setJobRole("Job role");
        s1.setSkills("skills");

        SearchByTech s2 =new SearchByTech();
        s2.setName("Full Name");
        s2.setEmail("Email");
        s2.setJobTitle("Job title");
        s2.setJobRole("Job role");
        s2.setSkills("skills");

        SearchByTech s3 =new SearchByTech();
        s3.setName("Full Name");
        s3.setEmail("Email");
        s3.setJobTitle("Job title");
        s3.setJobRole("Job role");
        s3.setSkills("skills");
        list.add(s1);
        list.add(s2);
        list.add(s3);
    }
}
