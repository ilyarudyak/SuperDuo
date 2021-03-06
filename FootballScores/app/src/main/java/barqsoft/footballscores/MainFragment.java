package barqsoft.footballscores;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import barqsoft.footballscores.adapter.ScoresAdapter;
import barqsoft.footballscores.adapter.ViewHolder;
import barqsoft.footballscores.data.ScoresContract;
import barqsoft.footballscores.detail.DetailActivity;
import barqsoft.footballscores.detail.DetailFragment;
import barqsoft.footballscores.utils.MiscUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MainFragment.class.getSimpleName();

    // if this flag is set we show detail activity on click
    public static final boolean IS_DETAIL_ACTIVITY = true;

    public static final int SCORES_LOADER = 0;
    public static final String FRAGMENT_DATE = "fragment_date";

    private ScoresAdapter mAdapter;
    private String[] mDate = new String[1];
    private View rootView;

    static MainFragment newInstance(int position) {
        MainFragment mf = new MainFragment();
        Bundle args = new Bundle();

        args.putString(FRAGMENT_DATE, MiscUtils.getFragmentDate(position));
        mf.setArguments(args);

        return mf;
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate[0] = getArguments().getString(FRAGMENT_DATE);
//        Log.d(TAG, mDate[0]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // initialize loader
        getLoaderManager().initLoader(SCORES_LOADER, null, this);

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setAdapter();

        return rootView;
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
                if (IS_DETAIL_ACTIVITY) {
                    Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                    detailIntent.putExtra(DetailFragment.MATCH_ID, selected.matchId);
                    if (MainActivity.DEBUG) {
                       Log.d(TAG, "id = " + selected.matchId);
                    }
                    getActivity().startActivity(detailIntent);
                } else {

                    mAdapter.detailMatchId = selected.matchId;
                    MainActivity.selectedMatchId = selected.matchId;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
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
