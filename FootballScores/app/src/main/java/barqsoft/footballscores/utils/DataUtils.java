package barqsoft.footballscores.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.data.ScoresContract;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class DataUtils {

    public static final String TAG = DataUtils.class.getSimpleName();

    /** Insert an array of content values (that we get from list of
     *  Match objects) into DB using bulkInsert().
     * */
    public static void insertMatches(Context context, ContentValues[] values) {
        int count = context.getContentResolver().bulkInsert(
                ScoresContract.BASE_CONTENT_URI, values);
//        Log.d(TAG, "# of inserted entries: " + count);
    }

    /**
     * Get the list of matches for Today. We use this in small widget.
     * */
    public static List<Match> getMatchesToday(Context context) {

        // get Today's date string
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        String dateStr = MiscUtils.formatDate(date);
        Log.d(TAG, dateStr);

        // where condition date=dateStr
        String selection = ScoresContract.ScoresTable.DATE_COLUMN + "=" + "'" + dateStr + "'";
        Log.d(TAG, selection);

        Cursor cursor = context.getContentResolver().query(
                ScoresContract.BASE_CONTENT_URI, null, selection, null, null);

        // convert to list of matches
        List<Match> matches = Match.getMatchesFromCursor(cursor);
        cursor.close();

        return matches;
    }

}
