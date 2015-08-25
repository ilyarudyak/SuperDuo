package barqsoft.footballscores.detail;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.adapter.ScoresAdapter;
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
    private View mDetailsView;
    private ShareActionProvider mShareActionProvider;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // initialize loader
        getLoaderManager().initLoader(SCORES_LOADER, null, this);

        mScoresView = inflater.inflate(R.layout.scores_list_item, container, false);
        LinearLayout scoresLayout = (LinearLayout) mScoresView.findViewById(R.id.scores_top_linear_layout);
        mDetailsView = inflater.inflate(R.layout.fragment_detail, container, false);
        scoresLayout.addView(mDetailsView);

        return mScoresView;
    }

    // helper methods
    private void setScoresView() {

        ImageView leagueImage = (ImageView) mScoresView.findViewById(R.id.league_image_view);
        leagueImage.setImageResource(R.drawable.premier_league);
        leagueImage.setContentDescription(getString(R.string.a11y_premier_league_image));

        TextView homeName = (TextView) mScoresView.findViewById(R.id.home_name);
        TextView awayName = (TextView) mScoresView.findViewById(R.id.away_name);
        ImageView homeCrest = (ImageView) mScoresView.findViewById(R.id.home_crest);
        ImageView awayCrest = (ImageView) mScoresView.findViewById(R.id.away_crest);
        TextView score = (TextView) mScoresView.findViewById(R.id.score_text_view);
        TextView time = (TextView) mScoresView.findViewById(R.id.time_text_view);

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
    private void setDetailsView() {

        TextView league = (TextView) mDetailsView.findViewById(R.id.league_text_view);
        TextView matchDay = (TextView) mDetailsView.findViewById(R.id.match_day_text_view);

        Button shareButton = (Button) mDetailsView.findViewById(R.id.share_button);
        shareButton.setVisibility(View.INVISIBLE);

        if (mMatch != null) {
            league.setText(MiscUtils.getLeague(getActivity(), mMatch.getLeague()));
            matchDay.setText(MiscUtils.getMatchDay(Integer.parseInt(mMatch.getMatchDay()),
                    mMatch.getLeague()));
        }
    }

    // ------------- menu and share intent ---------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // now this fragment can handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        setShareIntent();
    }

    // this method shares match details
    private void setShareIntent() {

        if (mMatch != null) {
            String sharedStr = mMatch.getHome() + " " +
                    mMatch.getHomeGoals() + ":" + mMatch.getAwayGoals() + " " +
                    mMatch.getAway() + " " +
                    ScoresAdapter.FOOTBALL_SCORES_HASH_TAG;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedStr);


            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(shareIntent);
            }
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
        setScoresView();
        setDetailsView();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
