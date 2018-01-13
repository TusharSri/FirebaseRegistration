package com.example.tushar.firebaseregistration;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEdittext;
    private EditText passwordEdittext;
    private EditText reEnterPasswordEdittext;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        emailEdittext = findViewById(R.id.email_edittext);
        passwordEdittext = findViewById(R.id.password_edittext);
        reEnterPasswordEdittext = findViewById(R.id.password_reenter_edittext);
        Button registrationButton = findViewById(R.id.submit_button);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fetching email amd password
                String email = "";
                String pass = "";
                String reEnterPass = "";
                email = emailEdittext.getText().toString();
                pass = passwordEdittext.getText().toString();
                reEnterPass = reEnterPasswordEdittext.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(reEnterPass)){
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!reEnterPass.equals(pass)){
                    Toast.makeText(RegistrationActivity.this, "passwords are not matching", Toast.LENGTH_SHORT).show();
                    return;
                }
                //firebase registration
                if(null != firebaseAuth){
                    //showing progress dailog
                    progressDialog.setMessage("Registration in progress");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.hide();
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistrationActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
