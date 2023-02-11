package com.varunkumar123.jobseeker.student.studentfragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varunkumar123.jobseeker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class StudentProfile extends Fragment {
    CircleImageView studentImage;
    TextView studentName,studentEmail,studentMobile, dateOfBirth,Location,education,yearOfPassing,experience,skills;
    FirebaseAuth mAuth;
    FloatingActionButton openUpdateProfile;
    FirebaseDatabase rootNode;
    StorageReference storage;
    DatabaseReference myRef;
    String user_id;



    public StudentProfile() {
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
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        studentImage = view.findViewById(R.id.update_recruiter_profileimg);
        studentName = view.findViewById(R.id.student_name);
        studentEmail = view.findViewById(R.id.student_profile_email);
        studentMobile = view.findViewById(R.id.student_profile_mobile);
        dateOfBirth = view.findViewById(R.id.student_dob);
        Location = view.findViewById(R.id.student_profile_location);
        education = view.findViewById(R.id.student_profile_qualification);
        yearOfPassing = view.findViewById(R.id.student_passingYear);
        experience = view.findViewById(R.id.student_experience);
        skills = view.findViewById(R.id.student_skills);
        openUpdateProfile = view.findViewById(R.id.fab_update_studentProfile);
        studentEmail.setText(mAuth.getCurrentUser().getEmail());
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        studentName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Picasso.with(getActivity()).load(acct.getPhotoUrl()).into(studentImage);
        user_id = mAuth.getCurrentUser().getUid();
        myRef = rootNode.getReference("Users");
        //code
        Query checkUser = myRef.child("Student").orderByChild("studentid").equalTo(user_id);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(dataSnapshot.exists()){
               studentName.setText(dataSnapshot.child(user_id).child("name").getValue(String.class));
               studentEmail.setText(dataSnapshot.child(user_id).child("email").getValue(String.class));
               studentMobile.setText(dataSnapshot.child(user_id).child("mobile").getValue(String.class));
               Location.setText(dataSnapshot.child(user_id).child("student_location").getValue(String.class));
               dateOfBirth.setText(dataSnapshot.child(user_id).child("dateofbirth").getValue(String.class));
               education.setText(dataSnapshot.child(user_id).child("qualification").getValue(String.class));
               yearOfPassing.setText(dataSnapshot.child(user_id).child("yearofpassing").getValue(String.class));
               experience.setText(dataSnapshot.child(user_id).child("experience").getValue(String.class));
               skills.setText(dataSnapshot.child(user_id).child("skills").getValue(String.class));
               FirebaseStorage.getInstance().getReference().child( "myimage/"+dataSnapshot.child(user_id).child("profileImg").getValue(String.class))
                       .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Picasso.with(getActivity()).load(uri).into(studentImage);
                   }
               });

           } }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // open student edit profile fragment
        openUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_student_profile_to_studentEditProfile);
            }
        });
    }
}
