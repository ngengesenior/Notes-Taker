package com.ngenge.apps.notestaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String userId;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        Objects.requireNonNull(getSupportActionBar()).hide();

        preferences = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
        userId = preferences.getString(getString(R.string.user_id), null);


        new Handler()
                .postDelayed(() -> {


                    if (userId != null) {

                        startActivity(new Intent(context,MainActivity.class));
                        finish();
                        Log.d("USERID","User id is not null "+ userId);
                    } else {

                        Intent intent = new Intent(context,AuthenticationActivity.class);
                        startActivity(intent);
                        Log.d("USERID","User id null");
                        finish();
                    }


                }, 2000);
    }
}
