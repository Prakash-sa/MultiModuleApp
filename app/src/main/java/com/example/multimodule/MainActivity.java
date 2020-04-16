package com.example.multimodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(Intent.ACTION_VIEW).setClassName("com.subconscious.atomdigitaldetox","com.subconscious.atomdigitaldetox."+"MainActivity");
        startActivity(intent);
    }
}
