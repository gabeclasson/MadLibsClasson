package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    public void generate(View v) {
        String noun = ((EditText) findViewById(R.id.editNoun)).getText().toString().trim();
        String adjective = ((EditText) findViewById(R.id.editAdjective)).getText().toString().trim();
        String adverb = ((EditText) findViewById(R.id.editAdverb)).getText().toString().trim();
        String number = ((EditText) findViewById(R.id.editNumber)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString().trim();
        String verb = ((EditText) findViewById(R.id.editVerb)).getText().toString().trim();
        String madLibTitle = ((Spinner) findViewById(R.id.spinnerSelector)).getSelectedItem().toString();
        if (noun.matches(".*\\w+.*") && adjective.matches(".*\\w+.*") && adverb.matches(".*\\w+.*") && number.matches(".*\\w+.*") && name.matches(".*\\w+.*") && verb.matches(".*\\w+.*")){
            String message = searchMadLibs(madLibTitle);
            Intent intent = new Intent(this, MadLibDisplay.class);
            intent.putExtra(MadLibDisplay.MAD_LIB_TEXT, createMadLib(noun, adjective, adverb, number, name, verb, message));
            intent.putExtra(MadLibDisplay.MAD_LIB_TITLE, madLibTitle);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            String text = getString(R.string.errorMessage);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    private String searchMadLibs(String title){
        String[] libs = getResources().getStringArray(R.array.madLibs);
        String[] titles = getResources().getStringArray(R.array.spinner_elements);
        for (int i = 0; i < titles.length; i++)
            if (title.equals(titles[i]))
                return libs[i];
         return getString(R.string.surpriseMadLib);
    }
    public String createMadLib(String noun, String adjective, String adverb, String number, String name, String verb, String rawMadLib){
        return rawMadLib.replace("#noun#", noun).replace("#adjective#", adjective).replace("#adverb#", adverb).replace("#number#", number).replace("#name#", name).replace("#verb#", verb);
    }
}
