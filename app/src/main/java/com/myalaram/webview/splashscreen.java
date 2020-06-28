package com.myalaram.webview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {
    private static int SPLASH=5000;
    ImageView image1;
    TextView t1,t2,t3;
    Animation topanim,botanim,sanim;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
        sanim=AnimationUtils.loadAnimation(this,R.anim.sideanimation);
        topanim= AnimationUtils.loadAnimation(this,R.anim.topanimation);
        botanim= AnimationUtils.loadAnimation(this,R.anim.bottomanimation);
        t1=(TextView)findViewById(R.id.splashtext);
        t2=(TextView)findViewById(R.id.text10);
        t3=(TextView)findViewById(R.id.text11);
        image1=(ImageView)findViewById(R.id.splashimg);
        image1.setAnimation(topanim);
        t1.setAnimation(botanim);
        t2.setAnimation(botanim);
        t3.setAnimation(sanim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentx=new Intent(splashscreen.this, Bottemnavigation.class);
                startActivity(intentx);
                finish();
            }
        },SPLASH);

    }
}
