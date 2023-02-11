package com.varunkumar123.jobseeker.recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.Recruiter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
    PinView pinView;
    String codeBySystem;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference myRef;
    String fullName,email,phoneNo, userName,password,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        // hooks
        pinView = findViewById(R.id.pinview);
        mAuth = FirebaseAuth.getInstance();

        fullName = getIntent().getStringExtra("fullname");
        userName = getIntent().getStringExtra("username");
        email= getIntent().getStringExtra("email");
        password= getIntent().getStringExtra("password");
        phoneNo = "+91"+getIntent().getStringExtra("phone");
        sendVerificationCodeToUser(phoneNo);
    }

    private void sendVerificationCodeToUser(String PhoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                PhoneNo,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if(code != null){
                        pinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final String userEnterPhoneno = phoneNo;
        rootnode = FirebaseDatabase.getInstance();
        myRef =rootnode.getReference("Users");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user_id = mAuth.getCurrentUser().getUid();
                            Query checkUser = myRef.child("Recruiter").orderByChild("user_id").equalTo(user_id);
                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String phonenoFromDB = dataSnapshot.child(user_id).child("phoneNo").getValue(String.class);
                                    if(phonenoFromDB.equals(userEnterPhoneno)){
                                        Toast.makeText(VerifyOtp.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(VerifyOtp.this,RecruiterHomePage.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(VerifyOtp.this, "Data Fatch error", Toast.LENGTH_SHORT).show();
                                    }
                                    }
                                    else {
                                        Recruiter recruiter = new Recruiter(fullName,email,phoneNo,user_id,null,null,null,null,null);
                                        myRef.child("Recruiter").child(user_id).setValue(recruiter);
                                        Toast.makeText(VerifyOtp.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(VerifyOtp.this,RecruiterHomePage.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //




                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOtp.this, "Verification not completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



    public void callNextScreenFromOtp(View view) {
        String code = pinView.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

    public void resendCodeToUser(View view) {

        sendVerificationCodeToUser(phoneNo);
        Toast.makeText(this, "Message send again", Toast.LENGTH_SHORT).show();
    }
}
