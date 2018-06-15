package com.project.esh_an.vemanafeeportal;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.widget.Toast.LENGTH_LONG;

public class Register extends AppCompatActivity {
    Button ok;
    EditText name, usn, email, pswd, cnfpswd;
    Spinner spbranch, spyear;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseStudents;
    TextView result;
    private CheckBox checkBox2;

    ArrayAdapter<CharSequence> adapter, adapter1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


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

        firebaseAuth = FirebaseAuth.getInstance();

        databaseStudents = FirebaseDatabase.getInstance().getReference("students");


        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        name = (EditText) findViewById(R.id.name);
        usn = (EditText) findViewById(R.id.usn);
        email = (EditText) findViewById(R.id.email);
        pswd = (EditText) findViewById(R.id.pswd);
        cnfpswd = (EditText) findViewById(R.id.cnfpswd);
        progressDialog =new ProgressDialog(this);

        spbranch = (Spinner) findViewById(R.id.spbranch);
        spyear = (Spinner) findViewById(R.id.spyear);

        adapter = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.spyear, android.R.layout.simple_spinner_item);

        spbranch.setAdapter(adapter);
        spyear.setAdapter(adapter1);

        spbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected", LENGTH_LONG).show();
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected", LENGTH_LONG).show();
                } else {
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    pswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cnfpswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    pswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cnfpswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        ok = (Button) findViewById(R.id.ok);
        result = (TextView) findViewById(R.id.md5);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                EditText et = findViewById(R.id.pswd);
                String pass1 = et.getText().toString();
                EditText et2 = findViewById(R.id.cnfpswd);
                String pass2 = et2.getText().toString();
                et.getEditableText().toString();

                if (!email.getEditableText().toString().isEmpty() ){
               //     if (!email.getText().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    if (pass1.length() > 6) {
                        if (pass1.equals(pass2)) {
                            //      startActivity(new Intent("com.project.esh_an.vemanafeeportal"));
                            if (validate()) {

                                progressDialog.setMessage("Registering User..!!");
                                progressDialog.show();


                                //Upload data to the database
                                String user_email = email.getText().toString().trim();
                                String user_password = pswd.getText().toString().trim();

                                firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                computeMD5Hash(pswd.toString());
                                                addStudent();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                                builder.setMessage("ThankYou for Registering with us...!");
                                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Log.d("AlertDialog","positive");
                                                        startActivity(new Intent(Register.this, Signin.class));
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();

                                                //startActivity(new Intent(Register.this, Signin.class));
                                            } else {
                                                    progressDialog.dismiss();
                                                    Snackbar.make(view, "Error Occurred", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                                }
                                    }
                                });
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                            builder.setTitle("Attention !");
                            builder.setMessage("Passwords Should be Same");
                            AlertDialog dialog = builder.show();
                        }
                    } else {
                        Snackbar.make(view, "password must be greater than 6 digits", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
                else{
                    Snackbar.make(view,"Enter All Details",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    private void addStudent() {

        String dname = name.getText().toString().trim();
        String dusn = usn.getText().toString();
        String rbranch = spbranch.getSelectedItem().toString();
        String rspyear = spyear.getSelectedItem().toString();
        String remail = email.getText().toString().trim();
        String rpswd = pswd.getText().toString();
        //String rcnfpswd = cnfpswd.getText().toString();
        String empass = result.getText().toString().trim();

        if (!TextUtils.isEmpty(dname)) {

            String id = databaseStudents.push().getKey();

            user student = new user(dname, dusn, rbranch, rspyear, remail, empass);

            databaseStudents.child(id).setValue(student);

        } else {}
    }

    private Boolean validate(){
        Boolean result = false;

        String vname = name.getText().toString();
        String password = pswd.getText().toString();
        String vemail = email.getText().toString();

        if(vname.isEmpty() || password.isEmpty() || vemail.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if (!vemail.contains("@")) {
            Toast.makeText(this, "Enter the Correct Email", Toast.LENGTH_SHORT).show();
        }//else if (!vemail.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            //Toast.makeText(this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        else   {
            result = true;
        }

        return result;
    }

    public void computeMD5Hash(String password){
        try{
            //create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for(int i=0;i<messageDigest.length;i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length()<2)
                    h = "0"+h;
                MD5Hash.append(h);
            }
            result.setText(MD5Hash);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
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