package barqsoft.footballscores.api;

import android.content.ContentValues;

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
                ", date='" + date + '\'' +
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

        cv.put(ScoresTable.MATCH_ID_COLUMN,     m.getMatchId());
        cv.put(ScoresTable.DATE_COLUMN,         m.getDate());
        cv.put(ScoresTable.TIME_COLUMN,         m.getTime());
        cv.put(ScoresTable.HOME_COLUMN,         m.getHome());
        cv.put(ScoresTable.AWAY_COLUMN,         m.getAway());
        cv.put(ScoresTable.HOME_GOALS_COLUMN,   m.getHomeGoals());
        cv.put(ScoresTable.AWAY_GOALS_COLUMN,   m.getAwayGoals());
        cv.put(ScoresTable.LEAGUE_COLUMN,       m.getLeague());
        cv.put(ScoresTable.MATCH_DAY_COLUMN,    m.getMatchDay());

        return cv;
    }

    public static ContentValues[] buildContentValues(List<Match> matches) {
        ContentValues[] values = new ContentValues[matches.size()];
        for (int i = 0; i < matches.size(); i++) {
            values[i] = buildContentValues(matches.get(i));
        }
        return values;
    }
}
