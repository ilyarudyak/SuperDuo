package barqsoft.footballscores.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import barqsoft.footballscores.data.ScoresContract.ScoresTable;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 1;

    public ScoresDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String createScoresTable =           "CREATE TABLE "
                + ScoresTable.TABLE_NAME +         " ("
                + ScoresTable._ID +                " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScoresTable.DATE_COLUMN +        " TEXT NOT NULL,"
                + ScoresTable.TIME_COLUMN +        " TEXT NOT NULL,"
                + ScoresTable.HOME_COLUMN +        " TEXT NOT NULL,"
                + ScoresTable.AWAY_COLUMN +        " TEXT NOT NULL,"
                + ScoresTable.LEAGUE_COLUMN +      " TEXT NOT NULL,"
                + ScoresTable.HOME_GOALS_COLUMN +  " TEXT NOT NULL,"
                + ScoresTable.AWAY_GOALS_COLUMN +  " TEXT NOT NULL,"
                + ScoresTable.MATCH_ID_COLUMN +    " TEXT NOT NULL,"
                + ScoresTable.MATCH_DAY_COLUMN +   " TEXT NOT NULL,"
                +                                  " UNIQUE ("
                + ScoresTable.MATCH_ID_COLUMN +    ") ON CONFLICT REPLACE"
                +                                  " );";

        db.execSQL(createScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // this is just data from API so we can drop table on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + ScoresTable.TABLE_NAME);
    }
}
