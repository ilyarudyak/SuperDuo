package barqsoft.footballscores.api;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

import barqsoft.footballscores.utils.DataUtils;
import barqsoft.footballscores.utils.NetworkUtils;

/**
 * Created by yehya khaled on 3/2/2015.
 */
public class ScoresFetchService extends IntentService {

    public static final String TAG = ScoresFetchService.class.getSimpleName();

    // see here: http://api.football-data.org/documentation
    // filter for timeFrame for past or next 2 days
    public static final String PAST_2_DAYS = "p2";
    public static final String NEXT_2_DAYS = "n2";

    public ScoresFetchService() {
        super("ScoresFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        getData(PAST_2_DAYS);
        getData(NEXT_2_DAYS);
    }

    private void getData(String timeFrame) {
        String jsonStr = NetworkUtils.getScoresJsonFromNetwork(timeFrame);
        if (jsonStr != null) {
            try {
                List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr, true);
                ContentValues[] values = Match.buildContentValues(matchList);
                DataUtils.insertMatches(getApplicationContext(), values);
            } catch (JSONException e) {
                Log.d(TAG, "Can not parse JSON: ", e);
            }
        }
    }

}

