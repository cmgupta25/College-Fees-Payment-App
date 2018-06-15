package com.project.esh_an.vemanafeeportal.add_on;

/**
 * Created by Chandra on 4/24/2018.
 */

public class hostelUser {
    String roomnum,sem,usn,amt;

    public hostelUser(){}

    public hostelUser(String roomnum, String sem, String usn, String amt) {
        this.roomnum = roomnum;
        this.sem = sem;
        this.usn = usn;
        this.amt = amt;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public String getSpinner() {
        return sem;
    }

    public void setSpinner(String spinner) {
        this.sem = spinner;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
