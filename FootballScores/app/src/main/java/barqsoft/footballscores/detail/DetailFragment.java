package barqsoft.footballscores.detail;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.data.ScoresContract;
import barqsoft.footballscores.utils.MiscUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment
    implements LoaderManager.LoaderCallbacks {

    public static final String TAG = DetailFragment.class.getSimpleName();
    public static final String MATCH_ID = "barqsoft.footballscores.detail.MATCH_ID";
    public static final int SCORES_LOADER = 1;

    private Match mMatch;
    private View mScoresView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // initialize loader
        getLoaderManager().initLoader(SCORES_LOADER, null, this);

        mScoresView = inflater.inflate(R.layout.scores_list_item, container, false);
        LinearLayout scoresLayout = (LinearLayout) mScoresView.findViewById(R.id.scores_linear_layout);
        View detailsView = inflater.inflate(R.layout.fragment_detail, container, false);
        scoresLayout.addView(detailsView);

        return mScoresView;
    }

    private void setScoresView(View scoresView) {

        ImageView leagueImage = (ImageView) scoresView.findViewById(R.id.league_image_view);
        leagueImage.setImageResource(R.drawable.premier_league);

        TextView homeName = (TextView) scoresView.findViewById(R.id.home_name);
        TextView awayName = (TextView) scoresView.findViewById(R.id.away_name);
        ImageView homeCrest = (ImageView) scoresView.findViewById(R.id.home_crest);
        ImageView awayCrest = (ImageView) scoresView.findViewById(R.id.away_crest);
        TextView score = (TextView) scoresView.findViewById(R.id.score_text_view);
        TextView time = (TextView) scoresView.findViewById(R.id.time_text_view);

        if (mMatch != null) {

            String homeNameStr = mMatch.getHome();
            String awayNameStr = mMatch.getAway();

            homeName.setText(homeNameStr);
            awayName.setText(awayNameStr);
            homeCrest.setImageResource(MiscUtils.getTeamCrestByTeamName(homeNameStr));
            awayCrest.setImageResource(MiscUtils.getTeamCrestByTeamName(awayNameStr));
            score.setText(mMatch.getHomeGoals() + ":" + mMatch.getAwayGoals());
            time.setText(mMatch.getTime());

            homeCrest.setContentDescription(getActivity().getString(
                    R.string.a11y_home_team_crest, homeNameStr));
            homeCrest.setContentDescription(getActivity().getString(
                    R.string.a11y_away_team_crest, awayNameStr));
        }

    }

    // -------------------- loader callbacks --------------------

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] matchId = new String[1];
        matchId[0] = getActivity().getIntent().getStringExtra(MATCH_ID);
        if (MainActivity.DEBUG) { Log.d(TAG, "id=" + matchId[0]); }
        return new CursorLoader(getActivity(),
                ScoresContract.ScoresTable.buildScoreWithId(),
                null, null, matchId, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mMatch = Match.getMatchesFromCursor((Cursor) data).get(0);
        setScoresView(mScoresView);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
