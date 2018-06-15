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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.esh_an.vemanafeeportal.activity.InitialScreenActivity;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class clg_fee extends AppCompatActivity {

    Button ok;
    TextView c_name,c_usn,c_year,c_branch,c_email;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseStudents;
    ArrayList<String> userInfoList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clg_fee);

        c_name = (TextView) findViewById(R.id.name_clgfee);
        c_usn = (TextView) findViewById(R.id.usn_clgfee);
        c_year = (TextView) findViewById(R.id.year_clgfee);
        c_branch = (TextView) findViewById(R.id.branch_clgfee);
        c_email  = (TextView) findViewById(R.id.email_clgfee);

        final EditText c_amount = (EditText) findViewById(R.id.amnt);

        ok = findViewById(R.id.ok_clgfee);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(clg_fee.this, InitialScreenActivity.class);
                i.putExtra("EditText",c_amount.getText().toString());
                startActivity(i);

                finishActivity(0);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        databaseStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (currentUser.getEmail().equalsIgnoreCase(ds.getValue(user.class).getEmail())) {
                        user userInfo = ds.getValue(user.class);
                        c_name.setText(userInfo.name);
                        c_usn.setText(userInfo.usn);
                        c_year.setText(userInfo.year);
                        c_email.setText(userInfo.email);
                        c_branch.setText(userInfo.branch);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
 }
