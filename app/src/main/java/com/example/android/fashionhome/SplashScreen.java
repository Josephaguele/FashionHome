package com.example.android.fashionhome;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.icu.text.RelativeDateTimeFormatter.Direction.THIS;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/21/2017.
 */

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView iv =(ImageView) findViewById(R.id.imageView);
         Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        iv.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                Intent i = new Intent(getApplicationContext(), CatalogActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

  /*      Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent (SplashScreen.this, CatalogActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    protected void onPause(){
        super.onPause();
        finish();*/
    }
}
