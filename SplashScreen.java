package com.project.esh_an.vemanafeeportal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.project.esh_an.vemanafeeportal.add_on.netConnection;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 1500;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);


        //net connection
        netConnection checkCon=new netConnection(this);
        if(checkCon.isConnected()==false){
            new AlertDialog.Builder(this)
                    .setTitle("WARNING")
                    .setMessage("No Internet Connection!!\n\nEnable it..!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

        imageView =(ImageView)findViewById(R.id.imageView);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.animation);
        imageView.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}