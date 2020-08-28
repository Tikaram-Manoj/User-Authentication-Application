package com.example.eztrainings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private Button backArrow;
    private EditText email;
    private EditText pass;
    private Button letMeIn;
    private Button forgotPass;
    private static final String TAG = "LoginScreen";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        backArrow = (Button) findViewById(R.id.backArrowBtn);
        email = (EditText) findViewById(R.id.emailET);
        pass = (EditText) findViewById(R.id.passET);
        letMeIn = (Button) findViewById(R.id.letMeInBtn);
        forgotPass = (Button) findViewById(R.id.forgotPassBtn);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        pb.setVisibility(View.GONE);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if ( currentUser != null )
                {
                    Log.d(TAG, "User Exist");
                    Log.d(TAG,"Username:" +currentUser.getEmail());
                    //Toast.makeText(LoginScreen.this, "User Exist", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Log.d(TAG, "User Doesn't Exist");
                }


            }
        };



       letMeIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pb.setVisibility(View.VISIBLE);
               String em = email.getText().toString();
               String pas = pass.getText().toString();
               if ( !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pas) )
               {
                   mAuth.signInWithEmailAndPassword(em, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               pb.setVisibility(View.GONE);
                               startActivity(new Intent(LoginScreen.this, ProfileScreen.class));
                               finish();
                               Toast.makeText(LoginScreen.this, "Signed In", Toast.LENGTH_LONG).show();

                           } else {
                               pb.setVisibility(View.GONE);
                               Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                           }
                       }
                   });

               }
               else
               {
                   pb.setVisibility(View.GONE);
                   Toast.makeText(LoginScreen.this, "Please fill both the fields", Toast.LENGTH_LONG).show();
               }
           }
       });

       forgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

       forgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(LoginScreen.this, ForgotPassActivity.class));
               finish();

           }
       });




        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, MainActivity.class));
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