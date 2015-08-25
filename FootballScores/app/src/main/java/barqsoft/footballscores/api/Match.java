package barqsoft.footballscores.api;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.data.ScoresContract.ScoresTable;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class Match {

    private String league;      // (1)
    private String date;        // (2)
    private String time;        // (3)
    private String home;        // (4)
    private String away;        // (5)
    private String homeGoals;   // (6)
    private String awayGoals;   // (7)
    private String matchId;     // (8)
    private String matchDay;    // (9)

    public Match() {

    }

    public Match(String away, String awayGoals, String date, String home,
                 String homeGoals, String league, String matchDay,
                 String matchId, String time) {
        this.away = away;
        this.awayGoals = awayGoals;
        this.date = date;
        this.home = home;
        this.homeGoals = homeGoals;
        this.league = league;
        this.matchDay = matchDay;
        this.matchId = matchId;
        this.time = time;
    }

    public String getAway() {
        return away;
    }
    public void setAway(String away) {
        this.away = away;
    }

    public String getAwayGoals() {
        return awayGoals;
    }
    public void setAwayGoals(String awayGoals) {
        this.awayGoals = awayGoals;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }

    public String getHomeGoals() {
        return homeGoals;
    }
    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }

    public String getMatchDay() {
        return matchDay;
    }
    public void setMatchDay(String matchDay) {
        this.matchDay = matchDay;
    }

    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Match{" +
                "away='" + away + '\'' +
                ", league='" + league + '\'' +
                ", time='" + date + '\'' +
                ", time='" + time + '\'' +
                ", home='" + home + '\'' +
                ", homeGoals='" + homeGoals + '\'' +
                ", awayGoals='" + awayGoals + '\'' +
                ", matchId='" + matchId + '\'' +
                ", matchDay='" + matchDay + '\'' +
                '}';
    }

    public static ContentValues buildContentValues(Match m) {

        ContentValues cv = new ContentValues();

        cv.put(ScoresTable.LEAGUE_COLUMN,       m.getLeague());     // (1)
        cv.put(ScoresTable.DATE_COLUMN,         m.getDate());       // (2)
        cv.put(ScoresTable.TIME_COLUMN,         m.getTime());       // (3)
        cv.put(ScoresTable.HOME_COLUMN,         m.getHome());       // (4)
        cv.put(ScoresTable.AWAY_COLUMN,         m.getAway());       // (5)
        cv.put(ScoresTable.HOME_GOALS_COLUMN,   m.getHomeGoals());  // (6)
        cv.put(ScoresTable.AWAY_GOALS_COLUMN,   m.getAwayGoals());  // (7)
        cv.put(ScoresTable.MATCH_ID_COLUMN,     m.getMatchId());    // (8)
        cv.put(ScoresTable.MATCH_DAY_COLUMN,    m.getMatchDay());   // (9)

        return cv;
    }

    public static ContentValues[] buildContentValues(List<Match> matches) {
        ContentValues[] values = new ContentValues[matches.size()];
        for (int i = 0; i < matches.size(); i++) {
            values[i] = buildContentValues(matches.get(i));
        }
        return values;
    }

    public static List<Match>  getMatchesFromCursor(Cursor cursor) {

        List<Match> matches = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                Match match = new Match();

                match.setLeague(cursor.getString(cursor.getColumnIndex(ScoresTable.LEAGUE_COLUMN)));
                match.setDate(cursor.getString(cursor.getColumnIndex(ScoresTable.DATE_COLUMN)));
                match.setTime(cursor.getString(cursor.getColumnIndex(ScoresTable.TIME_COLUMN)));
                match.setHome(cursor.getString(cursor.getColumnIndex(ScoresTable.HOME_COLUMN)));
                match.setAway(cursor.getString(cursor.getColumnIndex(ScoresTable.AWAY_COLUMN)));
                match.setHomeGoals(cursor.getString(cursor.getColumnIndex(ScoresTable.HOME_GOALS_COLUMN)));
                match.setAwayGoals(cursor.getString(cursor.getColumnIndex(ScoresTable.AWAY_GOALS_COLUMN)));
                match.setMatchId(cursor.getString(cursor.getColumnIndex(ScoresTable.MATCH_ID_COLUMN)));
                match.setMatchDay(cursor.getString(cursor.getColumnIndex(ScoresTable.MATCH_DAY_COLUMN)));

                matches.add(match);
            } while(cursor.moveToNext());
        }

        return matches;
    }


}
