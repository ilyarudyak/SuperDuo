package barqsoft.footballscores.utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import barqsoft.footballscores.data.ScoresContract;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class DataUtils {

    public static final String TAG = DataUtils.class.getSimpleName();

    public static void insertMatches(Context context, ContentValues[] values) {
        int count = context.getContentResolver().bulkInsert(
                ScoresContract.BASE_CONTENT_URI, values);
        Log.d(TAG, count + "");
    }
}
