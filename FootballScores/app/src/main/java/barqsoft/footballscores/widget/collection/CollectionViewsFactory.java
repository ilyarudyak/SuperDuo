package barqsoft.footballscores.widget.collection;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import barqsoft.footballscores.R;
import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.detail.DetailFragment;
import barqsoft.footballscores.utils.MiscUtils;

/**
 * Created by ilyarudyak on 8/24/15.
 */
public class CollectionViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Match> mMatches;
    private Context mContext;
    private int mAppWidgetId;

    public CollectionViewsFactory(Context context, Intent intent, List<Match> matches) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mMatches = matches;
    }

    // build Remote Views
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.small_widget);
        setRemoteViews(rv, position);
        setDetailIntent(rv, position);

        return rv;
    }

    // helper methods
    private void setDetailIntent(RemoteViews rv, int position) {
        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(DetailFragment.MATCH_ID, mMatches.get(position).getMatchId());
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        i.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.small_widget, i);
    }
    private void setRemoteViews(RemoteViews rv, int position) {

        Match m = mMatches.get(position);

        // set command titles
        rv.setTextViewText(R.id.home_name, m.getHome());
        rv.setTextViewText(R.id.away_name, m.getAway());

        // set command logos
        rv.setImageViewResource(R.id.home_crest, MiscUtils.getTeamCrestByTeamName(m.getHome()));
        rv.setImageViewResource(R.id.away_crest, MiscUtils.getTeamCrestByTeamName(m.getAway()));

        // set score
        rv.setTextViewText(R.id.score_text_view, m.getHomeGoals() + ":" + m.getAwayGoals());

        // set time of match
        rv.setTextViewText(R.id.time_text_view, m.getTime());

    }

    // simple getters
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public int getCount() {
        return mMatches.size();
    }

    // empty methods
    @Override
    public void onCreate() {
    }
    @Override
    public void onDataSetChanged() {

    }
    @Override
    public void onDestroy() {

    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

}
