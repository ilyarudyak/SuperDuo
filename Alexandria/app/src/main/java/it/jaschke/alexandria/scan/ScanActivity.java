package it.jaschke.alexandria.scan;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import it.jaschke.alexandria.R;

public class ScanActivity extends AppCompatActivity {

    public static final String TAG = ScanActivity.class.getSimpleName();

    public static final String EAN = "ean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.scan_container, new ScanFragmentBeforeScan(), null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void onActivityResult(int request, int result, Intent intent) {

        IntentResult scanIntent = IntentIntegrator.parseActivityResult(request, result, intent);

        if (scanIntent != null) {
            String formatStr = scanIntent.getFormatName();
            String contentsStr = scanIntent.getContents();

            ScanFragmentAfterScan fas = new ScanFragmentAfterScan();
            Bundle args = new Bundle();
            args.putString(EAN, contentsStr);
            Log.d(TAG, "args created " + args);
            fas.setArguments(args);

            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.scan_container, fas)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
