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

    public static String getLeague(Context context, String leagueNum) {
        switch (leagueNum) {
            case JsonParser.SERIE_A:
                return context.getString(R.string.serie_a);
            case JsonParser.PREMIER_LEAGUE:
                return context.getString(R.string.premier_league);
            case JsonParser.PRIMERA_DIVISION:
                return context.getString(R.string.primera_divison);
            case JsonParser.BUNDESLIGA:
                return context.getString(R.string.bundesliga);
            default:
                return "Not listed league";
        }
    }
    public static String getMatchDay(int matchDay, String leagueNum) {
        if (leagueNum.equals(JsonParser.CHAMPIONS_LEAGUE)) {
            if (matchDay <= 6) {
                return "Group Stages, Matchday : 6";
            } else if (matchDay == 7 || matchDay == 8) {
                return "First Knockout round";
            } else if (matchDay == 9 || matchDay == 10) {
                return "QuarterFinal";
            } else if (matchDay == 11 || matchDay == 12) {
                return "SemiFinal";
            } else {
                return "Final";
            }
        } else {
            return "Matchday : " + String.valueOf(matchDay);
        }
    }
    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }
    public static int getTeamCrestByTeamName(String teamName) {
        if (teamName == null) {
            return R.drawable.no_icon;
        }
        switch (teamName) {
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
            case "Aston Villa FC" :
                return R.drawable.aston_villa;
            case "Burnley FC" :
                return R.drawable.burnley_fc_hd_logo;
            case "Crystal Palace FC" :
                return R.drawable.crystal_palace_fc;
            case "Hull City FC" :
                return R.drawable.hull_city_afc_hd_logo;
            case "Queen's Park FC" :
                return R.drawable.queens_park_rangers_hd_logo;
            case "Newcastle United FC" :
                return R.drawable.newcastle_united;
            case "Southampton FC" :
                return R.drawable.southampton_fc;
            default:
                return R.drawable.no_icon;
        }
    }

    // -------------------- time and time --------------------

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
                return context.getString(R.string.today);
        }
    }

    /**
     * We define time of fragment based on position. So
     * if position == 2 we return current time,
     * if position == 3 we return Tomorrow's time etc.
     */
    public static String getFragmentDate(int position) {

        Calendar calendar = new GregorianCalendar();

        switch (position) {
            case 0:
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date date = calendar.getTime();
                return formatDate(date);
            case 1:
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                return formatDate(date);
            case 2:
                date = calendar.getTime();
                return formatDate(date);
            case 3:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = calendar.getTime();
                return formatDate(date);
            case 4:
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                date = calendar.getTime();
                return formatDate(date);
            default:
                throw new IllegalArgumentException("wrong position: " + position);
        }
    }

    /** Format time like 2015-08-20. This is the format we use in DB. */
    public static String formatDate(Date date) {

        // "yyyy-MM-dd" gets us time like 2015-08-20
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return sdf.format(date);
    }



}
