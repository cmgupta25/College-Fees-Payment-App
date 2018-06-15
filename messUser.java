package com.project.esh_an.vemanafeeportal.add_on;

/**
 * Created by Chandra on 4/23/2018.
 */

public class messUser {

    String rmn,usn2,mon,am;

    public messUser(){}

    public messUser(String rmn, String usn2, String mon, String am) {
        this.rmn = rmn;
        this.usn2 = usn2;
        this.mon = mon;
        this.am = am;
    }

    public String getRmn() {
        return rmn;
    }

    public void setRmn(String rmn) {
        this.rmn = rmn;
    }

    public String getUsn2() {
        return usn2;
    }

    public void setUsn2(String usn2) {
        this.usn2 = usn2;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

}
