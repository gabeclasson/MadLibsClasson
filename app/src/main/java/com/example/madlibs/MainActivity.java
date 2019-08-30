package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // adapted from https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSelector);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_elements, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void generate(View v){
        String noun = ((EditText) findViewById(R.id.editNoun)).getText().toString().trim();
        String adjective = ((EditText) findViewById(R.id.editAdjective)).getText().toString().trim();
        String adverb = ((EditText) findViewById(R.id.editAdverb)).getText().toString().trim();
        String number = ((EditText) findViewById(R.id.editNumber)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString().trim();
        String verb = ((EditText) findViewById(R.id.editVerb)).getText().toString().trim();
    }
}
