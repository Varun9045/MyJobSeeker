package com.varunkumar123.jobseeker.recruiter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.varunkumar123.jobseeker.R;

public class RecruiterSignup extends AppCompatActivity {
    EditText fullName,email,phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recruiter_signup);

        // hooks for getting data
        fullName =findViewById(R.id.fullname);
        email =findViewById(R.id.email);
        phoneNo =findViewById(R.id.mobileno);
    }
// validation functions
    private boolean validateFullname(){
        String FullName = fullName.getText().toString().trim();
        if(FullName.isEmpty()){
            fullName.setError("Field can not be empty");
            fullName.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
    private boolean validateEmail(){
        String EmailS = email.getText().toString().trim();
        String checkemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(EmailS.isEmpty()){
            email.setError("Field can not be empty");
            email.requestFocus();
            return false;
        }
        else if(!EmailS.matches(checkemail)){
            email.setError("Invalid Email");
            email.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
    private boolean validatePhoneNo(){
        String PasswordS = phoneNo.getText().toString().trim();
        String checkpass="^"+
                "(?=.*[0-9])"+    //at least 1 digit
                    //at least 1 special character
                ".{10,10}"+ // 10 character
                "$";
        if(PasswordS.isEmpty()){
            phoneNo.setError("Field can not be empty");
            phoneNo.requestFocus();
            return false;
        }
        else if(!PasswordS.matches(checkpass)){
            phoneNo.setError("Phone no must 10 digits  ");
            phoneNo.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }



    public void signIn(View view) {
        finish();
    }

    public void callNextSignup(View view) {
        if(!validateFullname()  | !validateEmail() | !validatePhoneNo()){
            return;
        }
        String FullNameS = fullName.getText().toString();
        String EmailS = email.getText().toString();
        String PhoneNO = phoneNo.getText().toString();
        Intent intent = new Intent(this, OtpActivity.class);
        intent.putExtra("fullname",FullNameS);
        intent.putExtra("email",EmailS);
        intent.putExtra("phone",PhoneNO);
        startActivity(intent);
        fullName.setText(null);
        email.setText(null);
        phoneNo.setText(null);
    }
}
