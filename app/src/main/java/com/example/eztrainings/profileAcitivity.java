package com.example.eztrainings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class profileAcitivity extends AppCompatActivity {
    private ImageButton profileImage;
    private Button done;
    private ProgressBar pg;
    private final static int GALLERY_CODE = 1;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivity);

        profileImage = (ImageButton) findViewById(R.id.profileImage);
        done = (Button) findViewById(R.id.pfActBtn);
        pg = (ProgressBar) findViewById(R.id.progressBar4);

        pg.setVisibility(View.GONE);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);


            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);
                flag = 1;
                if ( flag == 1 ) {
                    pg.setVisibility(View.GONE);
                    startActivity(new Intent(profileAcitivity.this, MainActivity.class));
                    Toast.makeText(profileAcitivity.this, "SUCCESS", Toast.LENGTH_LONG).show();

                }
            }
        });

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == GALLERY_CODE && resultCode == RESULT_OK )
        {
            Uri mImageUri = data.getData();
            CropImage.activity(mImageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);

        }
        if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if ( resultCode == RESULT_OK )
            {
                Uri resultUri = result.getUri();
                profileImage.setImageURI(resultUri);
            }
            else if ( resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }
}