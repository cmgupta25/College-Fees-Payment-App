package com.project.esh_an.vemanafeeportal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;

import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class myprofile extends Signin implements View.OnClickListener{


    TextView u_name,u_usn,u_year,u_branch,u_email;
    ImageView imageView;
    private FirebaseAuth firebaseAuth;
    private static final int REQ_CODE=9001;
    DatabaseReference databaseStudents;
    ArrayList<String> userInfoList = new ArrayList<>();

    private static final String TAG=null;
// ...

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getOverflowMenu();

        setContentView(R.layout.activity_myprofile);
        u_name = (TextView) findViewById(R.id.name_myprofile);
        u_usn = (TextView) findViewById(R.id.usn_myprofile);
        u_year = (TextView) findViewById(R.id.year_myprofile);
        u_branch = (TextView) findViewById(R.id.branch_myprofile);
        u_email  = (TextView) findViewById(R.id.email_myprofile);
        imageView =(ImageView) findViewById(R.id.imageView);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        databaseStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (currentUser.getEmail().equalsIgnoreCase(ds.getValue(user.class).getEmail())) {
                        user userInfo = ds.getValue(user.class);
                        u_name.setText(userInfo.name);
                        u_usn.setText(userInfo.usn);
                        u_year.setText(userInfo.year);
                        u_email.setText(userInfo.email);
                        u_branch.setText(userInfo.branch);
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
                    .setMessage("Internet Connection not Available..!!!")
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
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        netConnection checkCon=new netConnection(this);
        if(checkCon.isConnected()==false){
            new AlertDialog.Builder(this)
                    .setMessage("Internet Connection not Available..!!!")
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



