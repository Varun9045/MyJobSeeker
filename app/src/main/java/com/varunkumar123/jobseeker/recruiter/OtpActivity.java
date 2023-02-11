package com.varunkumar123.jobseeker.recruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.varunkumar123.jobseeker.R;

public class OtpActivity extends AppCompatActivity  {
    EditText phone;
    TextView txtNotify;
    Button next;
    String Phone_NO,SigninPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Phone_NO = getIntent().getStringExtra("phoneno");
        SigninPhoneNo = getIntent().getStringExtra("SigninPhoneNo");
        phone = findViewById(R.id.mobileno);
        phone.setEnabled(false);
        next = findViewById(R.id.nextbtn);
        if(Phone_NO!=null){
            phone.setText(Phone_NO);
        }
        else{
            phone.setText(SigninPhoneNo);
        }
    }

    private boolean validatePhone(){
        String getUserPhone = phone.getText().toString();
        if(TextUtils.isEmpty(getUserPhone)){
            phone.setError("Enter phone no");
            phone.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    public void callVerifyOtpscreen(View view) {
        //validation
        if(!validatePhone()){
            return;
        }
        //get data
        String _fullname = getIntent().getStringExtra("fullname");
        String _email = getIntent().getStringExtra("email");
        String _getUserEnterPhone = phone.getText().toString().trim();
        String _PhoneNo = "+91"+_getUserEnterPhone;

        Intent intent = new Intent(this,VerifyOtp.class);
        // pass all field to next activity
        intent.putExtra("fullname",_fullname);
        intent.putExtra("email",_email);
        intent.putExtra("phone",_PhoneNo);
        startActivity(intent);
        finish();
    }
}

