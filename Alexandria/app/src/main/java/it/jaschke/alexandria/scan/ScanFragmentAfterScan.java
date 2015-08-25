package it.jaschke.alexandria.scan;

import android.os.Bundle;

import it.jaschke.alexandria.BookSearchFragment;

/**
 * Created by ilyarudyak on 8/17/15.
 */
public class ScanFragmentAfterScan extends BookSearchFragment {

    private Bundle mArgs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArgs = getArguments();
    }


    @Override
    public void setEanEditText() {
        super.setEanEditText();

        if (mArgs != null) {
            setEanText(mArgs.getString(ScanActivity.EAN));
        }
    }

}
