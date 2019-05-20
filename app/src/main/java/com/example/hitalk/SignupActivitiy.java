package com.example.hitalk;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hitalk.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivitiy extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 10;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText name;
    private Button signup;
    private String splash_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activitiy);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance(); //파이어 베이스 연동
        splash_background=mFirebaseRemoteConfig.getString(getString(R.string.rc_color)) ; //동적 백그라운드

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){ //버전이 롤리팝부터 적용 가능. 테마 이쁘게
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        email = (EditText) findViewById(R.id.signupActivity_edit_Email);
        name = (EditText) findViewById(R.id.signupActivity_edit_Name);
        password = (EditText) findViewById(R.id.signupActivity_edit_Password);
        signup = (Button) findViewById(R.id.signupActivity_edit_signUp);
        signup.setBackgroundColor(Color.parseColor(splash_background));


        //fire base Authentication
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString() == null || name.getText().toString() == null || password.getText().toString()==null ){
                      return;
                }


                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener( SignupActivitiy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {  //완료후 파이어베이스 커밋.
                                        UserModel userModel = new UserModel();
                                        userModel.userName = name.getText().toString();

                                        String uid = task.getResult().getUser().getUid();
                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            //회원 가입 완료후
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                SignupActivitiy.this.finish();

                                            }
                                        });


                            }
                        });
            }
        });

    }

}
