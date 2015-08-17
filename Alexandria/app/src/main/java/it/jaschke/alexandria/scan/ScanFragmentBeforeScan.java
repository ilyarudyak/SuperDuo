package it.jaschke.alexandria.scan;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;

import it.jaschke.alexandria.R;


/**
 * Created by ilyarudyak on 8/17/15.
 */
public class ScanFragmentBeforeScan extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // retain fragment after configuration change
        setRetainInstance(true);

        View rootView = inflater.inflate(R.layout.fragment_before_scan, container, false);
        Button scanButton = (Button) rootView.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new IntentIntegrator(getActivity())).initiateScan();
            }
        });
        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.scan_title);
    }
}
