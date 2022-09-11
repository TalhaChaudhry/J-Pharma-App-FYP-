package com.talhachaudhry.jpharmaappfyp.login_details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.talhachaudhry.jpharmaappfyp.models.User;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivitySignUpBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.signUpBtn.setOnClickListener(v -> {
            progressDialog.show();
            try {
                auth.createUserWithEmailAndPassword(binding.Email.getText().toString().trim(),
                                binding.Password.getText().toString()).
                        addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                UserModel user = new UserModel(binding.shopNameTv.getText().toString(),
                                        binding.Name.getText().toString(),
                                        binding.Password.getText().toString(), binding.addressTv.getText().toString(),
                                        binding.cityTv.getText().toString(), binding.contactTv.getText().toString(),
                                        binding.Email.getText().toString(), "");
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String id = task.getResult().getUser().getUid();
                                firebaseUser.sendEmailVerification().addOnSuccessListener(aVoid -> {
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Toast.makeText(SignUp.this, "User created, Verify your email to login",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), "Email is Invalid",
                                            Toast.LENGTH_SHORT).show();
                                    firebaseUser.delete();
                                });
                            } else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, "Above Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
            }

        });
        binding.loginTv.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), Login.class)));


    }
}