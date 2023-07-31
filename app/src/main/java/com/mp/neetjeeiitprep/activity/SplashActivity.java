package com.mp.neetjeeiitprep.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private Pref pref;
    private ProgressBar pbSplash;
    private Boolean isTimerComplete=false, isAdMobInitialized=false;
    private CountDownTimer timer;
    private String TAG = "SplashActivityTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // getHash();
        pbSplash = findViewById(R.id.pbSplash);
        pref = new Pref(this);

        StartTimer();

    }

    private void StartTimer() {
        timer = new CountDownTimer(10, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: millisUntilFinished-> " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                isTimerComplete = true;
                Log.d(TAG, "onFinish: timer");
               // if (isAdMobInitialized) {
                    Log.d(TAG, "onFinish: 2");
                    if (!pref.getString(Constant.USER_ID).isEmpty()) {
                        loadActivity(MainActivity.class);
                    } else {
                        loadActivity(LandingActivity.class);
                    }
                    pbSplash.setVisibility(View.GONE);
               // }
            }
        };
        timer.start();

    }

    private void loadActivity(Class mClass) {
        Intent intent = new Intent(this, mClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void getHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mp.neetjeeiitprep",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                MessageDigest.getInstance("SHA").toString();
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("yio", "getHash: 1");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("yio", "getHash: 2");
        }
    }
}
