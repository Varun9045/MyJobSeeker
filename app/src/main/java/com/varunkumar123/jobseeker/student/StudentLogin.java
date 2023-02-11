package com.varunkumar123.jobseeker.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.varunkumar123.jobseeker.ForgotPassword;
import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.Student;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity {
    String email,password,fullname,userid;
    EditText StudentEmail,StudentPass;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);
        //hooks
        StudentEmail = findViewById(R.id.Student_signin_email);
        StudentPass = findViewById(R.id.student_signin_passs);
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
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("Users");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userid = mAuth.getCurrentUser().getUid();
                            Query checkUser = myRef.child("Student").orderByChild("studentid").equalTo(userid);
                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String phonenoFromDB = dataSnapshot.child(userid).child("mobile").getValue(String.class);
                                        if(!phonenoFromDB.isEmpty()){
                                            startActivity(new Intent(StudentLogin.this,StudentHomePage.class));
                                        }
                                        else{
                                            email = mAuth.getCurrentUser().getEmail();
                                            fullname = mAuth.getCurrentUser().getDisplayName();
                                            Student student = new Student(userid,fullname,null,null,null,null,null,null,email,null,null);
                                            myRef.child("Student").child(userid).setValue(student);
                                        }
                                    }else{
                                        email = mAuth.getCurrentUser().getEmail();
                                        fullname = mAuth.getCurrentUser().getDisplayName();
                                        Student student = new Student(userid,fullname,null,null,null,null,null,null,email,null,null);
                                        myRef.child("Student").child(userid).setValue(student);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            startActivity(new Intent(StudentLogin.this,StudentHomePage.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StudentLogin.this, "Sign In Failed", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
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
                //at least 1 special character
                "$";
        if(password.isEmpty()){
            StudentPass.setError("Field can not be empty");
            StudentPass.requestFocus();
            return false;
        }
        else if(!password.matches(checkpass)){
            StudentPass.setError("weak password at least 1 digit 1 small and 1 capital aplhabat  ");
            StudentPass.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    public void passForget(View view) {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    public void signUp(View view) {
        startActivity(new Intent(this, StudentSignup.class));
    }

    public void signIn(View view) {
        if(!validateEmail()|!validatePassword()){
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(StudentLogin.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentLogin.this, StudentHomePage.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StudentLogin.this, "Login failed please correct your Email id or password", Toast.LENGTH_SHORT).show();
                        }


                        // ...
                    }
                });
    }

}



