package com.example.adminquickmed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.adminquickmed.R;

public class EditProfileActivity extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_upImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_back = findViewById(R.id.btn_back);
        btn_upImg = findViewById(R.id.btn_upImg);

        btn_back.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent myIntent = new Intent(EditProfileActivity.this, MainActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                                            startActivity(myIntent);
//                                            if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
//                                                getSupportFragmentManager().beginTransaction()
//                                                    .add(android.R.id.content, new HomeFragment())
//                                                    .commit();
//                                            }
                                        }
                                    });

        btn_upImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EditProfileActivity.this, UploadImageActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);

            }
        });

    }
}

