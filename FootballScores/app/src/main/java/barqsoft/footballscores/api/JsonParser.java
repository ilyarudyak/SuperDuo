package barqsoft.footballscores.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class JsonParser {

    public static final String TAG = JsonParser.class.getSimpleName();

    // code for series: http://api.football-data.org/alpha/soccerseasons
    // they are NOT the same as in the previous year!
    private static final String SERIE_A =               "401"; // Serie A 2015/16
    private static final String PREMIER_LEAGUE =        "398"; // Premier League 2015/16
    private static final String CHAMPIONS_LEAGUE =      "";    // n/a
    private static final String PRIMERA_DIVISION =      "399"; // Primera Division 2015/16
    private static final String BUNDESLIGA =            "394"; // Bundesliga 2015/16 - BL1

    private static final String SEASON_LINK =           "http://api.football-data.org/alpha/soccerseasons/";
    private static final String MATCH_LINK =            "http://api.football-data.org/alpha/fixtures/";
    private static final String FIXTURES =              "fixtures";
    private static final String LINKS =                 "_links";
    private static final String SOCCER_SEASON =         "soccerseason";
    private static final String SELF =                  "self";
    private static final String MATCH_DATE =            "date";
    private static final String HOME_TEAM =             "homeTeamName";
    private static final String AWAY_TEAM =             "awayTeamName";
    private static final String RESULT =                "result";
    private static final String HOME_GOALS =            "goalsHomeTeam";
    private static final String AWAY_GOALS =            "goalsAwayTeam";
    private static final String MATCH_DAY =             "matchday";
    private static final String HREF =                  "href";

    private static boolean isKnownLeague(String league) {
        if (league.equals(PREMIER_LEAGUE)   ||
            league.equals(SERIE_A)          ||
            league.equals(CHAMPIONS_LEAGUE) ||
            league.equals(BUNDESLIGA)       ||
            league.equals(PRIMERA_DIVISION)
            ) {
            return true;
        }
        return false;
    }

    private static void setDateTime(Match m, String date) {

        String time = date.substring(date.indexOf("T") + 1, date.indexOf("Z"));
        date = date.substring(0, date.indexOf("T"));
        SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date parseddate = match_date.parse(date + time);
            SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
            new_date.setTimeZone(TimeZone.getDefault());
            date = new_date.format(parseddate);
            time = date.substring(date.indexOf(":") + 1);
            date = date.substring(0, date.indexOf(":"));

            m.setDate(date);
            m.setTime(time);

        } catch (Exception e) {
            Log.d(TAG, "error here!");
            Log.e(TAG, e.getMessage());
        }
    }

    public static List<Match> parseScoresJsonStr(String scoresJsonStr) throws JSONException {

        List<Match> matchList = new ArrayList<>();
        JSONArray matches = new JSONObject(scoresJsonStr).getJSONArray(FIXTURES);

        for (int i = 0; i < matches.length(); i++) {

            Match match = new Match();
            JSONObject mo = matches.getJSONObject(i);

            String league = mo.getJSONObject(LINKS)
                    .getJSONObject(SOCCER_SEASON)
                                     .getString(HREF)
                                     .replace(SEASON_LINK, "");

            if (isKnownLeague(league)) {

                match.setLeague(league);                                        // (1) league

                String matchId = mo.getJSONObject(LINKS)
                                          .getJSONObject(SELF)
                                          .getString(HREF)
                                          .replace(MATCH_LINK, "");
                match.setMatchId(matchId);                                      // (8) matchId

                String date = mo.getString(MATCH_DATE);
                setDateTime(match, date);                                       // (2) date (3) time


                match.setHome(mo.getString(HOME_TEAM));                         // (4) home
                match.setAway(mo.getString(AWAY_TEAM));                         // (5) away
                match.setHomeGoals(mo.getJSONObject(RESULT)                     // (6) homeGoals
                        .getString(HOME_GOALS));
                match.setAwayGoals(mo.getJSONObject(RESULT)                     // (7) awayGoals
                        .getString(AWAY_GOALS));
                match.setMatchDay(mo.getString(MATCH_DAY));                     // (9) matchDay

                matchList.add(match);
            }
        }
        return matchList;
    }
}
