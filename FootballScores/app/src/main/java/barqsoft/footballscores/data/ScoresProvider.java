package barqsoft.footballscores.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresProvider extends ContentProvider {

    private static final int MATCHES = 100;
    private static final int MATCHES_WITH_LEAGUE = 101;
    private static final int MATCHES_WITH_ID = 102;
    private static final int MATCHES_WITH_DATE = 103;

    private static ScoresDbHelper sOpenHelper;
//    private static final SQLiteQueryBuilder sScoreQuery =
//            new SQLiteQueryBuilder();

    private static final String SCORES_BY_LEAGUE =
            ScoresContract.ScoresTable.LEAGUE_COLUMN + " = ?";
    private static final String SCORES_BY_DATE =
            ScoresContract.ScoresTable.DATE_COLUMN + " LIKE ?";
    private static final String SCORES_BY_ID =
            ScoresContract.ScoresTable.MATCH_ID_COLUMN + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScoresContract.BASE_CONTENT_URI.toString();
        matcher.addURI(authority, null, MATCHES);
        matcher.addURI(authority, "league", MATCHES_WITH_LEAGUE);
        matcher.addURI(authority, "id", MATCHES_WITH_ID);
        matcher.addURI(authority, "date", MATCHES_WITH_DATE);
        return matcher;
    }
    private int matchUri(Uri uri) {
        String link = uri.toString();
        {
            if (link.contentEquals(ScoresContract.BASE_CONTENT_URI.toString())) {
                return MATCHES;
            } else if (link.contentEquals(ScoresContract.ScoresTable.buildScoreWithDate().toString())) {
                return MATCHES_WITH_DATE;
            } else if (link.contentEquals(ScoresContract.ScoresTable.buildScoreWithId().toString())) {
                return MATCHES_WITH_ID;
            } else if (link.contentEquals(ScoresContract.ScoresTable.buildScoreWithLeague().toString())) {
                return MATCHES_WITH_LEAGUE;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreate() {
        sOpenHelper = new ScoresDbHelper(getContext());
        return false;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCHES:
                return ScoresContract.ScoresTable.CONTENT_TYPE;
            case MATCHES_WITH_LEAGUE:
                return ScoresContract.ScoresTable.CONTENT_TYPE;
            case MATCHES_WITH_ID:
                return ScoresContract.ScoresTable.CONTENT_ITEM_TYPE;
            case MATCHES_WITH_DATE:
                return ScoresContract.ScoresTable.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        //Log.v(FetchScoreTask.TAG,uri.getPathSegments().toString());
        int match = matchUri(uri);
        //Log.v(FetchScoreTask.TAG,SCORES_BY_LEAGUE);
        //Log.v(FetchScoreTask.TAG,selectionArgs[0]);
        //Log.v(FetchScoreTask.TAG,String.valueOf(match));
        switch (match) {
            case MATCHES:
                retCursor = sOpenHelper.getReadableDatabase().query(
                        ScoresContract.ScoresTable.TABLE_NAME,
                        projection, null, null, null, null, sortOrder);
                break;
            case MATCHES_WITH_DATE:
                //Log.v(FetchScoreTask.TAG,selectionArgs[1]);
                //Log.v(FetchScoreTask.TAG,selectionArgs[2]);
                retCursor = sOpenHelper.getReadableDatabase().query(
                        ScoresContract.ScoresTable.TABLE_NAME,
                        projection, SCORES_BY_DATE, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_ID:
                retCursor = sOpenHelper.getReadableDatabase().query(
                        ScoresContract.ScoresTable.TABLE_NAME,
                        projection, SCORES_BY_ID, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_LEAGUE:
                retCursor = sOpenHelper.getReadableDatabase().query(
                        ScoresContract.ScoresTable.TABLE_NAME,
                        projection, SCORES_BY_LEAGUE, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = sOpenHelper.getWritableDatabase();
        //db.delete(DatabaseContract.SCORES_TABLE,null,null);
        //Log.v(FetchScoreTask.TAG,String.valueOf(sUriMatcher.match(uri)));
        switch (matchUri(uri)) {
            case MATCHES:
                db.beginTransaction();
                int returncount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(
                                ScoresContract.ScoresTable.TABLE_NAME, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1) {
                            returncount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returncount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
