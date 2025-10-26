package com.example.kew_kredit_ilham1125100160_tii25;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Button btDataPetugasHome = (Button) findViewById(R.id.btDataPetugasHome);
        Button btDataMotorHome = (Button) findViewById(R.id.btDataMotorHome);
        Button btDataKreditorHome = (Button) findViewById(R.id.btDataKreditorHome);

        btDataPetugasHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KlikbtDataPetugasHome();
            }
        });

        btDataMotorHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KlikbtDataMotorHome();
            }
        });

        btDataKreditorHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KlikbtDataKreditorHome();
            }
        });
    }

    public void KlikbtDataPetugasHome() {
        Intent i = new Intent(getApplicationContext(), DataPetugasActivity.class);
        ;//target = nama class
        startActivity(i);
    }

    public void KlikbtDataMotorHome() {
        Intent i = new Intent(getApplicationContext(), DataMotorActivity.class);
        startActivity(i);
    }

    public void KlikbtDataKreditorHome() {
        Intent i = new Intent(getApplicationContext(), DataKreditorActivity.class);
        startActivity(i);
    }
}