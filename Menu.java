package com.project.esh_an.vemanafeeportal.add_on;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.esh_an.vemanafeeportal.R;
import com.project.esh_an.vemanafeeportal.myprofile;
import com.project.esh_an.vemanafeeportal.payfee;

public class Menu extends AppCompatActivity{
    ProgressDialog pd;
    WebView htmlWebView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.myprofile:
                    startActivity(new Intent(Menu.this,myprofile.class));
                    return true;
                case R.id.payfee:
                    startActivity(new Intent(Menu.this,payfee.class));
                    return true;
                case R.id.feedetails:
                    new AlertDialog.Builder(Menu.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Working on it...:-)")
                            .setPositiveButton("ok",null)
                            .show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...Please Wait...!");
        pd.show();
        htmlWebView = (WebView) findViewById(R.id.webview);
        htmlWebView.setWebViewClient(new Menu.CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        htmlWebView.loadUrl("http://vemanait.edu.in/");

    }
    public class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }

            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (htmlWebView.canGoBack()) {
            htmlWebView.goBack();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Logout from the application.?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }
    }
}
