package com.example.jobseeker.student.studentfragment;

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

import com.example.jobseeker.R;
import com.example.jobseeker.model.Student;
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


public class StudentEditProfile extends Fragment {
    FloatingActionButton cameraBtn;
    CircleImageView uploadImage;
    private StorageReference mStorageRef;
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    Button update_studentProfile_btn;
    String fileName,Name,Email,PhoneNo,DateOfBirth,user_id,student_Location,Education,YearOfPassing,Experience,Skills;
    EditText fullName,email,phoneNo,dateOfBirth,location,education,yearOfPassing,experience,skills;


    public StudentEditProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootNode = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        update_studentProfile_btn = view.findViewById(R.id.update_studentProfile_btn);
        cameraBtn = view.findViewById(R.id.capture_student_image);
        uploadImage = view.findViewById(R.id.update_student_profileimg);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //Edittext Hoooks
        fullName = view.findViewById(R.id.student_update_profileName);
        email = view.findViewById(R.id.student_update_email);
        phoneNo = view.findViewById(R.id.student_phoneNo);
        dateOfBirth = view.findViewById(R.id.studentr_birth_date);
        location = view.findViewById(R.id.student_location);
        education = view.findViewById(R.id.student_update_education);
        yearOfPassing = view.findViewById(R.id.student_Update_PassYear);
        experience = view.findViewById(R.id.student_update_exprience);
        skills = view.findViewById(R.id.student_update_skills);
        email.setText(mAuth.getCurrentUser().getEmail());
        email.setEnabled(false);
        // get profile images
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult(intent,1002);
            }
        });
        update_studentProfile_btn.setOnClickListener(new View.OnClickListener() {
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
            if(requestCode == 1002){
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
        if (bb != null) {
            fileName = System.currentTimeMillis() + ".jpg";
            StorageReference storageReference = mStorageRef.child("myimage/" + fileName);
            storageReference.putBytes(bb).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        updateData();
                        Toast.makeText(getActivity(), "Successful upload", Toast.LENGTH_SHORT).show();
                    } else {
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
        DateOfBirth = dateOfBirth.getText().toString();
        student_Location = location.getText().toString();
        Education = education.getText().toString();
        YearOfPassing = yearOfPassing.getText().toString();
        Experience = experience.getText().toString();
        Skills = skills.getText().toString();

        Student student = new Student(user_id,Name,PhoneNo,DateOfBirth,student_Location,Experience,Education,YearOfPassing,Email,fileName,Skills);
        myRef.child("Student").child(user_id).setValue(student);

    }
}


