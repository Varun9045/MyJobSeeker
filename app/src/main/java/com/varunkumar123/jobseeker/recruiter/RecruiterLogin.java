package com.varunkumar123.jobseeker.recruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.varunkumar123.jobseeker.R;

public class RecruiterLogin extends AppCompatActivity {
    EditText SigninPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recruiter_login);
        // hooks
        SigninPhoneNo = findViewById(R.id.signin_phone_no);

    }


    private boolean validatePhoneNo(){
        String PasswordS = SigninPhoneNo.getText().toString().trim();
        String checkpass="^"+
                "(?=.*[0-9])"+    //at least 1 digit
                ".{10,10}"+ // 10 character
                "$";
        if(PasswordS.isEmpty()){
            SigninPhoneNo.setError("Field can not be empty");
            SigninPhoneNo.requestFocus();
            return false;
        }
        else if(!PasswordS.matches(checkpass)){
            SigninPhoneNo.setError("Phone no must 10 digits  ");
            SigninPhoneNo.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }


    public void SignUp (View v) {
        startActivity(new Intent(this, RecruiterSignup.class));

    }

    public void Signin(View view) {
            if( !validatePhoneNo()){
                return;
            }
            String SIGNINPhoneNO = SigninPhoneNo.getText().toString();
            Intent intent = new Intent(this, VerifyOtp.class);
            intent.putExtra("phone",SIGNINPhoneNO);
            startActivity(intent);
        SigninPhoneNo.setText(null);
    }
}
