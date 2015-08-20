package barqsoft.footballscores.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import barqsoft.footballscores.R;
import barqsoft.footballscores.api.JsonParser;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class MiscUtils {

    public static String getLeague(String leagueNum) {
        switch (leagueNum) {
            case JsonParser.SERIE_A:
                return "Serie A 2015/16";
            case JsonParser.PREMIER_LEAGUE:
                return "Premier League 2015/16";
            case JsonParser.PRIMERA_DIVISION:
                return "Primera Division 2015/16";
            case JsonParser.BUNDESLIGA:
                return "Bundesliga 2015/16 - BL1";
            default:
                return "Not listed league";
        }
    }
    public static String getMatchDay(int match_day, String leagueNum) {
        if (leagueNum == JsonParser.CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return "Group Stages, Matchday : 6";
            } else if (match_day == 7 || match_day == 8) {
                return "First Knockout round";
            } else if (match_day == 9 || match_day == 10) {
                return "QuarterFinal";
            } else if (match_day == 11 || match_day == 12) {
                return "SemiFinal";
            } else {
                return "Final";
            }
        } else {
            return "Matchday : " + String.valueOf(match_day);
        }
    }
    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }
    public static int getTeamCrestByTeamName(String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        switch (teamname) {
            case "Arsenal London FC":
                return R.drawable.arsenal;
            case "Manchester United FC":
                return R.drawable.manchester_united;
            case "Manchester City FC":
                return R.drawable.manchester_city;
            case "Liverpool FC":
                return R.drawable.liverpool;
            case "Chelsea FC":
                return R.drawable.chelsea;
            case "Swansea City":
                return R.drawable.swansea_city_afc;
            case "Leicester City":
                return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC":
                return R.drawable.everton_fc_logo1;
            case "West Ham United FC":
                return R.drawable.west_ham;
            case "Tottenham Hotspur FC":
                return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion":
                return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC":
                return R.drawable.sunderland;
            case "Stoke City FC":
                return R.drawable.stoke_city;
            default:
                return R.drawable.no_icon;
        }
    }

    // -------------------- date and time --------------------

    /**
     * We define names for tabs in our ViewPager. We get:
     * Monday-Yesterday-Today-Tomorrow-Friday (suppose Today is Wednesday).
     */
    public static String getDayName(Context context, int position) {

        Calendar calendar = new GregorianCalendar();

        // "EEEE" gets us the full name of a day
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);

        switch (position) {
            case 0:
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date date = calendar.getTime();
                return sdf.format(date);
            case 1:
                return context.getString(R.string.yesterday);
            case 2:
                return context.getString(R.string.today);
            case 3:
                return context.getString(R.string.tomorrow);
            case 4:
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                date = calendar.getTime();
                return sdf.format(date);
            default:
                throw new IllegalArgumentException("wrong position: " + position);
        }
    }

    /**
     * We define date of fragment based on position. So
     * if position == 2 we return current date,
     * if position == 3 we return Tomorrow's date etc.
     */
    public static String getFragmentDate(int position) {

        Calendar calendar = new GregorianCalendar();

        // "yyyy-MM-dd" gets us date like 2015-08-20
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        switch (position) {
            case 0:
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date date = calendar.getTime();
                return sdf.format(date);
            case 1:
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                return sdf.format(date);
            case 2:
                date = calendar.getTime();
                return sdf.format(date);
            case 3:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = calendar.getTime();
                return sdf.format(date);
            case 4:
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                date = calendar.getTime();
                return sdf.format(date);
            default:
                throw new IllegalArgumentException("wrong position: " + position);
        }
    }
}
