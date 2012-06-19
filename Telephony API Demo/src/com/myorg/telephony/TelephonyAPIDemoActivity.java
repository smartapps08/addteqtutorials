package com.myorg.telephony;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class TelephonyAPIDemoActivity extends Activity{
	TextView txtOutput;
	TelephonyManager telephonyManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtOutput = (TextView) findViewById(R.id.txtOutput);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		String phoneInfo = "Device Id: " + telephonyManager.getDeviceId()
				+ "\n" + "Network Operator Name: "
				+ telephonyManager.getNetworkOperatorName()
				+ "\nNetwork Country ISO: "
				+ telephonyManager.getNetworkCountryIso() + "\nNetwork Type: "
				+ telephonyManager.getNetworkType();
		txtOutput.append(phoneInfo);
		
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		

	}
	
	PhoneStateListener listener=new PhoneStateListener()
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			String stateString="N/A";
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				stateString="Idle";
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				stateString="Off Hook";
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				stateString="Ringing";
				break;
			default:
				break;
			}
			txtOutput.append(String.format("\n onCallStateChanged: %s "+incomingNumber, stateString));
		}
	};
}