package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import barqsoft.footballscores.adapter.ScoresAdapter;
import barqsoft.footballscores.adapter.ViewHolder;
import barqsoft.footballscores.data.ScoresContract;
import barqsoft.footballscores.api.ScoresFetchService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int SCORES_LOADER = 0;

    private ScoresAdapter mAdapter;
    private String[] mDate = new String[1];
    private View rootView;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // initialize loader
        getLoaderManager().initLoader(SCORES_LOADER, null, this);

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        updateScores();
        setAdapter();

        return rootView;
    }

    // start service to get scores
    private void updateScores() {
        Intent serviceIntent = new Intent(getActivity(), ScoresFetchService.class);
        getActivity().startService(serviceIntent);
    }
    private void setAdapter () {

        mAdapter = new ScoresAdapter(getActivity(), null, 0);
        mAdapter.detailMatchId = MainActivity.selectedMatchId;

        ListView scoreList = (ListView) rootView.findViewById(R.id.scores_list);
        scoreList.setAdapter(mAdapter);
        scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder selected = (ViewHolder) view.getTag();
                mAdapter.detailMatchId = selected.matchId;
                MainActivity.selectedMatchId = (int) selected.matchId;
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    public void setFragmentDate(String date) {
        mDate[0] = date;
    }

    // -----------------  loader callbacks -----------------

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                ScoresContract.ScoresTable.buildScoreWithDate(),
                null, null, mDate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }


}
