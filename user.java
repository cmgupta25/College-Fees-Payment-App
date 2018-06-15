package com.project.esh_an.vemanafeeportal;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ESH_AN on 12/12/2017.
 */

public class user {
    String name,usn,branch,year,email,pswd;

    public user(String dsem, String dusn, String droom, String damnt) {}

    public user(String name, String usn, String branch, String year, String email, String pswd) {
        this.name = name;
        this.usn = usn;
        this.branch = branch;
        this.year = year;
        this.email = email;
        this.pswd = pswd;
      //  this.cnfpswd = cnfpswd;
    }

    public String getName() {
        return name;
    }

    public String getUsn() {
        return usn;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public String getEmail() {
        return email;
    }

    public String getPswd() {
        return pswd;
    }

    public boolean isValidEmail(String email){
        if (email.contains("@")){
            return true;
        }else
            return false;
    }


    public static void displayMessageToast(Context context, String displayMessage){
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }
}

