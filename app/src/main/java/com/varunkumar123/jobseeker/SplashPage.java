package com.varunkumar123.jobseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.varunkumar123.jobseeker.recruiter.RecruiterHomePage;
import com.varunkumar123.jobseeker.student.StudentHomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifImageView;

public class SplashPage extends AppCompatActivity {

    private static int SPLASH_SCREEN=2000;
//Variables
Animation topAnim,bottomAnim;
ImageView image;
GifImageView gifImage;
TextView t1,t2;
FirebaseAuth mAuth;
FirebaseDatabase rootnode;
DatabaseReference myRef;
    private static final int PERMISSION_REQUEST_CODE = 200;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);
//Amimations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Hooks
        image=findViewById(R.id.splash_imageblue);
        gifImage=findViewById(R.id.splash_gif);
        t1=findViewById(R.id.splashtxt1);
        t2=findViewById(R.id.splashtxt2);
        mAuth = FirebaseAuth.getInstance();
        image.setAnimation(topAnim);
        t1.setAnimation(topAnim);
        t2.setAnimation(topAnim);
        gifImage.setAnimation(bottomAnim);
        if(mAuth.getCurrentUser() !=null){
            final String currentUserId = mAuth.getCurrentUser().getUid();
            rootnode = FirebaseDatabase.getInstance();
            myRef =rootnode.getReference("Users");
            Query checkUser = myRef.child("Recruiter").orderByChild("user_id").equalTo(currentUserId);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String useridFromDB =dataSnapshot.child(currentUserId).child("user_id").getValue(String.class);

                        if(useridFromDB.equals(currentUserId)){
                            startActivity(new Intent(SplashPage.this, RecruiterHomePage.class));
                            finish();
                        }else{
                            Toast.makeText(SplashPage.this, "UID search error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        startActivity(new Intent(SplashPage.this, StudentHomePage.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run(){
                    // permission code
                    if (checkPermission()) {
                        Toast.makeText(SplashPage.this, "Permission already granted", Toast.LENGTH_SHORT).show();

                    } else {
                        requestPermission();

                    }
                    // permission code


                }
            },SPLASH_SCREEN);
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(SplashPage.this,IntroSlider.class);
                    startActivity(intent);
                    finish();
            } else {
                    Toast.makeText(this, "Permission Denied! You can't access your images or media ", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow access  the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        }
                                    });

                        }
                    }
                    Intent intent = new Intent(SplashPage.this,IntroSlider.class);
                    startActivity(intent);
                    finish();
            }
            break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),STORAGE_SERVICE);
        return result == PackageManager.PERMISSION_GRANTED && result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
