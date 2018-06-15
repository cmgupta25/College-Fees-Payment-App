package com.project.esh_an.vemanafeeportal.add_on;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by CHANDRA on 4/22/2018.
 */

public class netConnection {
    Context context;
    public netConnection(Context context){
        this.context=context;
    }

    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(cm!=null){
                NetworkInfo info = cm.getActiveNetworkInfo();
                if(info!=null){
                    if(info.getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }


        }
        return false;
    }
}
