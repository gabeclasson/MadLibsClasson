package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String[] madLibs;
    private String[] titles;
    private String[] madLibsSurprise;
    private String[] titlesSurprise;

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
        //adapted from https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
        // listens for when the spinner updates
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != titles.length)
                    resetSpinnerAdapter();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        // arrays of madlib text and title
        madLibs = getResources().getStringArray(R.array.madLibs);
        titles = getResources().getStringArray(R.array.spinner_elements);
        // these surprise arrays are to store the same contents of madLibs and titles with the surprise madlib put in at the end
        madLibsSurprise = new String[madLibs.length + 1];
        int i;
        for (i = 0; i < madLibs.length; i++)
            madLibsSurprise[i] = madLibs[i];
        madLibsSurprise[i] = getString(R.string.surpriseMadLib);
        titlesSurprise = new String[titles.length + 1];
        int j;
        for (j = 0; j < titles.length; j++)
            titlesSurprise[j] = titles[j];
        titlesSurprise[j] = getString(R.string.surpriseTitle);
    }

    /**
     * Generates a madlib based on user entered data. Opens a new activity.
     * @param v
     */
    public void generate(View v) {
        String noun = ((EditText) findViewById(R.id.editNoun)).getText().toString().trim();
        String adjective = ((EditText) findViewById(R.id.editAdjective)).getText().toString().trim();
        String adverb = ((EditText) findViewById(R.id.editAdverb)).getText().toString().trim();
        String number = ((EditText) findViewById(R.id.editNumber)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editName)).getText().toString().trim();
        String verb = ((EditText) findViewById(R.id.editVerb)).getText().toString().trim();
        int madLibIndex = ((Spinner) findViewById(R.id.spinnerSelector)).getSelectedItemPosition();
        if (noun.matches(".*\\w+.*") && adjective.matches(".*\\w+.*") && adverb.matches(".*\\w+.*") && number.matches(".*\\w+.*") && name.matches(".*\\w+.*") && verb.matches(".*\\w+.*")){
            String message = madLibsSurprise[madLibIndex];
            String title = titlesSurprise[madLibIndex];
            Intent intent = new Intent(this, MadLibDisplay.class);
            intent.putExtra(MadLibDisplay.MAD_LIB_TEXT, createMadLib(noun, adjective, adverb, number, name, verb, message));
            intent.putExtra(MadLibDisplay.MAD_LIB_TITLE, title);
            startActivity(intent);
            resetSpinnerAdapter();
        }
        else{
            Context context = getApplicationContext();
            String text = getString(R.string.errorMessage);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Given user entered data a string containing the raw text of a madlib, returns a completed mad lib.
     * @param noun
     * @param adjective
     * @param adverb
     * @param number
     * @param name
     * @param verb
     * @param rawMadLib The raw text of a madlib without any words substituted into it yet. The rawMadLib should use the following words in its text which will all be replaced by user entered data: #noun#, #adjective#, #adverb#, #number#, #name#, and #verb#.
     * @return A completed mad lib mad using the information provided in the parameters
     */
    public String createMadLib(String noun, String adjective, String adverb, String number, String name, String verb, String rawMadLib){
        return rawMadLib.replace("#noun#", noun).replace("#adjective#", adjective).replace("#adverb#", adverb).replace("#number#", number).replace("#name#", name).replace("#verb#", verb);
    }

    /**
     * Chooses a random madlib and updates the spinner to match. Has a probability of giving the surprise mad lib.
     * @param v
     */
    public void surprise(View v) {
        Spinner s = findViewById(R.id.spinnerSelector);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        int numItems = madLibsSurprise.length;
        int choice = (int) (Math.random() * (numItems));
        if (choice >= numItems - 1){ // if the surprise mad lib was selected
            setSpinnerAdapter(titlesSurprise); // to add another option to the spinner, we need to use a different spinner adapter
            s.setSelection(choice);
        }
        else {
            resetSpinnerAdapter(); // if you generate a mad lib, the surprise option will disappear
            s.setSelection(choice);
        }
        s.startAnimation(rotate);
    }

    /**
     * Resets the spinner adapter so that the surprise option is no longer visible
     */
    private void resetSpinnerAdapter() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSelector);
        if (spinner.getCount() > titles.length){
            int spinnerSelection = spinner.getSelectedItemPosition();
            if (spinnerSelection >= titles.length)
                spinnerSelection = 0;
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_elements, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(spinnerSelection);
        }
    }

    /**
     * Sets the spinner adapter for the selection of the mad lib to match a String[]
     * @param array an array of strings to be used for the spinner adapter for the selection of the mad lib.
     */
    private void setSpinnerAdapter(String[] array){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSelector);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
