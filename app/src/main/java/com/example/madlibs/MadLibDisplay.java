package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MadLibDisplay extends AppCompatActivity {
    public static final String MAD_LIB_TEXT = "madLibText";
    public static final String MAD_LIB_TITLE = "madLibTitle";

    private String madLibText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mad_lib_display);

        Intent intent = getIntent();
        String madLibText = intent.getStringExtra(MAD_LIB_TEXT);
        String madLibTitle = intent.getStringExtra(MAD_LIB_TITLE);
        this.madLibText = madLibText;
        ((TextView) findViewById(R.id.madLibTitleView)).setText(madLibTitle);
        ((TextView) findViewById(R.id.madLibTextView)).setText(madLibText);
    }

    /**
     * Shares the generated mad lib
     * @param gabeClasson
     */
    public void shareInfo (View gabeClasson) {
        String myMessage = madLibText;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, myMessage);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }
}
