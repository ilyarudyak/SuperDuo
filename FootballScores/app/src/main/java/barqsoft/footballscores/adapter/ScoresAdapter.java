package barqsoft.footballscores.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import barqsoft.footballscores.R;
import barqsoft.footballscores.utils.MiscUtils;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class ScoresAdapter extends CursorAdapter {

    public static final int COL_DATE =          1;
    public static final int COL_TIME =          2;
    public static final int COL_HOME =          3;
    public static final int COL_AWAY =          4;
    public static final int COL_LEAGUE =        5;
    public static final int COL_HOME_GOALS =    6;
    public static final int COL_AWAY_GOALS =    7;
    public static final int COL_MATCH_ID =      8;
    public static final int COL_MATCH_DAY =     9;

    public static final String FOOTBALL_SCORES_HASH_TAG = "#FootballScores";

    public String detailMatchId = "0";

    public ScoresAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        setMainView(viewHolder, context, cursor);
        setDetailView(viewHolder, view, context, cursor);

    }

    // helper methods
    private void setMainView(ViewHolder viewHolder, Context context, Cursor cursor) {

        viewHolder.time.setText(cursor.getString(COL_TIME));

        String homeName = cursor.getString(COL_HOME);
        String awayName = cursor.getString(COL_AWAY);
        viewHolder.homeName.setText(homeName);
        viewHolder.awayName.setText(awayName);
        viewHolder.homeCrest.setImageResource(MiscUtils.getTeamCrestByTeamName(homeName));
        viewHolder.awayCrest.setImageResource(MiscUtils.getTeamCrestByTeamName(awayName));
        // set content description
        viewHolder.homeCrest.setContentDescription(context.getString(R.string.a11y_home_team_crest, homeName));
        viewHolder.awayCrest.setContentDescription(context.getString(R.string.a11y_away_team_crest, awayName));

        viewHolder.score.setText(MiscUtils.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
        viewHolder.matchId = cursor.getString(COL_MATCH_ID);
    }
    private void setDetailView(final ViewHolder viewHolder, View view,
                               final Context context, Cursor cursor) {

        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_detail, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);

        if (viewHolder.matchId.equals(detailMatchId)) {

            container.addView(v, 0, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            TextView league = (TextView) v.findViewById(R.id.league_text_view);
            league.setText(MiscUtils.getLeague(context, cursor.getString(COL_LEAGUE)));

            TextView matchDay = (TextView) v.findViewById(R.id.match_day_text_view);
            matchDay.setText(MiscUtils.getMatchDay(cursor.getInt(COL_MATCH_DAY),
                    cursor.getString(COL_LEAGUE)));

            Button shareButton = (Button) v.findViewById(R.id.share_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add Share Action
                    context.startActivity(createShareMatchIntent(viewHolder.homeName.getText() + " "
                            + viewHolder.score.getText() + " " + viewHolder.awayName.getText() + " "));
                }
            });
            // add content description
            shareButton.setContentDescription(context.getString(R.string.a11y_share_button));

        } else {
            container.removeAllViews();
        }
    }

    // create share intent to use with share button
    public Intent createShareMatchIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareText + FOOTBALL_SCORES_HASH_TAG);
        return shareIntent;
    }



}
