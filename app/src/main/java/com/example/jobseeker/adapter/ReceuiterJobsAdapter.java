package com.example.jobseeker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseeker.R;
import com.example.jobseeker.model.Jobs;

import java.util.ArrayList;

public class ReceuiterJobsAdapter extends RecyclerView.Adapter<ReceuiterJobsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Jobs> myJobs;
    public ReceuiterJobsAdapter(Context c , ArrayList<Jobs> j){
        context = c;
        myJobs = j;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recruiter_view_jobs,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.jobRole.setText(myJobs.get(position).getJobTitle());
        holder.exprience.setText(myJobs.get(position).getExperience());
        holder.location.setText(myJobs.get(position).getLocation());
        holder.education.setText(myJobs.get(position).getEducation());
        holder.jobSkills.setText(myJobs.get(position).getJobskills());
        holder.salary.setText(myJobs.get(position).getSalary());
    }

    @Override
    public int getItemCount() {
        return myJobs.size();
    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView jobRole,exprience,location,education,jobSkills,salary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jobRole = (TextView) itemView.findViewById(R.id.viewJobRole);
            exprience = (TextView) itemView.findViewById(R.id.view_experience);
            location = (TextView) itemView.findViewById(R.id.view_job_location);
            education = (TextView) itemView.findViewById(R.id.view_min_education);
            jobSkills = (TextView) itemView.findViewById(R.id.view_jobSkills);
            salary = (TextView) itemView.findViewById(R.id.view_job_salary);
        }
    }
}
