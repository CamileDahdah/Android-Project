package com.example.camilledahdah.finalandroidproject.screens.splash;

/**
 * Created by camilledahdah on 4/21/18.
 */

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.data.local.LocalStorageManager;
import com.example.camilledahdah.finalandroidproject.models.User;
import com.example.camilledahdah.finalandroidproject.screens.authentication.AuthenticationActivity;
import com.example.camilledahdah.finalandroidproject.screens.main.MainActivity;
import com.example.camilledahdah.finalandroidproject.screens.main.trips.PostTripFragment;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {

    LocalStorageManager localStorageManager;


    private ValueAnimator valueAnimator;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final MediaPlayer mp = MediaPlayer.create(SplashActivity.this, R.raw.intro_splash);
        mp.start();

        logoImageView = findViewById(R.id.logo);

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(4000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                logoImageView.setAlpha(value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                gotoNextScreen();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
    }

    private void gotoNextScreen() {
        Intent intent;

        localStorageManager = LocalStorageManager.getInstance(getApplicationContext());
        User user = localStorageManager.getUser();
        if (user == null) {
            intent = new Intent(this, AuthenticationActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
