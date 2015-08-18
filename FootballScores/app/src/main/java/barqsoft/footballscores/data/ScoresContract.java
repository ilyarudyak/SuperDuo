package barqsoft.footballscores.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresContract {

    public static final String CONTENT_AUTHORITY = "barqsoft.footballscores";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "scores";

    public static final class ScoresTable implements BaseColumns {

        public static final String TABLE_NAME = "scores_table";

        public static final String LEAGUE_COLUMN =      "league";
        public static final String DATE_COLUMN =        "date";
        public static final String TIME_COLUMN =        "time";
        public static final String HOME_COLUMN =        "home";
        public static final String AWAY_COLUMN =        "away";
        public static final String HOME_GOALS_COLUMN =  "home_goals";
        public static final String AWAY_GOALS_COLUMN =  "away_goals";
        public static final String MATCH_ID_COLUMN =    "match_id";
        public static final String MATCH_DAY_COLUMN =   "match_day";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        // TODO why league is not argument?
        public static Uri buildScoreWithLeague() {
            return BASE_CONTENT_URI.buildUpon().appendPath("league").build();
        }
        public static Uri buildScoreWithId() {
            return BASE_CONTENT_URI.buildUpon().appendPath("id").build();
        }
        public static Uri buildScoreWithDate() {
            return BASE_CONTENT_URI.buildUpon().appendPath("date").build();
        }
    }
}
