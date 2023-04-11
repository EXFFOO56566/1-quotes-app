package com.xolu.dailyquote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //simple functions to intent activities.

        LinearLayout l1 = ( LinearLayout)findViewById(R.id.layout1);

        l1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }

        });


        LinearLayout l2 = ( LinearLayout)findViewById(R.id.layout2);

        l2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i);
            }

        });




    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }

}
