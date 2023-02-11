package com.varunkumar123.jobseeker.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.Student;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignup extends AppCompatActivity {
    EditText StudentName, StudentEmail, StudentPass;
    Button StudentSignup;
   private FirebaseAuth mAuth;
   private FirebaseDatabase rootnode;
   private DatabaseReference myRef;
    String email, password,full_name;
    private GoogleSignInClient mGoogleSignInClient;
     String Email,FullName, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.student_signup);
        //hooks
        StudentName = findViewById(R.id.student_fullname);
        StudentEmail = findViewById(R.id.Student_email);
        StudentPass = findViewById(R.id.Student_Pass);
        StudentSignup = findViewById(R.id.Student_Signup);
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesignIn();
            }
        });
    }

    private void googlesignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign in Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        rootnode = FirebaseDatabase.getInstance();
        myRef = rootnode.getReference("Users");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userid = mAuth.getCurrentUser().getUid();
                            Email = mAuth.getCurrentUser().getEmail();
                            FullName = mAuth.getCurrentUser().getDisplayName();
                            Student student = new Student(userid,FullName,null,null,null,null,null,null,Email,null,null);
                            myRef.child("Student").child(userid).setValue(student);
                            startActivity(new Intent(StudentSignup.this,StudentHomePage.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StudentSignup.this, "Sign In Failed", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private boolean validateFullname() {
         full_name = StudentName.getText().toString().trim();
        if (full_name.isEmpty()) {
            StudentName.setError("Field can not be empty");
            StudentName.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        email = StudentEmail.getText().toString().trim();
        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            StudentEmail.setError("Field can not be empty");
            StudentEmail.requestFocus();
            return false;
        } else if (!email.matches(checkemail)) {
            StudentEmail.setError("Invalid Email");
            StudentEmail.requestFocus();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePassword(){
         password = StudentPass.getText().toString().trim();
        String checkpass="^"+
                "(?=.*[0-9])"+    //at least 1 digit
                "(?=.*[a-z])"+    //at least 1 small letter
                "(?=.*[A-Z])"+//at least 1 big letter
                "$";
        if(password.isEmpty()){
            StudentPass.setError("Field can not be empty");
            StudentPass.requestFocus();
            return false;
        }
        else if(!password.matches(checkpass)){
            StudentPass.setError("weak password at least 1 digit 1 small and 1 capital aplhabat 1 special character ");
            StudentPass.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
    public void signIn(View view) {
        finish();
    }

    public void studentSignUp(View view) {
        if (!validateFullname() | !validateEmail()| !validatePassword()) {


           return;
        }
        Email = StudentEmail.getText().toString();
        FullName = StudentName.getText().toString();

        rootnode = FirebaseDatabase.getInstance();
        myRef = rootnode.getReference("Users");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userid = mAuth.getCurrentUser().getUid();
                            Student student = new Student(userid,FullName,null,null,null,null,null,null,Email,null,null);
                            myRef.child("Student").child(userid).setValue(student);
                            Toast.makeText(StudentSignup.this, "Sucessfull Signup", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StudentSignup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }




}
