package com.xolu.dailyquote;



import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    String[] mTestArray;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    TextView textView ;
    SharedPreferences preference_shared, text_shared;
    SharedPreferences.Editor edit;


    private AdView mAdView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    //admob implimentation

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        mTestArray = getResources().getStringArray(R.array.QUOTES); //get a random quote from string
        textView = (TextView) findViewById(R.id.TextView); //identify text and link it to textview


        //check if it's first run , and checkin' time and date
        preference_shared = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        text_shared = this.getSharedPreferences("TEXT", MODE_PRIVATE);

        if (preference_shared.getBoolean("isFirstRun", true)) {
            //if first run code

            textView.setText(mTestArray[(0) % (mTestArray.length)]); //take a random text from strings


            text_shared.edit().putString("Text", textView.getText().toString()).apply(); //store picked text




            Save_Date();

        } else {

            if (!Objects.equals(preference_shared.getString("Date", ""), dateFormat.format(date))) {
                // if new day , excute this


                int idx = new Random().nextInt(mTestArray.length); // get a random text from list array

                textView.setText(mTestArray[idx]); // set the random text

                text_shared.edit().putString("Text", textView.getText().toString()).apply(); // تخزين النص الجديد ليتم عرضه طوال اليوم


                Save_Date(); // save the date for the next run
            } else {

                // if the same they ,

                textView.setText(text_shared.getString("Text", "")); //put the the already selected text
            }
        }
        preference_shared.edit().putBoolean("isFirstRun", false).apply();


    }




    public void Save_Date() { //saving date

        preference_shared = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        edit = preference_shared.edit();
        edit.clear();
        edit.putString("Date", dateFormat.format(date));
        edit.apply();

    }

    //// android Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
                //Create Intent for Product Activity
                Intent productIntent = new Intent(this,AboutActivity.class);
                //Start Product Activity
                startActivity(productIntent);
                return true;
        }

        if (id == R.id.action_like) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            return true;
        }
        if (id == R.id.action_more) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=6720847872553662727"));
            startActivity(intent);
            return true;

        }

        if (id == R.id.action_share) {
            int applicationNameId = getApplicationInfo().labelRes;
            final String appPackageName = getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
            String text = "Install this cool application: ";
            String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
            i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
            startActivity(Intent.createChooser(i, "Share link:"));
            return true;

        }

        return super.onOptionsItemSelected(item);

        // end option menu
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}