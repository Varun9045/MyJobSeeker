package com.varunkumar123.jobseeker.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.Jobs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.jobRole.setText(myJobs.get(position).getJobTitle());
        holder.exprience.setText(myJobs.get(position).getExperience());
        holder.location.setText(myJobs.get(position).getLocation());
        holder.education.setText(myJobs.get(position).getEducation());
        holder.jobSkills.setText(myJobs.get(position).getJobskills());
        holder.salary.setText(myJobs.get(position).getSalary());
        //delete job
        holder.delete_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this Job ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child("Jobs").child(myJobs.get(position).getJobid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "job Deleted", Toast.LENGTH_SHORT).show();
                                        myJobs.remove(position);
                                        notifyItemRemoved(position);
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "job not deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myJobs.size();
    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView jobRole,exprience,location,education,jobSkills,salary;
        FloatingActionButton delete_jobs;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jobRole = (TextView) itemView.findViewById(R.id.viewJobRole);
            exprience = (TextView) itemView.findViewById(R.id.view_experience);
            location = (TextView) itemView.findViewById(R.id.view_job_location);
            education = (TextView) itemView.findViewById(R.id.view_min_education);
            jobSkills = (TextView) itemView.findViewById(R.id.view_jobSkills);
            salary = (TextView) itemView.findViewById(R.id.view_job_salary);
            delete_jobs = (FloatingActionButton) itemView.findViewById(R.id.delete);
        }
    }
}
