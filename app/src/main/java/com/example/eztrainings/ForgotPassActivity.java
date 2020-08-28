package com.example.eztrainings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private ProgressBar fPB;
    private Button backBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email = (EditText) findViewById(R.id.forgotEmailET);
        reset = (Button) findViewById(R.id.resetBTn);
        fPB = (ProgressBar) findViewById(R.id.progressBar3);
        backBtn = (Button) findViewById(R.id.forgotBackArrow);

        mAuth = FirebaseAuth.getInstance();

        fPB.setVisibility(View.GONE);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fPB.setVisibility(View.VISIBLE);
                String em = email.getText().toString().trim();
                if (!TextUtils.isEmpty(em) )
                {
                    mAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if ( task.isSuccessful() )
                            {
                                fPB.setVisibility(View.GONE);
                                Toast.makeText(ForgotPassActivity.this, "Check Email To Reset Password!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPassActivity.this, LoginScreen.class));
                                finish();

                            }
                            else
                            {
                                fPB.setVisibility(View.GONE);
                                Toast.makeText(ForgotPassActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                else
                {
                    fPB.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassActivity.this, "Please Enter Your Email",Toast.LENGTH_LONG).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassActivity.this, LoginScreen.class));
                finish();
            }
        });
    }
}