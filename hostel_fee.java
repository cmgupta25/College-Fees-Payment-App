package com.project.esh_an.vemanafeeportal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.esh_an.vemanafeeportal.add_on.netConnection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class hostel_fee extends AppCompatActivity implements View.OnClickListener{

    TextView girls_rent,boys_rent;
    ImageView girl,boy;

protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hostel_fee);

        girls_rent = findViewById(R.id.girls_rent);
        girls_rent.setOnClickListener(this);

        boys_rent = findViewById(R.id.boys_rent);
        boys_rent.setOnClickListener(this);

        girl = (ImageView)findViewById(R.id.imagegirl);
        girl.setOnClickListener(this);

        boy = (ImageView) findViewById(R.id.imageboy);
        boy.setOnClickListener(this);


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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.girls_rent: startActivity(new Intent(this,rentdetails.class));
                finishActivity(0);
                break;

            case R.id.boys_rent: startActivity(new Intent(this,rentdetails.class));
                finishActivity(0);
                break;

            case R.id.imagegirl: startActivity(new Intent(this,rentdetails.class));
                finishActivity(0);
                break;

            case R.id.imageboy: startActivity(new Intent(this,rentdetails.class));
                finishActivity(0);
                break;
        }

    }
}
