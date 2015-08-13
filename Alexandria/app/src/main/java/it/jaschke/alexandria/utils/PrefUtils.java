package it.jaschke.alexandria.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import it.jaschke.alexandria.R;

/**
 * Created by ilyarudyak on 8/13/15.
 */
public class PrefUtils {

    public static boolean isSearchLive(Context c) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getBoolean(c.getString(R.string.pref_live_search_key), false);
    }
}
