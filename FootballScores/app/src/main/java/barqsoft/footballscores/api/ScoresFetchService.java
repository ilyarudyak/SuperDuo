package barqsoft.footballscores.api;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import barqsoft.footballscores.R;
import barqsoft.footballscores.data.ScoresContract;
import barqsoft.footballscores.utils.DataUtils;
import barqsoft.footballscores.utils.NetworkUtils;

/**
 * Created by yehya khaled on 3/2/2015.
 */
public class ScoresFetchService extends IntentService {

    public static final String LOG_TAG = ScoresFetchService.class.getSimpleName();

    // see here: http://api.football-data.org/documentation
    // filter for timeFrame for past or next 2 days
    public static final String PAST_2_DAYS = "p2";
    public static final String NEXT_2_DAYS = "n2";

    public ScoresFetchService() {
        super("ScoresFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        getData2();
//        getData(NEXT_2_DAYS);
    }

    private void getData2() {
        String jsonStr = NetworkUtils.getScoresJsonFromNetwork(PAST_2_DAYS);
        try {
            List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr);
            ContentValues[] values = Match.buildContentValues(matchList);
            DataUtils.insertMatches(getApplicationContext(), values);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getData(String timeFrame) {
        //Creating fetch URL
        final String BASE_URL = "http://api.football-data.org/alpha/fixtures";
        final String QUERY_TIME_FRAME = "timeFrame";

        Uri fetch_build = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(QUERY_TIME_FRAME, timeFrame).build();
        HttpURLConnection m_connection = null;
        BufferedReader reader = null;
        String JSON_data = null;
        //Opening Connection
        try {
            URL fetch = new URL(fetch_build.toString());
            m_connection = (HttpURLConnection) fetch.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.addRequestProperty("X-Auth-Token", ApiKey.KEY);
            m_connection.connect();

            // Read the input stream into a String
            InputStream inputStream = m_connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            JSON_data = buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception here" + e.getMessage());
        } finally {
            if (m_connection != null) {
                m_connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error Closing Stream");
                }
            }
        }
        try {
            if (JSON_data != null) {
                //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                JSONArray matches = new JSONObject(JSON_data).getJSONArray("fixtures");
                if (matches.length() == 0) {
                    //if there is no data, call the function on dummy data
                    //this is expected behavior during the off season.
                    processJsonData(getString(R.string.dummy_data), getApplicationContext(), false);
                    return;
                }


                processJsonData(JSON_data, getApplicationContext(), true);
            } else {
                //Could not Connect
                Log.d(LOG_TAG, "Could not connect to server.");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    private void processJsonData(String JSONdata,
                                 Context mContext, boolean isReal) {
        //JSON data
        final String SERIE_A = "401";
        final String PREMIER_LEGAUE = "398";
        final String CHAMPIONS_LEAGUE = "";
        final String PRIMERA_DIVISION = "399";
        final String BUNDESLIGA = "394";
        final String SEASON_LINK = "http://api.football-data.org/alpha/soccerseasons/";
        final String MATCH_LINK = "http://api.football-data.org/alpha/fixtures/";
        final String FIXTURES = "fixtures";
        final String LINKS = "_links";
        final String SOCCER_SEASON = "soccerseason";
        final String SELF = "self";
        final String MATCH_DATE = "date";
        final String HOME_TEAM = "homeTeamName";
        final String AWAY_TEAM = "awayTeamName";
        final String RESULT = "result";
        final String HOME_GOALS = "goalsHomeTeam";
        final String AWAY_GOALS = "goalsAwayTeam";
        final String MATCH_DAY = "matchday";

        //Match data
        String League = null;
        String mDate = null;
        String mTime = null;
        String Home = null;
        String Away = null;
        String Home_goals = null;
        String Away_goals = null;
        String match_id = null;
        String match_day = null;


        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(FIXTURES);


            //ContentValues to be inserted
            Vector<ContentValues> values = new Vector<ContentValues>(matches.length());
            for (int i = 0; i < matches.length(); i++) {
                JSONObject match_data = matches.getJSONObject(i);
                League = match_data.getJSONObject(LINKS).getJSONObject(SOCCER_SEASON).
                        getString("href");
                League = League.replace(SEASON_LINK, "");
                if (League.equals(PREMIER_LEGAUE) ||
                        League.equals(SERIE_A) ||
                        League.equals(CHAMPIONS_LEAGUE) ||
                        League.equals(BUNDESLIGA) ||
                        League.equals(PRIMERA_DIVISION) || true) {
                    match_id = match_data.getJSONObject(LINKS).getJSONObject(SELF).
                            getString("href");
                    match_id = match_id.replace(MATCH_LINK, "");
                    if (!isReal) {
                        //This if statement changes the match ID of the dummy data so that it all goes into the database
                        match_id = match_id + Integer.toString(i);
                    }

                    mDate = match_data.getString(MATCH_DATE);
                    mTime = mDate.substring(mDate.indexOf("T") + 1, mDate.indexOf("Z"));
                    mDate = mDate.substring(0, mDate.indexOf("T"));
                    SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date parseddate = match_date.parse(mDate + mTime);
                        SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
                        new_date.setTimeZone(TimeZone.getDefault());
                        mDate = new_date.format(parseddate);
                        mTime = mDate.substring(mDate.indexOf(":") + 1);
                        mDate = mDate.substring(0, mDate.indexOf(":"));

                        if (!isReal) {
                            //This if statement changes the dummy data's date to match our current date range.
                            Date fragmentdate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
                            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
                            mDate = mformat.format(fragmentdate);
                        }
                    } catch (Exception e) {
                        Log.d(LOG_TAG, "error here!");
                        Log.e(LOG_TAG, e.getMessage());
                    }
                    Home = match_data.getString(HOME_TEAM);
                    Away = match_data.getString(AWAY_TEAM);
                    Home_goals = match_data.getJSONObject(RESULT).getString(HOME_GOALS);
                    Away_goals = match_data.getJSONObject(RESULT).getString(AWAY_GOALS);
                    match_day = match_data.getString(MATCH_DAY);

                    ContentValues cv = new ContentValues();
                    cv.put(ScoresContract.ScoresTable.MATCH_ID_COLUMN, match_id);
                    cv.put(ScoresContract.ScoresTable.DATE_COLUMN, mDate);
                    cv.put(ScoresContract.ScoresTable.TIME_COLUMN, mTime);
                    cv.put(ScoresContract.ScoresTable.HOME_COLUMN, Home);
                    cv.put(ScoresContract.ScoresTable.AWAY_COLUMN, Away);
                    cv.put(ScoresContract.ScoresTable.HOME_GOALS_COLUMN, Home_goals);
                    cv.put(ScoresContract.ScoresTable.AWAY_GOALS_COLUMN, Away_goals);
                    cv.put(ScoresContract.ScoresTable.LEAGUE_COLUMN, League);
                    cv.put(ScoresContract.ScoresTable.MATCH_DAY_COLUMN, match_day);

                    values.add(cv);
                }
            }
            int inserted_data = 0;
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);
            inserted_data = mContext.getContentResolver().bulkInsert(
                    ScoresContract.BASE_CONTENT_URI, insert_data);

            //Log.v(LOG_TAG,"Succesfully Inserted : " + String.valueOf(inserted_data));
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

    }
}

