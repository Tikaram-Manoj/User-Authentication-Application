package com.example.eztrainings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    private Button backArrow;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText contact;
    private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "SignUpScreen";
    private ProgressBar sPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backArrow = (Button) findViewById(R.id.signUpBackArrowBtn);
        name = (EditText) findViewById(R.id.nameET);
        email = (EditText) findViewById(R.id.signUpEmailET);
        password = (EditText) findViewById(R.id.signUpPassET);
        confirmPassword = (EditText) findViewById(R.id.signUpConfirmPassET);
        contact = (EditText) findViewById(R.id.contactET);
        signup = (Button) findViewById(R.id.signUpBtn);
        sPB = (ProgressBar) findViewById(R.id.progressBar2);

        sPB.setVisibility(View.GONE);



        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser presentUser = firebaseAuth.getCurrentUser();
                if ( presentUser != null )
                {
                    Log.d(TAG, "User Exist");
                    //Toast.makeText(SignUp.this, "User Exist", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.d(TAG, "User Doesn't Exist");
                    //Toast.makeText(SignUp.this, "User Doesn't Exist", Toast.LENGTH_LONG).show();
                }

            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPB.setVisibility(View.VISIBLE);
                String nameST = name.getText().toString();
                String contactST = contact.getText().toString();
                String emailST = email.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                if (!TextUtils.isEmpty(nameST) && !TextUtils.isEmpty(emailST) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmPass) && !TextUtils.isEmpty(contactST)) {
                    if (contactST.length() == 10) {
                        if (pass.equals(confirmPass)) {
                            mAuth.createUserWithEmailAndPassword(emailST, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sPB.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_LONG).show();
                                        email.setText("");
                                        startActivity(new Intent(SignUp.this,profileAcitivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        sPB.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            sPB.setVisibility(View.GONE);
                            password.setText("");
                            confirmPassword.setText("");
                            Toast.makeText(SignUp.this, "Check Your Password Again!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        sPB.setVisibility(View.GONE);
                        contact.setText("");
                        Toast.makeText(SignUp.this, "Check Your Number Again!", Toast.LENGTH_LONG).show();

                    }
                } else {
                    sPB.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "You have to fill all the blanks.", Toast.LENGTH_LONG).show();
                }

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mAuth != null )
        {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}