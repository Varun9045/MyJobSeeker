package com.example.jobseeker.recruiter.recruiterfragments;

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

import com.example.jobseeker.R;
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


public class RecruiterProfile extends Fragment {

FloatingActionButton openEditProfile;
TextView name,email,phoneNo,address,profiletitle,companyName,functionalArea;
String savedImage,user_id;
CircleImageView profileImage;
FirebaseAuth mAuth;
FirebaseDatabase rootNode;
StorageReference storage;
DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_recruiter_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        openEditProfile = view.findViewById(R.id.fab);
        // Firebase hooks
        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();

        // TextView hooks
        name = view.findViewById(R.id.recruiter_profileName);
        email = view.findViewById(R.id.show_email);
        phoneNo = view.findViewById(R.id.show_phoneNo);
        address = view.findViewById(R.id.show_address);
        profiletitle = view.findViewById(R.id.show_profileTitle);
        companyName = view.findViewById(R.id.show_company);
        functionalArea = view.findViewById(R.id.show_functionalArea);
        profileImage = view.findViewById(R.id.update_recruiter_profileimg);



        user_id = mAuth.getCurrentUser().getUid();
        myRef = rootNode.getReference("Users");
       //code
        Query checkUser = myRef.child("Recruiter").orderByChild("user_id").equalTo(user_id);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    name.setText(dataSnapshot.child(user_id).child("name").getValue(String.class));
                    email.setText(dataSnapshot.child(user_id).child("email").getValue(String.class));
                    phoneNo.setText(dataSnapshot.child(user_id).child("phoneNo").getValue(String.class));
                    address.setText(dataSnapshot.child(user_id).child("address").getValue(String.class));
                    profiletitle.setText(dataSnapshot.child(user_id).child("profile_title").getValue(String.class));
                    companyName.setText(dataSnapshot.child(user_id).child("company").getValue(String.class));
                    functionalArea.setText(dataSnapshot.child(user_id).child("fuctional_area").getValue(String.class));
                    FirebaseStorage.getInstance().getReference().child( "myimage/"+dataSnapshot.child(user_id).child("profile_img").getValue(String.class))
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getActivity()).load(uri).into(profileImage);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // copy code

        // copy code

        // open Edit profile
        openEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_recruiterProfile_to_recruiterEditProfile);
            }
        });

    }
}
