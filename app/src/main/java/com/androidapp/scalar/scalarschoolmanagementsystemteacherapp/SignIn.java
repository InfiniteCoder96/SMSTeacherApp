package com.androidapp.scalar.scalarschoolmanagementsystemteacherapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    private Button mRegisterBtn;
    private EditText mEmail,mPassword,mConfirmPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent intent = new Intent(SignIn.this, WelcomeSplash.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirmpassword);

        mRegisterBtn = (Button) findViewById(R.id.registerbtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String confirmPassword = mConfirmPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
                    if(password.equals(confirmPassword) && password.length() > 6){
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignIn.this, "Sign Up Failed.Please try again", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SignIn.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    String teacher_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_teacher_id = FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").child(teacher_id);
                                    current_teacher_id.setValue(true);
                                    Intent intent = new Intent(SignIn.this, FirstWelcomeSplash.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        });
                    }
                    else if(!password.equals(confirmPassword)){
                        mPassword.setError("Password & Confirm password is not match");
                        mPassword.requestFocus();
                        return;

                    }
                    else if(password.length() < 6){
                        mPassword.setError("The password must be at least 6 characters");
                        mPassword.requestFocus();
                        return;
                    }

                }
                else if(email.isEmpty()){
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }
                else if(password.isEmpty()){
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;
                }
                else if(confirmPassword.isEmpty()){
                    mConfirmPassword.setError("Password Confirmation is required");
                    mConfirmPassword.requestFocus();
                    return;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListner);
    }
}
