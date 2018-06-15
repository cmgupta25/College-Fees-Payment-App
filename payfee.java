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
import android.widget.Button;

import com.project.esh_an.vemanafeeportal.activity.InitialScreenActivity;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class payfee extends AppCompatActivity implements View.OnClickListener{

    Button colg_fee,hostel_rent,mess_fee,others;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payfee);

        colg_fee = (Button) findViewById(R.id.colg_fee);
        hostel_rent = (Button) findViewById(R.id.hostel_rent);
        mess_fee = (Button) findViewById(R.id.mess_fee);
        others = (Button) findViewById(R.id.others);

        colg_fee.setOnClickListener(this);
        hostel_rent.setOnClickListener(this);
        mess_fee.setOnClickListener(this);
        others.setOnClickListener(this);


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
            case R.id.colg_fee:
                startActivity(new Intent(this,clg_fee.class));
                finishActivity(0);
                break;
            case R.id.hostel_rent:
                startActivity(new Intent(this,hostel_fee.class));
                finishActivity(0);
                break;
            case R.id.mess_fee:
                startActivity(new Intent(this,messfee.class));
                finishActivity(0);
                break;
            case R.id.others:
                break;
        }
    }
}
