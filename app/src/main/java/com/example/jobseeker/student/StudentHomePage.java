package com.example.jobseeker.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jobseeker.IntroSlider;
import com.example.jobseeker.R;
import com.example.jobseeker.recruiter.RecruiterHomePage;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHomePage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    String user_id,savedImage;
    CircleImageView userImage;
    TextView userEmail,userName;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        Toolbar toolbar = findViewById(R.id.std_toolbar);
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.std_drawer_layout);
        NavigationView navigationView = findViewById(R.id.student_nav_view);
        View view=navigationView.getHeaderView(0);
        userImage = view.findViewById(R.id.student_header_profile_img);
        userEmail = view.findViewById(R.id.student_email);
        userName = view.findViewById(R.id.student_username);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.getMenu().findItem(R.id.nav_student_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Email And Password Authentication Signout

                FirebaseAuth.getInstance().signOut();
                //Facebook logout
                LoginManager.getInstance().logOut();
                // Google Authentication Sign Out

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(StudentHomePage.this,gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            FirebaseAuth.getInstance().signOut(); // very important if you are using firebase.
                            Intent login_intent = new Intent(getApplicationContext(), IntroSlider.class);
                            login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // clear previous task (optional)
                            startActivity(login_intent);
                        }
                    }
                });
                return false;
            }
        });
        // Navigation header user data
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());// username of login user
        userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Picasso.with(getBaseContext()).load(acct.getPhotoUrl()).into(userImage);
        // Using database
        user_id = mAuth.getCurrentUser().getUid();
        myRef = rootNode.getReference("Users");
        //code
        Query checkUser = myRef.child("Student").orderByChild("studentid").equalTo(user_id);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child(user_id).child("name").getValue(String.class));
                userEmail.setText(dataSnapshot.child(user_id).child("email").getValue(String.class));
                savedImage = dataSnapshot.child(user_id).child("profileImg").getValue(String.class);
                StorageReference storage = FirebaseStorage.getInstance().getReference().child("myimage/"+savedImage);
                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(StudentHomePage.this).load(uri).into(userImage);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // email of login user


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.student_nav_home, R.id.student_profile, R.id.uploadResume, R.id.viewResume, R.id.listOfJobs, R.id.saveJobs,R.id.applyJobs, R.id.nav_student_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.std_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.std_nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
