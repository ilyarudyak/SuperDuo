package barqsoft.footballscores.utils;

import android.content.ContentValues;
import android.content.Context;

import barqsoft.footballscores.data.ScoresContract;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class DataUtils {

    public static void insertMatches(Context context, ContentValues[] values) {
        context.getContentResolver().bulkInsert(
                ScoresContract.BASE_CONTENT_URI, values);
    }
}
