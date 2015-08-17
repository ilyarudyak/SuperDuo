package it.jaschke.alexandria.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    public static final String TAG = ScanActivity.class.getSimpleName();

    public static final String EAN = "ean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new ScanFragmentBeforeScan(), null)
                    .commit();
        }
    }

    public void onActivityResult(int request, int result, Intent i) {

        IntentResult scanIntent = IntentIntegrator.parseActivityResult(request, result, i);

        if (scanIntent != null) {
            String formatStr = scanIntent.getFormatName();
            String contentsStr = scanIntent.getContents();

            ScanFragmentAfterScan scas = new ScanFragmentAfterScan();
            Bundle args = new Bundle();
            args.putString(EAN, contentsStr);
            Log.d(TAG, "args created " + args);
            scas.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, scas)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
