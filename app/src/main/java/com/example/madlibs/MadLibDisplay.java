package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MadLibDisplay extends AppCompatActivity {
    public static final String MAD_LIB_TEXT = "madLibText";
    public static final String MAD_LIB_TITLE = "madLibTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mad_lib_display);

        Intent intent = getIntent();
        String madLibText = intent.getStringExtra(MAD_LIB_TEXT);
        String madLibTitle = intent.getStringExtra(MAD_LIB_TITLE);

        ((TextView) findViewById(R.id.madLibTitleView)).setText(madLibTitle);
        ((TextView) findViewById(R.id.madLibTextView)).setText(madLibText);
    }
}
