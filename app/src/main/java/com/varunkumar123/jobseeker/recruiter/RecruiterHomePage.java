package com.varunkumar123.jobseeker.recruiter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.varunkumar123.jobseeker.IntroSlider;
import com.varunkumar123.jobseeker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecruiterHomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    String user_id,savedImage;
    TextView tname,temail;
    CircleImageView recruiterNavImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_home_page);
        Toolbar toolbar = findViewById(R.id.rec_toolbar);
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.recruiter_drawer_layout);
        NavigationView navigationView = findViewById(R.id.recruiter_nav_view);
        View view=navigationView.getHeaderView(0);
        tname=view.findViewById(R.id.name);
        temail=view.findViewById(R.id.email);
        recruiterNavImage = view.findViewById(R.id.recruiter_nav_profile_img);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.getMenu().findItem(R.id.nav_recruiter_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                Intent login_intent = new Intent(getApplicationContext(), IntroSlider.class);
                login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // clear previous task (optional)
                startActivity(login_intent);
                return false;
            }
        });
        // Navigation header user data
        user_id = mAuth.getCurrentUser().getUid();
        myRef = rootNode.getReference("Users");
        //code
        Query checkUser = myRef.child("Recruiter").orderByChild("user_id").equalTo(user_id);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tname.setText(dataSnapshot.child(user_id).child("name").getValue(String.class));
                    temail.setText(dataSnapshot.child(user_id).child("email").getValue(String.class));
                    savedImage = dataSnapshot.child(user_id).child("profile_img").getValue(String.class);
                    StorageReference storage = FirebaseStorage.getInstance().getReference().child("myimage/"+savedImage);
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(RecruiterHomePage.this).load(uri).into(recruiterNavImage);
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.recruiter_nav_home, R.id.recruiterProfile, R.id.searchByTechnology, R.id.addJobs, R.id.myJobs, R.id.nav_recruiter_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.rec_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.rec_nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
