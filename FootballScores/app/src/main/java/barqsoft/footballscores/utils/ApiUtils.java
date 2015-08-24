package barqsoft.footballscores.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import barqsoft.footballscores.api.JsonParser;
import barqsoft.footballscores.api.Match;

/**
 * Created by ilyarudyak on 8/20/15.
 */
public class ApiUtils {

    public static final int NUMBER_OF_MATCHES = 20;

    public static final int NUMBER_OF_DAYS = 5;
    public static final int MAX_MATCH_ID = 15000;
    public static final int MAX_GOALS = 5;
    public static final int MAX_HOURS = 24;
    public static final String MATCH_DAY = "10";

    public static final List<String> sTeams = Arrays.asList(
            "Arsenal London FC", "Manchester United FC", "Manchester City FC",
            "Liverpool FC", "Chelsea FC", "Swansea City", "Leicester City",
            "Everton FC", "West Ham United FC", "Tottenham Hotspur FC",
            "West Bromwich Albion", "Sunderland AFC", "Stoke City FC",
            "Aston Villa FC", "Burnley FC", "Crystal Palace FC",
            "Hull City FC", "Queen's Park FC", "Newcastle United FC",
            "Southampton FC"
    );

    public static List<Match> generateTestData() {

        List<Match> matches = new ArrayList<>();
        List<String> days = getDays();

        Random r = new Random();
        r.setSeed(0);

        for (int i = 0; i < NUMBER_OF_MATCHES; i++) {
            Match match = new Match();

            String homeTeam = sTeams.get(r.nextInt(sTeams.size()));
            String awayTeam = getAwayTeam(r, homeTeam);

            match.setLeague(JsonParser.PREMIER_LEAGUE);
            match.setDate(days.get(r.nextInt(NUMBER_OF_DAYS)));
            match.setTime(Integer.toString(r.nextInt(MAX_GOALS)) + ":00");
            match.setHome(homeTeam);
            match.setAway(awayTeam);
            match.setHomeGoals(Integer.toString(r.nextInt(MAX_GOALS)));
            match.setAwayGoals(Integer.toString(r.nextInt(MAX_GOALS)));
            match.setMatchId(Integer.toString(r.nextInt(MAX_MATCH_ID)));
            match.setMatchDay(MATCH_DAY);

            matches.add(match);
        }

        return matches;

    }

    private static String getAwayTeam(Random r, String homeTeam) {
        String awayTeam = sTeams.get(r.nextInt(sTeams.size()));
        while (awayTeam.equals(homeTeam)) {
            awayTeam = sTeams.get(r.nextInt(sTeams.size()));
        }
        return awayTeam;
    }

    private static List<String> getDays() {

        List<String> days = new ArrayList<>();

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            days.add(MiscUtils.formatDate(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;
    }
}

















