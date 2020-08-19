package com.example.jobseeker.recruiter.recruiterfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobseeker.R;
import com.example.jobseeker.model.Jobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddJobs extends Fragment {

   EditText jobRole,experience,education,salary,jobSkills,jobLocation,companyName,recruiterName,phoneNo;
   Button addJobs;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference myRef;
    String job_id,recruiter_id,job_Role,Experience,Education,Salary,job_Skills,job_Location,company_Name,recruiter_Name,phone_No;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //firebase hooks
        mAuth = FirebaseAuth.getInstance();
        //firebase hooks

        jobRole = view.findViewById(R.id.job_role);
        experience = view.findViewById(R.id.experience);
        education = view.findViewById(R.id.education);
        salary = view.findViewById(R.id.salary);
        jobSkills = view.findViewById(R.id.job_skills);
        jobLocation = view.findViewById(R.id.job_location);
        recruiterName =view.findViewById(R.id.recruiter_name);
        companyName =view.findViewById(R.id.company_name);
        phoneNo =view.findViewById(R.id.phone_no);
        addJobs = view.findViewById(R.id.addjob_btn);
        phoneNo.setEnabled(false);
        phoneNo.setText(mAuth.getCurrentUser().getPhoneNumber());

        //code


        addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getValue
                job_Role = jobRole.getText().toString();
                Experience = experience.getText().toString();
                company_Name = companyName.getText().toString();
                Education = education.getText().toString();
                job_Location = jobLocation.getText().toString();
                Salary = salary.getText().toString();
                job_Skills = jobSkills.getText().toString();
                recruiter_Name = recruiterName.getText().toString();
                phone_No = phoneNo.getText().toString();
                rootnode = FirebaseDatabase.getInstance();
                myRef = rootnode.getReference("Users");
                 job_id = myRef.push().getKey();
                 recruiter_id = mAuth.getCurrentUser().getUid();
                Jobs myjob = new Jobs(job_id,job_Role,Experience,company_Name,Education,job_Location,recruiter_id,Salary,job_Skills,phone_No,recruiter_Name);
                myRef.child("Jobs").child(job_id).setValue(myjob);
                Toast.makeText(getActivity(), "upload data successfuly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
