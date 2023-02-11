package com.varunkumar123.jobseeker.recruiter.recruiterfragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.Recruiter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecruiterEditProfile extends Fragment {
    FloatingActionButton fbutton;
    CircleImageView uploadImage;
    private StorageReference mStorageRef;
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    Button updateProfile_btn;
    String fileName,Name,Email,PhoneNo,user_id,Address,Profile_title,Company_Name,Functional_area;
    EditText fullName,email,phoneNo,address,profileTitle,companyName,functionalArea;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recruiter_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootNode = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        updateProfile_btn = view.findViewById(R.id.update_Recruiter_profile);
        fbutton = view.findViewById(R.id.capture_image);
        uploadImage = view.findViewById(R.id.update_recruiter_profileimg);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //Edittext Hoooks
        fullName = view.findViewById(R.id.recruiter_profileName);
        email = view.findViewById(R.id.recruiter_email);
        phoneNo = view.findViewById(R.id.recruiter_phoneNo);
        address = view.findViewById(R.id.recruiter_address);
        profileTitle = view.findViewById(R.id.recruiter_profileTitle);
        companyName = view.findViewById(R.id.recruiter_companyName);
        functionalArea = view.findViewById(R.id.recruiter_functionalArea);
        phoneNo.setText(mAuth.getCurrentUser().getPhoneNumber());
        phoneNo.setEnabled(false);

        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult(intent,1001);
            }
        });

        updateProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadsImage();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1001){
                // gallery handling code here
                galleryHandlingCode(data);

            }
        }
    }

    private void galleryHandlingCode(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(selectedImage,filePath,null,null,null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String pictuePath = c.getString(columnIndex);
        c.close();
        Bitmap thumbnail = (BitmapFactory.decodeFile(pictuePath));
        uploadImage.setImageBitmap(thumbnail);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
     thumbnail.compress(Bitmap.CompressFormat.JPEG,100,bytes);
       bb = bytes.toByteArray();

    }
    byte[] bb;
    private void uploadsImage() {
        if(bb!=null) {
            fileName = System.currentTimeMillis()+".jpg";
            StorageReference storageReference = mStorageRef.child("myimage/"+fileName);
            storageReference.putBytes(bb).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        updateData();
                        Toast.makeText(getActivity(), "Successful upload", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "upload failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void updateData() {
        myRef = rootNode.getReference("Users");
        // get value
        user_id = mAuth.getCurrentUser().getUid();
        Name = fullName.getText().toString();
        Email = email.getText().toString();
         PhoneNo = phoneNo.getText().toString();
        Address = address.getText().toString();
        Profile_title = profileTitle.getText().toString();
        Company_Name = companyName.getText().toString();
        Functional_area = functionalArea.getText().toString();

        Recruiter recruiter = new Recruiter(Name,Email,PhoneNo,user_id,Address,fileName,Profile_title,Company_Name,Functional_area);
        myRef.child("Recruiter").child(user_id).setValue(recruiter);


    }
}
