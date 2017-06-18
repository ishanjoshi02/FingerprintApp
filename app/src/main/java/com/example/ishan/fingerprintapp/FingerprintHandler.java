package com.example.ishan.fingerprintapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by ishan on 18/6/17.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context mContext;

    public FingerprintHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return;
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }

    public void update(String e, Boolean success) {
        TextView textView = (TextView) ((Activity)mContext).findViewById(R.id.error_text_view);
        textView.setText(e);
        if (success) {
            textView.setTextSize(26);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }
    }

}
