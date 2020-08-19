package com.example.jobseeker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseeker.R;
import com.example.jobseeker.model.Jobs;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.MyViewHolder>{
    Context context;
    ArrayList<Jobs> myStudentJobs;

    public JobListAdapter(Context c , ArrayList<Jobs> j){
        context = c;
        myStudentJobs = j;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.student_view_list_of_jobs,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.jobTitle.setText(myStudentJobs.get(position).getJobTitle());
        holder.exprience.setText(myStudentJobs.get(position).getExperience());
        holder.location.setText(myStudentJobs.get(position).getLocation());
        holder.education.setText(myStudentJobs.get(position).getEducation());
        holder.jobSkills.setText(myStudentJobs.get(position).getJobskills());
        holder.salary.setText(myStudentJobs.get(position).getSalary());
        holder.company.setText(myStudentJobs.get(position).getCompany());
    }


    @Override
    public int getItemCount() {
        return myStudentJobs.size();
    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{
        TextView jobTitle,exprience,location,education,jobSkills,salary,company;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.student_viewJobTitle);
            exprience = (TextView) itemView.findViewById(R.id.student_view_experience);
            location = (TextView) itemView.findViewById(R.id.student_view_job_location);
            education = (TextView) itemView.findViewById(R.id.student_view_min_education);
            jobSkills = (TextView) itemView.findViewById(R.id.student_view_jobSkills);
            salary = (TextView) itemView.findViewById(R.id.student_view_job_salary);
            company = (TextView) itemView.findViewById(R.id.view_jobposted_company);
        }
    }
}


