package com.sahabatpnj.tommorowapps.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sahabatpnj.tommorowapps.BaseActivity;
import com.sahabatpnj.tommorowapps.Database.UserManager;
import com.sahabatpnj.tommorowapps.R;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setActionBar("Splash Screen", false, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserManager.isUserLoggedIn()){
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                    finish();
                }

            }
        },2000);
    }
}
