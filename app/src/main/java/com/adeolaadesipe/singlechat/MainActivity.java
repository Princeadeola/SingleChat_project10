package com.adeolaadesipe.singlechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String name;
    private String number;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
    }
}