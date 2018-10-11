package com.avigail.android.quizexpert;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener{


    /** URL for getting quiz data */
    private static final String REQUEST_URL = "https://opentdb.com/api.php?";

    private static String urlBuild = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        final Button button = findViewById(R.id.startGame);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to get the Game screen to start it :
                Intent i = new Intent(MainActivity.this,GameActivity.class);
                i.putExtra("url", urlBuild);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_order_by_category_key)) ||
                key.equals(getString(R.string.settings_order_by_num_of_questions_key)) ||
                key.equals(getString(R.string.settings_order_by_difficulty_key))
                ) {

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            String numOfQuestion = sharedPrefs.getString(
                    getString(R.string.settings_order_by_num_of_questions_key),
                    getString(R.string.settings_order_by_num_of_questions_default));

            String difficulty = sharedPrefs.getString(
                    getString(R.string.settings_order_by_difficulty_key),
                    getString(R.string.settings_order_by_difficulty_default));

            String category = sharedPrefs.getString(
                    getString(R.string.settings_order_by_category_key),
                    getString(R.string.settings_order_by_category_default));

            Uri baseUri = Uri.parse(REQUEST_URL);

            Uri.Builder uriBuilder = baseUri.buildUpon();

            // Append query parameter and its value :

            int numOfQ = Integer.parseInt(numOfQuestion);


            if (!(numOfQ < 0 && numOfQ > 50)){
                uriBuilder.appendQueryParameter(getString(R.string.settings_order_by_num_of_questions_key), numOfQuestion);
            }

            if (!difficulty.equals("any")){
                uriBuilder.appendQueryParameter(getString(R.string.settings_order_by_difficulty_key), difficulty);
            }

            uriBuilder.appendQueryParameter(getString(R.string.settings_order_by_category_key), category);

            urlBuild = uriBuilder.toString();

            Log.e("API ",uriBuilder.toString());


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
