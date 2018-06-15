package com.project.esh_an.vemanafeeportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.esh_an.vemanafeeportal.activity.InitialScreenActivity;
import com.project.esh_an.vemanafeeportal.add_on.hostelUser;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class rentdetails extends AppCompatActivity implements View.OnClickListener {

    Button proceed;
     EditText roomnum,usn,amt;
     Spinner semester;
    ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseHostel;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rentdetails);


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

            roomnum = (EditText) findViewById(R.id.roomnum);
            usn = (EditText) findViewById(R.id.usn);
            amt = (EditText) findViewById(R.id.amt);

            proceed = (Button) findViewById(R.id.proceed);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseHostel = FirebaseDatabase.getInstance().getReference("hostelfee");

        semester = (Spinner)findViewById(R.id.sem);

            adapter = ArrayAdapter.createFromResource(this,R.array.semester,android.R.layout.simple_spinner_item);
            semester.setAdapter(adapter);


            semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i>0) {

                        Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected", Toast.LENGTH_LONG).show();
                    }
                    else{}

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            proceed.setOnClickListener(this);

        }
    private void addHostelStudent() {

        String dsem=semester.getSelectedItem().toString();
        String dusn = usn.getText().toString();
        String droom = roomnum.getText().toString();
        String damnt = amt.getText().toString().trim();


        if (!TextUtils.isEmpty(dsem)) {

            String id = databaseHostel.push().getKey();

            hostelUser hostel = new hostelUser(droom,dsem, dusn,damnt);

            databaseHostel.child(id).setValue(hostel);

            startActivity(getIntent());
            finish();

        }
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.proceed:
                if(validate()) {
                    addHostelStudent();
                    Intent i = new Intent(this, InitialScreenActivity.class);
                    i.putExtra("EditText", amt.getText().toString());
                    startActivity(i);
                    finishActivity(0);
                }
                break;

        }
    }


    private Boolean validate(){
        Boolean result = false;

        String vsem = semester.getSelectedItem().toString();
        String vrmn = roomnum.getText().toString();
        String vusn = usn.getText().toString();


        if(vsem.isEmpty() || vrmn.isEmpty() || vusn.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else   {
            result = true;
        }

        return result;
    }
}


