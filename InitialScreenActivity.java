package com.project.esh_an.vemanafeeportal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.project.esh_an.vemanafeeportal.R;
import com.project.esh_an.vemanafeeportal.add_on.netConnection;
import com.project.esh_an.vemanafeeportal.utility.AvenuesParams;
import com.project.esh_an.vemanafeeportal.utility.ServiceUtility;

public class InitialScreenActivity extends AppCompatActivity implements View.OnClickListener {

	private TextView taccessCode, tmerchantId, trsaKeyUrl, tredirectUrl, tcancelUrl;
	private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;
	Button nextButton;

	private void init(){
		accessCode = (EditText) findViewById(R.id.accessCode);
		accessCode.setVisibility(View.INVISIBLE);

		merchantId = (EditText) findViewById(R.id.merchantId);
		merchantId.setVisibility(View.INVISIBLE);

		orderId  = (EditText) findViewById(R.id.orderId);
		currency = (EditText) findViewById(R.id.currency);

		amount = (EditText) findViewById(R.id.amount);

		rsaKeyUrl = (EditText) findViewById(R.id.rsaUrl);
		rsaKeyUrl.setVisibility(View.INVISIBLE);

		redirectUrl = (EditText) findViewById(R.id.redirectUrl);
		redirectUrl.setVisibility(View.INVISIBLE);

		cancelUrl = (EditText) findViewById(R.id.cancelUrl);
		cancelUrl.setVisibility(View.INVISIBLE);


		taccessCode = (TextView) findViewById(R.id.taccessCode);
		taccessCode.setVisibility(View.INVISIBLE);

		tmerchantId = (TextView) findViewById(R.id.tmerchantId);
		tmerchantId.setVisibility(View.INVISIBLE);

		currency = (EditText) findViewById(R.id.currency);

		amount = (EditText) findViewById(R.id.amount);

		trsaKeyUrl = (TextView) findViewById(R.id.trsaUrl);
		trsaKeyUrl.setVisibility(View.INVISIBLE);

		tredirectUrl = (TextView) findViewById(R.id.tredirectUrl);
		tredirectUrl.setVisibility(View.INVISIBLE);

		tcancelUrl = (TextView) findViewById(R.id.tcancelUrl);
		tcancelUrl.setVisibility(View.INVISIBLE);

		Intent in = getIntent();
		String am = in.getExtras().getString("EditText");
		amount.setText(am);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial_screen);
		init();

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


		nextButton = (Button) findViewById(R.id.nextButton);

		nextButton.setOnClickListener(this);
	}

	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.nextButton:
				//Mandatory parameters. Other parameters can be added if required.
				String vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
				String vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
				String vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
				String vAmount = ServiceUtility.chkNull(amount.getText()).toString().trim();
				if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
					Intent intent = new Intent(this, WebViewActivity.class);
					intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
					intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
					intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
					intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
					intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount.getText()).toString().trim());

					intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl.getText()).toString().trim());
					intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl.getText()).toString().trim());
					intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl.getText()).toString().trim());


					startActivity(intent);
				} else {
					showToast("All parameters are mandatory.");
				}
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this,"Caution: " + msg, Toast.LENGTH_LONG).show();
	}


	@Override
	protected void onStart() {
		super.onStart();
		//generating new order number for every transaction
			Integer randomNum = ServiceUtility.randInt(0, 9999999);
			orderId.setText(randomNum.toString());

	}
}