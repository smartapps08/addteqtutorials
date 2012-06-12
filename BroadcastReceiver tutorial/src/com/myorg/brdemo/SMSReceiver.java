package com.myorg.brdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context,
				"Received Event: " + intent.getAction().toString(),
				Toast.LENGTH_LONG).show();
		// action
		// category
		// data as extra data
		// for multiple actions
		//
		if (intent.getAction().equalsIgnoreCase(
				"android.provider.Telephony.SMS_RECEIVED")) {

			// extract the sms
			Bundle bundle = intent.getExtras();
			SmsMessage[] chunks = null;
			String text = "";
			String address = "";

			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				chunks = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					chunks[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					text += chunks[i].getMessageBody().toString();
					address += chunks[i].getOriginatingAddress();
				}
			}

			Toast.makeText(context,
					"SMS from " + address + " Content: " + text,
					Toast.LENGTH_LONG).show();

			// pass the number and text to the new activity through intent
			Intent intentToReply = new Intent(context,
					BroadcastReceivertutorialActivity.class);
			intentToReply.putExtra("number", address);
			intentToReply.putExtra("text", text);
			intentToReply.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intentToReply);
		}

		// invoke an activity to reply the sms

	}
}
